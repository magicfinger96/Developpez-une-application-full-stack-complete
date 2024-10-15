import { Component } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';

/**
 * Component of the home page.
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButton, RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {}
