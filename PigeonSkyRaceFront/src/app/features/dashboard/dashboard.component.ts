import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FeaturesService} from '../features.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,

  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  pigeons: any[] = [];
  pigeonForm: FormGroup;
  isAuthenticated: boolean = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private featuresService: FeaturesService, private fb: FormBuilder) {
    this.pigeonForm = this.fb.group({
      numeroBague: ['', [
        Validators.required,
        Validators.pattern(/^[mf][0-9]+$/),
      ],
      ],
      couleur: ['', [Validators.required]],
      age: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.checkAuthentication();
    this.getPigeons();
  }

  checkAuthentication(): void {
    this.isAuthenticated = !!localStorage.getItem('authToken');
  }

  getPigeons(): void {
    this.featuresService.getPigeons().subscribe({
      next: (data) => {
        this.pigeons = data;
      },
      error: (error) => {
        console.error('Error fetching pigeons:', error);
        this.errorMessage = 'Failed to load pigeons. Please try again later.';
      }
    });
  }


  addPigeon(): void {
    if (this.pigeonForm.invalid) {
      this.errorMessage = 'Please fill in all fields correctly.';
      return;
    }

    if (!this.isAuthenticated) {
      this.errorMessage = 'You must be authenticated to add a pigeon.';
      return;
    }

    this.featuresService.addPigeon(this.pigeonForm.value).subscribe({
      next: () => {
        this.successMessage = 'Pigeon added successfully!';
        this.errorMessage = null;
        this.getPigeons();
        this.pigeonForm.reset();
      },
      error: (error) => {
        console.error('Error adding pigeon:', error);
        this.successMessage = null;
        this.errorMessage = 'Failed to add pigeon. Please try again later.';
      }
    });
  }
}
