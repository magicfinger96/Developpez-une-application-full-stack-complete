import { Component } from '@angular/core';
import { HeaderComponent } from "../../../core/components/header/header.component";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

}
