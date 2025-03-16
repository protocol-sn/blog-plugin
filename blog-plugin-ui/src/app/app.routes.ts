import { Routes } from '@angular/router';
import {NavigationComponent} from './navigation/navigation.component';
import {DisplayBlogComponent} from './display-blog/display-blog.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: NavigationComponent },
  {
    path: 'blog/:blogId',
    component: DisplayBlogComponent,
  },
];
