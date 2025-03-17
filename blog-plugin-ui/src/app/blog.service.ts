import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {BlogMetadata} from './blog-metadata';
import {Blog} from './blog';

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  blogs = [
    <Blog>{
      id: '1',
      blogTitle: 'Blog 1',
      blogText: 'This is blog 1',
      tags: 'tag1,tag2',
      createdAt: new Date(),
      updatedAt: new Date(),
    },
    <Blog>{
      id: '2',
      blogTitle: 'Blog 2',
      blogText: 'This is blog 2',
      tags: 'tag3,tag2',
      createdAt: new Date(),
      updatedAt: new Date(),
    }
  ];

  constructor() { }

  listBlogs(): Observable<BlogMetadata[]> {
    return new Observable(observer =>
      observer.next(this.blogs
        .map(value => this.toMetaData(value))));
  }

  getDefaultBlog():Observable<Blog> {
    return new Observable(observer => observer.next(this.blogs[0]));
  }

  private toMetaData(Blog:Blog):BlogMetadata {
    return <BlogMetadata>{
      id: Blog.id,
      blogTitle: Blog.blogTitle,
      tags: Blog.tags,
      createdAt: Blog.createdAt,
      updatedAt: Blog.updatedAt,
    };
  }

  getBlogById(id:string):Observable<Blog> {
    return new Observable(observer =>
      observer.next(<Blog>this.blogs
        .find(value => value.id === id)));
  }

  saveBlog(blog: Blog) {
    console.log(blog);
    this.blogs.push(blog);
  }
}
