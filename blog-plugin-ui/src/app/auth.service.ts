import {inject, Injectable} from '@angular/core';
import {AuthConfig, OAuthService} from "angular-oauth2-oidc";
import {Router} from "@angular/router";
import {environment} from '../environments/environment';
import {BehaviorSubject, Observable} from 'rxjs';
import {UserService} from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly oidcSecurityService = inject(OAuthService);
  public roles: string[] = [];
  public sub: string = "";
  public userName: string = "";
  public subObs: Observable<string> = new Observable<string>();
  public subBehavior:BehaviorSubject<string> = new BehaviorSubject("");

  authCodeFlowConfig: AuthConfig = {
    requireHttps: environment.PRODUCTION,
    // Url of the Identity Provider
    issuer: environment.OAUTH_ISSUER,

    // URL of the SPA to redirect the user to after login
    redirectUri: window.location.origin,

    // The SPA's id. The SPA is registerd with this id at the auth-server
    // clientId: 'server.code',
    clientId: environment.OAUTH_CLIENT_ID,

    // Just needed if your auth server demands a secret. In general, this
    // is a sign that the auth server is not configured with SPAs in mind
    // and it might not enforce further best practices vital for security
    // such applications.
    // dummyClientSecret: 'secret',

    responseType: 'code',

    // set the scope for the permissions the client should request
    // The first four are defined by OIDC.
    // Important: Request offline_access to get a refresh token
    // The api scope is a usecase specific one
    // scope: 'openid profile email offline_access api',
    scope: 'openid profile email offline_access roles plugin-aud',

    showDebugInformation: true,
  };

  async initialize(userService: UserService) {
    console.log("initializing...");
    this.oidcSecurityService.loadDiscoveryDocumentAndTryLogin().then(r => {});
    let localSub = sessionStorage.getItem('loggedInUser');
    if (localSub) {
      this.subBehavior.next(localSub);
      this.sub = localSub;
    }
    this.oidcSecurityService.events
      .forEach(event => {
        console.log(event);
        if (event.type === 'token_received') {
          console.log("token received");
          this.oidcSecurityService.loadUserProfile()
            .then((value:Record<string, any>) => {
              console.log("time to load user");
              this.loadUser().then(userId => {
                userService.setUser(userId);
              });
            });
        }
        else if(event.type === 'token_expires') {
          this.oidcSecurityService.refreshToken();
        }
      })
      .then(value => {
        console.log(value);
      })
      .finally(() => {});
  }

  constructor(router: Router) {
    this.subObs = this.subBehavior.asObservable();
    this.oidcSecurityService.configure(this.authCodeFlowConfig);
  }

  login(redirectRoute: string = '/') {
    this.oidcSecurityService.initLoginFlow();
    return new Observable<boolean>(subscriber => subscriber.next(true));
  }

  logout() {
    sessionStorage.removeItem('loggedInUser')
    this.oidcSecurityService.logOut();
  }

  isAuthenticated() {
    return this.oidcSecurityService.hasValidIdToken() && this.oidcSecurityService.hasValidAccessToken();
  }

  getAccessToken() {
    return this.oidcSecurityService.getAccessToken();
  }

  getIdentityData() {
    return this.oidcSecurityService.getIdentityClaims();
  }

  getScopes() {
    return this.oidcSecurityService.getGrantedScopes();
  }

  loadUser():Promise<string> {
    if (this.isAuthenticated()) {
      if (this.roles && this.sub && this.userName) {
        console.log("already had values");
        return new Promise(resolve => this.sub);
      }
      if (!this.oidcSecurityService.discoveryDocumentLoaded) {
        this.oidcSecurityService.loadDiscoveryDocument()
          .then(value => {
            console.log("reloaded disco doc and setting values");
            return this.setValuesFromUserProfile();
          });
      }
      else {
        console.log("setting values");
        return this.setValuesFromUserProfile();
      }
    }
    console.log("Not authenticated");
    return new Promise(resolve => '');
  }

  private async setValuesFromUserProfile() {
    console.log("about to load profile");
    const value: Record<string, any> = await this.oidcSecurityService.loadUserProfile();
    console.log("loaded profile");
    this.roles = value['info'].realm_access.roles;
    this.sub = value['info'].sub;
    console.log("sub: " + this.sub);
    sessionStorage.setItem('loggedInUser', this.sub);
    this.subBehavior.next(this.sub);
    this.userName = value['info'].preferred_username;
    return this.sub;
  }
}
