import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenService} from './services/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private Url = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient,  private tokenService: TokenService) { }

  register(
    data:{
      username: string;
      motDePasse: string;
      nom: string;
      latitude: number;
      longitude: number;
    }
  ): Observable<any>{
    return this.http.post(`${this.Url}/register`, data);
  }

  login(credentials: { username: string; motDePasse: string }): Observable<string> {
    return this.http.post(`${this.Url}/login`, credentials, { responseType: 'text' });
  }

  logout(): void {
    this.tokenService.removeToken();
  }
  storeToken(token: string): void {
    this.tokenService.setToken(token);
  }

  getToken(): string | null {
    return this.tokenService.getToken();
  }
}
