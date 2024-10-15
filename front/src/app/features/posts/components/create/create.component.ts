import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { PostsService } from '../../services/posts.service';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { TopicsService } from '../../../topics/services/topics.service';
import { Post } from '../../interfaces/post.interface';
import { MessageResponse } from '../../../../core/interfaces/message-response.interface';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PostRequest } from '../../interfaces/post-request.interface';

/**
 * Component of the post creation page.
 */
@Component({
  selector: 'app-create',
  standalone: true,
  imports: [
    MatButtonModule,
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    RouterModule,
  ],
  templateUrl: './create.component.html',
  styleUrl: './create.component.scss',
})
export class CreateComponent implements OnInit {
  public form!: FormGroup;
  private topicsService: TopicsService = inject(TopicsService);
  private postsService: PostsService = inject(PostsService);
  private matSnackBar: MatSnackBar = inject(MatSnackBar);
  private router: Router = inject(Router);
  private fb: FormBuilder = inject(FormBuilder);
  public errorMessage: String = '';

  public topics$ = this.topicsService.all();

  public ngOnInit(): void {
    this.initForm();
  }

  /**
   * Initializes the form.
   */
  private initForm(): void {
    this.form = this.fb.group({
      title: ['', [Validators.required]],
      topicId: ['', [Validators.required]],
      content: ['', [Validators.required, Validators.max(2000)]],
    });
  }

  /**
   * Called when hitting the submit form.
   * Request the post creation with the form data.
   */
  public submit(): void {
    const post = this.form?.value as PostRequest;
    this.postsService.create(post).subscribe({
      next: (response: MessageResponse) => {
        this.errorMessage = '';
        this.matSnackBar.open(response.message, 'Close', { duration: 3000 });
        this.router.navigate(['/feed']);
      },
      error: (errorResponse) => (this.errorMessage = errorResponse.error),
    });
  }
}
