import { Component } from '@angular/core';
import {Store} from '@ngrx/store';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logout',
  standalone: false,

  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
