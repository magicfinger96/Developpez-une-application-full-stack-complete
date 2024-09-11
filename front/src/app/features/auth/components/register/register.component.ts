import { Component, inject } from '@angular/core';
import { HeaderComponent } from "../../../../core/components/header/header.component";
import { MatIcon } from '@angular/material/icon';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [HeaderComponent, MatIcon, MatFormField, MatLabel],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  public form!: FormGroup;
  private fb: FormBuilder = inject(FormBuilder);
  private authService: AuthService = inject(AuthService);

  public ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.form = this.fb.group({
      title: [
        '',
        [Validators.required]
      ],
      topic_id: [
        '',
        [Validators.required]
      ],
      description: [
        '',
        [
          Validators.required,
          Validators.max(2000)
        ]
      ],
    });
  }

  public submit(): void {

    const formData = new FormData();
    formData.append('name', this.form!.get('name')?.value);
    formData.append('surface', this.form!.get('surface')?.value);
    formData.append('price', this.form!.get('price')?.value);
    formData.append('description', this.form!.get('description')?.value);

    this.authService.register(formData);
  }

}
