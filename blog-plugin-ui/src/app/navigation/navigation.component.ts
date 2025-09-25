import {Component, inject, OnInit, signal} from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {map, mergeMap, Observable, shareReplay} from 'rxjs';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {AsyncPipe} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {BlogService} from '../blog.service';
import {WriteBlogComponent} from '../write-blog/write-blog.component';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from '../auth.service';
import {MatButtonModule} from '@angular/material/button';
import {rxResource} from '@angular/core/rxjs-interop';
import {BlogMetadataResourceType} from '../blog-metadata-resource-type';

@Component({
  selector: 'app-navigation',
  imports: [
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    AsyncPipe,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './navigation.component.html',
  standalone: true,
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent implements OnInit {
  private readonly breakpointObserver = inject(BreakpointObserver);
  private readonly blogService = inject(BlogService);
  readonly dialog = inject(MatDialog);
  protected readonly authService:AuthService = inject(AuthService);

  blogListRequest = signal("");
  protected blogListResource = rxResource<BlogMetadataResourceType, string>({
    params: () => this.blogService.lastUpdated(),
    stream: () => {
      console.log(this.authService.subBehavior);
      return this.authService.subBehavior
        .pipe(
          mergeMap(sub => {
            console.log(sub);
            return this.blogService.listBlogs(sub)
              .pipe(
                map (
                  value => {
                    console.log(value);
                    return {
                      count: value.length,
                      results: value
                    };
                  })
              );
          })
        )

    },
    defaultValue: {
      count: 0,
      results: []
    }
  })

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  showBlogForm() {
    this.dialog.open(WriteBlogComponent, {
      id: 'writeBlogDialog',
      height: '80%',
      width: '60%',
    });
  }

  ngOnInit(): void {
    console.log("initializing");
    this.authService.subBehavior.subscribe(value => this.blogListRequest.set(value));
    this.blogService.blogUpdate.subscribe(value => this.blogListRequest.set(value.toString()));
    this.blogListResource.reload();
  }
}
