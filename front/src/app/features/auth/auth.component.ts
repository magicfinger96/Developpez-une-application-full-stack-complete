import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import {
  ActivatedRoute,
  Data,
  RouterLink,
  RouterOutlet,
} from '@angular/router';
import { MatButton } from '@angular/material/button';
import { HeaderComponent } from '../../core/components/header/header.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [HeaderComponent, MatIcon, MatButton, RouterLink, RouterOutlet],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss',
})
export class AuthComponent implements OnInit, OnDestroy {
  pageTitle!: string;

  private route: ActivatedRoute = inject(ActivatedRoute);
  private subscription!: Subscription;

  ngOnInit() {
    this.subscription = this.route.url.subscribe(() => {
      this.pageTitle = this.route.snapshot.firstChild!.data['title'];
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
