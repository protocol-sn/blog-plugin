import {Component, inject, OnInit} from '@angular/core';
import {NavigationComponent} from '../navigation/navigation.component';
import {BlogComponent} from '../blog/blog.component';
import {ActivatedRoute} from '@angular/router';
import {BlogService} from '../blog.service';

@Component({
  selector: 'app-display-blog',
  imports: [
    NavigationComponent,
    BlogComponent
  ],
  templateUrl: './display-blog.component.html',
  standalone: true,
  styleUrl: './display-blog.component.css'
})
export class DisplayBlogComponent implements OnInit {
  protected blogId: any;
  private readonly blogService = inject(BlogService);

  constructor(private readonly route: ActivatedRoute) {}

  ngOnInit() {
    this.blogId = this.route.snapshot.paramMap.get('blogId');
  }

  getOneBlog(blogId: string) {
    console.log(blogId);
    console.log(this.blogService.getBlogById(blogId));
    return this.blogService.getBlogById(blogId);
  }
}
