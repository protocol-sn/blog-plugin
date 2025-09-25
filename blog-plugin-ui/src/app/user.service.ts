import {inject, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {environment} from '../environments/environment';
import {PsnUser} from './psn-user';
import {map, Observable} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  apiService: ApiService = inject(ApiService);
  authService: AuthService = inject(AuthService);
  public readonly GET_USER_ENDPOINT = "/users/{userId}";
  public user: PsnUser | undefined;
  private readonly loggedInUser: PsnUser | undefined;

  getUser(id = this.authService.sub): Observable<PsnUser> {
    if (!id) {
      return new Observable(subscriber => subscriber.next(<PsnUser>{}));
    }
    return this.apiService.doSecureGET<PsnUser>(environment.USER_PLUGIN_HOME + this.GET_USER_ENDPOINT.replace("{userId}", id))
      .pipe(
        map(
          value => {
            if (value.ok && value.body) {
              return value.body;
            }
            return <PsnUser>{};
          }
        ));
  }

  setUser(id: string) {
    this.getUser(id)
      .subscribe(value => {
        this.user = value;
      })
  }
}
