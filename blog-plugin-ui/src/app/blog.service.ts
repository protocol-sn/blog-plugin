import {inject, Injectable} from '@angular/core';
import {BehaviorSubject, map, mergeMap, Observable} from 'rxjs';
import {BlogMetadata} from './blog-metadata';
import {Blog} from './blog';
import {ApiService} from './api.service';
import {UserService} from './user.service';
import {environment} from '../environments/environment';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  private readonly apiService: ApiService = inject(ApiService);
  private readonly userService: UserService = inject(UserService);
  private readonly authService: AuthService = inject(AuthService);
  public blogUpdate:BehaviorSubject<Date> = new BehaviorSubject<Date>(new Date());

  private readonly LIST_BLOGS_ENDPOINT: string = "/blog/by-user/{userId}/metadata";
  private readonly MOST_RECENT_BLOG_ENDPOINT: string = "/blog/by-user/{userId}/most-recent";
  private readonly BLOG_BY_ID_ENDPOINT: string = "/blog/{blogId}";
  private readonly SAVE_BLOG_ENDPOINT: string = "/blog";
  private readonly DEFAULT_BLOG_ENDPOINT: string = "/blog/default";
  private readonly DEFAULT_BLOG_STREAM_ENDPOINT: string = "/blog/default-stream";

  constructor() { }

  getDefaultStream(): Observable<Blog[]> {
    return this.apiService.doSecureGET<Blog[]>(environment.SERVICE_HOME + this.DEFAULT_BLOG_STREAM_ENDPOINT)
      .pipe(
        map(
          value => {
            if (value.ok && value.body) {
              return value.body;
            }
            return <Blog[]>[];
          }
      ));
  }

  listBlogs(userId: string): Observable<BlogMetadata[]> {
    return this.apiService.doSecureGET<BlogMetadata[]>(environment.SERVICE_HOME + this.LIST_BLOGS_ENDPOINT.replace("{userId}", userId))
      .pipe(
        map(
          value => {
            if (value.ok && value.body) {
              return value.body;
            }
            return <BlogMetadata[]>[];
          }));
  }

  getDefaultBlog():Observable<Blog> {
    if (this.authService.isAuthenticated()) {
      return this.authService.subObs
        .pipe(
          mergeMap(sub => {
            return this.apiService.doSecureGET<Blog>(environment.SERVICE_HOME + this.MOST_RECENT_BLOG_ENDPOINT.replace("{userId}", sub))
              .pipe(
                map(
                  value => {
                    if (value.ok && value.body) {
                      return value.body;
                    }
                    return <Blog>{};
                  }
                )
              )
          })
        )
    }
    return this.apiService.doSecureGET<Blog>(environment.SERVICE_HOME + this.DEFAULT_BLOG_ENDPOINT)
      .pipe(
        map(
          value => {
            if (value.ok && value.body) {
              return value.body;
            }
            return <Blog>{};
          }
        )
      )
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
    return this.apiService.doSecureGET<Blog>(environment.SERVICE_HOME + this.BLOG_BY_ID_ENDPOINT.replace("{blogId}", id))
      .pipe(
        map(
          value => {
            if (value.ok && value.body) {
              return value.body;
            }
            return <Blog>{};
          }
        )
    )
  }

  saveBlog(blog: Blog) {
    console.log("about to save");
    this.blogUpdate.next(new Date())
    this.apiService.doSecurePOST<Blog>(environment.SERVICE_HOME + this.SAVE_BLOG_ENDPOINT, "application/json", blog)
      .subscribe();
  }
}
