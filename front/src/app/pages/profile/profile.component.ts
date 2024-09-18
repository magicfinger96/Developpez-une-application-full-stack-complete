import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SubscriptionsComponent } from "../../features/topics/components/subscriptions/subscriptions.component";
import { AuthService } from '../../features/auth/services/auth.service';
import { SessionService } from '../../core/services/session.service';
import { User } from '../../core/interfaces/user.interface';
import { noChangesValidator } from '../../shared/directives/no-changes.directive';
import { Response } from '../../features/topics/interfaces/response.interface';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatButtonModule,
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    SubscriptionsComponent
],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent implements OnInit {
  public form!: FormGroup;
  private authService: AuthService = inject(AuthService);
  private sessionService: SessionService = inject(SessionService);
  private matSnackBar: MatSnackBar = inject(MatSnackBar);
  private router: Router = inject(Router);
  private fb: FormBuilder = inject(FormBuilder);

  public errorMessage: String = '';

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
        email: [user.email, [Validators.required, Validators.email]],
      },
      { validators: noChangesValidator(user) }
    );
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('email', this.form!.get('email')?.value);
    formData.append('username', this.form!.get('username')?.value);

    this.authService.update(formData).subscribe({
      next: (response: Response) => {
        this.errorMessage = "";
        this.matSnackBar.open(response.message, "Close", { duration: 3000 });
      },
      error: (errorResponse) => (this.errorMessage = errorResponse.error),
    });
  }
}
