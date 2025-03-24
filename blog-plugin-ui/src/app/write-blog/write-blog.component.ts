import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Blog} from '../blog';
import {BlogService} from '../blog.service';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-write-blog',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './write-blog.component.html',
  standalone: true,
  styleUrl: './write-blog.component.css'
})
export class WriteBlogComponent {
  private readonly blogService = inject(BlogService);
  readonly dialog = inject(MatDialog);

  blogForm = new FormGroup({
    id: new FormControl(''),
    blogTitle: new FormControl(''),
    blogText: new FormControl(''),
    tags: new FormControl(''),
  });

  onSubmit() {
    let blog = <Blog> {
      blogTitle: this.blogForm.value.blogTitle,
      blogText: this.blogForm.value.blogText,
      tags: this.blogForm.value.tags,
    }
    console.log(blog);
    this.validateBlog(blog);
    this.blogService.saveBlog(blog);
    this.dialog.getDialogById('writeBlogDialog')?.close();
  }

  private validateBlog(blog: Blog) {
    console.log("validating");
  }
}
