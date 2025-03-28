import {Component, inject, Input, OnInit} from '@angular/core';
import {MatGridListModule} from '@angular/material/grid-list';
import {BlogCardComponent} from '../blog-card/blog-card.component';
import {MatListModule} from '@angular/material/list';
import {Blog} from '../blog';
import {Observable} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {BlogService} from '../blog.service';
import {NgForOf, NgIf} from '@angular/common';
import {MarkdownComponent} from 'ngx-markdown';

@Component({
  selector: 'app-blog',
  imports: [
    MatGridListModule,
    MatListModule,
    BlogCardComponent,
    NgForOf,
    MarkdownComponent,
    NgIf
  ],
  templateUrl: './blog.component.html',
  standalone: true,
  styleUrl: './blog.component.scss'
})
export class BlogComponent implements OnInit {
  @Input() blog: Observable<Blog> | undefined;
  protected blogsToShow: Blog[] = <Blog[]>[];
  private readonly blogService = inject(BlogService);


  constructor(private readonly route: ActivatedRoute) {}

  ngOnInit(): void {
    if (!this.blog) {
      let blogId = this.route.snapshot.paramMap.get('blogId');
      if (blogId) {
        if (blogId === 'stream') {
          this.blogService.getDefaultStream().subscribe(value => this.blogsToShow = value);
        }
        else {
          this.blogService.getBlogById(blogId).subscribe(blog => this.blogsToShow = [blog]);
        }
      }
      else {
        this.blogService.getDefaultBlog().subscribe(blog => this.blogsToShow = [blog]);
      }
    }
    else {
      this.blog.subscribe(blog => this.blogsToShow = [blog]);
    }
  }

  splitTags(tags: string | null): string[] {
    if (tags == null) {
      return [];
    }
    return tags.split(',');
  }

}
