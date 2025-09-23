import {ApplicationConfig, inject, provideAppInitializer, provideZoneChangeDetection} from '@angular/core';

import {provideHttpClient} from '@angular/common/http';
import {provideOAuthClient} from 'angular-oauth2-oidc';
import {InitializerService} from './initializer.service';
import {provideMarkdown} from 'ngx-markdown';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideOAuthClient(),
    provideHttpClient(),
    provideAppInitializer(() => {
      inject(InitializerService).init();
    }),
    provideMarkdown(),
  ]
};
