import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8081/api/auth';

    constructor(private http: HttpClient) { }

    register(data: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, data);
    }

    login(credentials: any) {
        return this.http.post<any>(`${this.apiUrl}/login`, credentials).pipe(
            tap(response => {
                // Check if the login was successful and the token exists
                if (response && response.token) {
                    // Save the token to localStorage
                    localStorage.setItem('jwt_token', response.token);
                }
            })
        );
    }

    // Helper method to retrieve the token anywhere in the app
    getToken(): string | null {
        return localStorage.getItem('jwt_token');
    }

    // Update your logout method to clear the token
    logout() {
        localStorage.removeItem('jwt_token');
        return this.http.post(`${this.apiUrl}/logout`, {});
    }
}