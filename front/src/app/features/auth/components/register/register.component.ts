import { Component, inject } from '@angular/core';
import { HeaderComponent } from '../../../../core/components/header/header.component';
import { MatIcon } from '@angular/material/icon';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/register-request.interface';
import { AuthSuccess } from '../../interfaces/auth-success.interface';
import { User } from '../../../../core/interfaces/user.interface';
import { Router, RouterLink } from '@angular/router';
import { SessionService } from '../../../../core/services/session.service';
import { MatInputModule } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    HeaderComponent,
    MatIcon,
    MatButton,
    MatFormField,
    MatLabel,
    ReactiveFormsModule,
    MatInputModule,
    RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  public form!: FormGroup;
  private fb: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);
  private sessionService: SessionService = inject(SessionService);
  private authService: AuthService = inject(AuthService);

  public onError = false;

  public ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.max(2000)]],
    });
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/feed']);
        });
      },
      (error) => (this.onError = true)
    );
  }
}
