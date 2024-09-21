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
import { RouterModule } from '@angular/router';
import { TopicsService } from '../../../topics/services/topics.service';

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
  private fb: FormBuilder = inject(FormBuilder);

  public topics$ = this.topicsService.all();

  public ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      title: ['', [Validators.required]],
      topic_id: ['', [Validators.required]],
      content: ['', [Validators.required, Validators.max(2000)]],
    });
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('name', this.form!.get('name')?.value);
    formData.append('surface', this.form!.get('surface')?.value);
    formData.append('price', this.form!.get('price')?.value);
    formData.append('description', this.form!.get('description')?.value);

    this.postsService.create(formData);
  }
}
