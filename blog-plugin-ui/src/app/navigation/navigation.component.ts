import {Component, inject, OnInit} from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {environment} from '../../environments/environment';
import {map, Observable, shareReplay} from 'rxjs';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {AsyncPipe, NgForOf, NgIf} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {BlogService} from '../blog.service';
import {BlogMetadata} from '../blog-metadata';
import {BlogComponent} from '../blog/blog.component';

@Component({
  selector: 'app-navigation',
  imports: [
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    AsyncPipe,
    MatIconModule,
    NgIf,
    NgForOf,
    BlogComponent
  ],
  templateUrl: './navigation.component.html',
  standalone: true,
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent implements OnInit {
  private readonly breakpointObserver = inject(BreakpointObserver);
  private readonly blogService = inject(BlogService);

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
  protected readonly environment = environment;
  blogMetas: BlogMetadata[] = [];

  ngOnInit(): void {
    this.blogService.listBlogs().subscribe(blogs => {
      this.blogMetas = blogs;
    });

  }
}
