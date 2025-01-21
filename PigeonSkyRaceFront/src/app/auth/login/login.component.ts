import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';

import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,

  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private floginb: FormBuilder,private authService: AuthService,private router: Router) {
    this.loginForm = this.floginb.group({
      username: ['', Validators.required],
      motDePasse: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          this.authService.storeToken(response);
          this.router.navigate(['/dashboard']);
          alert("Login Successful");
          console.log('Login successful!');
        },
        error: (err) => {
          console.error('Login failed:', err);
          alert("Wrong Credentials!");
        },
      });
    }
  }
}
