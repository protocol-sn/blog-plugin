import {Component, Input, OnInit} from '@angular/core';
import {MatGridListModule} from '@angular/material/grid-list';
import {BlogCardComponent} from '../blog-card/blog-card.component';
import {MatListModule} from '@angular/material/list';
import {Blog} from '../blog';
import {Observable} from 'rxjs';

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
export class BlogComponent implements OnInit {
  @Input() blog!:Observable<Blog>;
  protected blogToShow: Blog = <Blog>{};
  ngOnInit(): void {
    this.blog.subscribe(blog => this.blogToShow = blog);
  }

  splitTags(tags: string | null): string[] {
    if (tags == null) {
      return [];
    }
    return tags.split(',');
  }

}
