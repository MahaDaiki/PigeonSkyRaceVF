import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {select, Store} from '@ngrx/store';

import {Observable} from 'rxjs';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm: FormGroup;
  registrationError: string = '';

  constructor(private fregisterb:  FormBuilder,private authService: AuthService, private router: Router) {
    this.registerForm = this.fregisterb.group({
      username: ['', [
        Validators.required,
        Validators.maxLength(50)
      ]],
      motDePasse: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.pattern(/^.{6,}$/)
      ]],
      nom: ['', [
        Validators.required,
        Validators.maxLength(100)
      ]],
      latitude: [0, [
        Validators.required,
        Validators.min(-90),
        Validators.max(90)
      ]],
      longitude: [0, [
        Validators.required,
        Validators.min(-180),
        Validators.max(180)
      ]]
    });

  }
  onSubmit(): void {
    this.registrationError = '';
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          console.log('Registration successful!', response);
          this.router.navigate(['/']);
        },
        error: (err) => {
          if (err.status === 409) {
            this.registrationError = 'Username already exists';
          } else {
            this.registrationError = 'Registration failed. Please try again.';
          }
        },
      });
    } else {
      this.markFormGroupTouched(this.registerForm);
      console.warn('Invalid registration form');
    }
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

}
