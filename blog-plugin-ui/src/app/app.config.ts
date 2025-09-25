import {ApplicationConfig, inject, provideAppInitializer, provideZoneChangeDetection} from '@angular/core';

import {provideHttpClient} from '@angular/common/http';
import {provideOAuthClient} from 'angular-oauth2-oidc';
import {InitializerService} from './initializer.service';
import {provideMarkdown} from 'ngx-markdown';
import {routes} from './app.routes';
import {provideRouter, withHashLocation} from '@angular/router';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withHashLocation()),
    provideOAuthClient(),
    provideHttpClient(),
    provideAppInitializer(() => {
      inject(InitializerService).init();
    }),
    provideMarkdown(),
  ]
};
