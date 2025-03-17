import { Routes } from '@angular/router';
import {DisplayBlogComponent} from './display-blog/display-blog.component';
import {WriteBlogComponent} from './write-blog/write-blog.component';
import {HomeComponent} from './home-component/home.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  {
    path: 'blog/:blogId',
    component: DisplayBlogComponent,
  },
  {
    path: 'write-blog',
    component: WriteBlogComponent
  },
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: '**', redirectTo: 'home' },
];
