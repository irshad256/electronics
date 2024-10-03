import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token: string) { 
    localStorage.setItem("token", token);
  }

  get token(){
    return localStorage.getItem("token") as string;
  }

   // Decode the JWT token and check expiration
  isTokenExpired(): boolean {
    const token = this.token;
    if (!token) {
      return true;
    }

    const decodedToken = this.decodeToken(token);
    if (decodedToken && decodedToken.exp) {
      const expirationTime = decodedToken.exp * 1000; // Convert to milliseconds
      return Date.now() > expirationTime;
    }
    return true;
  }

  private decodeToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch (error) {
      return null;
    }
  }

}
