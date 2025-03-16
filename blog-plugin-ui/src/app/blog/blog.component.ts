import { Component } from '@angular/core';
import {MatGridListModule} from '@angular/material/grid-list';
import {BlogCardComponent} from '../blog-card/blog-card.component';
import {MatListModule} from '@angular/material/list';

@Component({
  selector: 'app-blog',
  imports: [
    MatGridListModule,
    MatListModule,
    BlogCardComponent
  ],
  templateUrl: './blog.component.html',
  standalone: true,
  styleUrl: './blog.component.scss'
})
export class BlogComponent {

}
