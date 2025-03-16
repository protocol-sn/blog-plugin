import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {BlogMetadata} from './blog-metadata';

@Injectable({
  providedIn: 'root'
})
export class BlogService {

  constructor() { }

  listBlogs(): Observable<BlogMetadata[]> {
    return new Observable(observer => observer.next([
      <BlogMetadata>{
        id: '1',
        blogTitle: 'Blog 1',
        tags: 'tag1,tag2',
        createdAt: new Date(),
        updatedAt: new Date(),
      },
      <BlogMetadata>{
        id: '2',
        blogTitle: 'Blog 2',
        tags: 'tag3,tag2',
        createdAt: new Date(),
        updatedAt: new Date(),
      }
    ]));
  }
}
