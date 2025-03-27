import { Routes } from '@angular/router';
import {DisplayBlogComponent} from './display-blog/display-blog.component';
import {HomeComponent} from './home-component/home.component';
import {BlogComponent} from './blog/blog.component';

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'blog/embed',
    component: BlogComponent,
  },
  {
    path: 'blog/:blogId',
    component: DisplayBlogComponent,
  },
  {
    path: 'blog/embed/:blogId',
    component: BlogComponent,
  },
  {
    path: '', pathMatch: 'full', redirectTo: 'home'
  },
  {
    path: '**', redirectTo: 'home'
  },
];
