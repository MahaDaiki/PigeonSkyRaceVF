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
  successMessage: string | null = null;

  constructor(private featuresService: FeaturesService, private fb: FormBuilder) {
    this.pigeonForm = this.fb.group({
      numeroBague: ['', [Validators.required]],
      couleur: ['', [Validators.required]],
      age: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.getPigeons();
  }


  getPigeons(): void {
    this.featuresService.getPigeons().subscribe((data) => {
      this.pigeons = data;
    });
  }

  // Method to add a pigeon using the reactive form data
  addPigeon(): void {
    console.log(this.pigeonForm.value);
    if (this.pigeonForm.valid) {
      this.featuresService.addPigeon(this.pigeonForm.value).subscribe(
        (message) => {
          this.successMessage = message;
          this.getPigeons();
          this.pigeonForm.reset();
        },
        (error) => {
          console.error('Error adding pigeon', error);
        }
      );
    }
  }
}
