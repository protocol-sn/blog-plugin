import {Component, Input} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-blog-card',
  imports: [
    MatCardModule,
    NgForOf
  ],
  templateUrl: './blog-card.component.html',
  standalone: true,
  styleUrl: './blog-card.component.scss'
})
export class BlogCardComponent {
  @Input() title!: string;
  @Input() postDate!: string;
  @Input() tags!: string[];

}
