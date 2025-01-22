import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeaturesService {
  private apiUrl = 'http://localhost:8080/api/pigeons';
  constructor(private http: HttpClient) {}


  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if (!token) {
      throw new Error('User is not authenticated');
    }

    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Add pigeon
  addPigeon(pigeon: any): Observable<any> {
    const headers = this.getHeaders();

    return this.http.post(`${this.apiUrl}/add`, pigeon, { headers, responseType: 'text' }).pipe(
      catchError(error => {
        console.error('Error adding pigeon', error);
        return throwError(error);
      })
    );
  }


  // Get pigeons
  getPigeons(): Observable<any> {
    const headers = this.getHeaders();

    return this.http.get<any>(`${this.apiUrl}/user`, { headers }).pipe(
      catchError(error => {
        console.error('Error fetching pigeons', error);
        return throwError(error);
      })
    );
  }
}
