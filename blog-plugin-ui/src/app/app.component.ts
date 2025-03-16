import { Component } from '@angular/core';
import {NavigationComponent} from './navigation/navigation.component';

@Component({
  selector: 'app-root',
  imports: [NavigationComponent],
  template: `<app-navigation></app-navigation>`,
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'blog-plugin-ui';
}
