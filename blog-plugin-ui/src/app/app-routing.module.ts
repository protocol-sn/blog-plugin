import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from './home-component/home.component';
import {BlogComponent} from './blog/blog.component';
import {DisplayBlogComponent} from './display-blog/display-blog.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
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

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    useHash: true,
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
