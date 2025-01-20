import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {select, Store} from '@ngrx/store';

import {Observable} from 'rxjs';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fregisterb:  FormBuilder,private authService: AuthService) {
    this.registerForm = this.fregisterb.group({
      username: ['', Validators.required],
      motDePasse: ['', [Validators.required, Validators.minLength(6)]],
      nom: ['', Validators.required],
      latitude: [0, Validators.required],
      longitude: [0, Validators.required],
    });

  }
  onSubmit(): void {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          console.log('Registration successful!', response);
        },
        error: (err) => {
          console.error('Registration failed:', err);
        },
      });
    } else {
      console.warn('Invalid registration form');
    }
  }

}
