import { Injectable } from '@angular/core';
import {jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('token') ? true : false;
  }

  getFullName(): string {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.fullName; 
    }
    return '';
  }

  getUserRole(): string | null {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.authorities[0]; // Assuming you have a roles array in your JWT
    }
    return null;
  }
}
