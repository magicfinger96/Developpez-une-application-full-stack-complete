import { Component, inject, OnInit } from '@angular/core';
import { HeaderComponent } from '../../../../core/components/header/header.component';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { SessionService } from '../../../../core/services/session.service';
import { AuthService } from '../../services/auth.service';
import { AuthSuccess } from '../../interfaces/auth-success.interface';
import { LoginRequest } from '../../interfaces/login-request.interface';
import { User } from '../../../../core/interfaces/user.interface';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatLabel,
    ReactiveFormsModule,
    MatInputModule,
    NgIf,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit {
  private fb: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);
  private sessionService: SessionService = inject(SessionService);
  private authService: AuthService = inject(AuthService);

  public form!: FormGroup;
  public errorMessage: String = '';

  public ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      emailOrUsername: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.max(2000)]],
    });
  }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        this.sessionService.logIn(response.token);
        this.router.navigate(['/feed']);
      },
      error: () => (this.errorMessage = 'Les identifiants sont incorrects.'),
    });
  }
}
