import { Component, inject, OnInit } from '@angular/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/register-request.interface';
import { AuthSuccess } from '../../interfaces/auth-success.interface';
import { Router } from '@angular/router';
import { SessionService } from '../../../../core/services/session.service';
import { MatInputModule } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { NgIf } from '@angular/common';

/**
 * Component of the register page.
 */
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatLabel,
    ReactiveFormsModule,
    MatInputModule,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent implements OnInit {
  public errorMessage: String = '';
  public form!: FormGroup;
  private fb: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);
  private sessionService: SessionService = inject(SessionService);
  private authService: AuthService = inject(AuthService);

  public ngOnInit(): void {
    this.initForm();
  }

  /**
   * Initializes the register form.
   */
  private initForm(): void {
    this.form = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  /**
   * Register the user with form data.
   */
  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.sessionService.logIn(response.token);
        this.router.navigate(['/feed']);
      },
      error: (errorResponse) => (this.errorMessage = errorResponse.error.message),
    });
  }
}
