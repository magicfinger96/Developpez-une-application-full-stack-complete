import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { noChangesValidator } from 'src/app/shared/directives/no-changes.directive';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatButtonModule,
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {
  public form!: FormGroup;
  private authService: AuthService = inject(AuthService);
  private sessionService: SessionService = inject(SessionService);
  private router: Router = inject(Router);
  private fb: FormBuilder = inject(FormBuilder);

  public ngOnInit(): void {
    this.authService.me().subscribe((user: User) => this.initForm(user));
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  private initForm(user: User): void {
    this.form = this.fb.group(
      {
        username: [user.username, [Validators.required]],
        email: [user.email, [Validators.required]],
      },
      { validators: noChangesValidator(user) }
    );
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('email', this.form!.get('email')?.value);
    formData.append('username', this.form!.get('username')?.value);

    this.authService.update(formData);
  }
}
