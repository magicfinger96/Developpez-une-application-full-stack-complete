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
import { SubscriptionsComponent } from '../../features/topics/components/subscriptions/subscriptions.component';
import { AuthService } from '../../features/auth/services/auth.service';
import { SessionService } from '../../core/services/session.service';
import { User } from '../../core/interfaces/user.interface';
import { noChangesValidator } from '../../shared/directives/no-changes.directive';
import { MessageResponse } from '../../core/interfaces/message-response.interface';

/**
 * Component of the profile page.
 */
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MatButtonModule,
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIconModule,
    SubscriptionsComponent,
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
  private user! : User;

  public ngOnInit(): void {
    this.authService.me().subscribe((user: User) => {
      this.user = user;
      this.initForm(user);
    });
  }

  /**
   * Log out from the session and redirects to the home page.
   */
  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  /**
   * Initiliaze the profile form.
   * 
   * @param user user data injected inside the form.
   */
  private initForm(user: User): void {
    this.form = this.fb.group(
      {
        username: [user.username, [Validators.required]],
        email: [user.email, [Validators.required, Validators.email]],
      },
      { validators: noChangesValidator(user) }
    );
  }

  /**
   * Called on form submit button click.
   * Update the user data.
   */
  public submit(): void {
    const formData = new FormData();
    let email : string = this.form!.get('email')?.value;
    let username : string = this.form!.get('username')?.value;
    formData.append('email', email);
    formData.append('username', username);

    this.authService.update(formData).subscribe({
      next: (response: MessageResponse) => {
        this.errorMessage = '';
        this.matSnackBar.open(response.message, 'Close', { duration: 3000 });
        this.user.email = email;
        this.user.username = username;
        this.initForm(this.user);
      },
      error: (errorResponse) => (this.errorMessage = errorResponse.error),
    });
  }
}
