import {Component, inject} from '@angular/core';
import {BlogService} from '../blog.service';
import {BlogComponent} from '../blog/blog.component';
import {NavigationComponent} from '../navigation/navigation.component';

@Component({
  selector: 'app-home-component',
  imports: [
    BlogComponent,
    NavigationComponent
  ],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrl: './home.component.css'
})
export class HomeComponent {
  private readonly blogService = inject(BlogService);

  getDefaultBlog() {
    return this.blogService.getDefaultBlog();
  }

}
