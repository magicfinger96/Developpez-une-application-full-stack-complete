import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HeaderComponent } from '../header/header.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-menu-layout',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    HeaderComponent,
    RouterModule,
    CommonModule,
    MatSidenavModule,
  ],
  templateUrl: './menu-layout.component.html',
  styleUrl: './menu-layout.component.scss',
})
export class MenuLayoutComponent {}
