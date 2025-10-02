import {Component, Input} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-blog-card',
  imports: [
    MatCardModule,
    DatePipe
  ],
  templateUrl: './blog-card.component.html',
  standalone: true,
  styleUrl: './blog-card.component.scss'
})
export class BlogCardComponent {
  @Input() title!: string | null;
  @Input() postDate!: Date | null;
  @Input() tags!: string[];

}
