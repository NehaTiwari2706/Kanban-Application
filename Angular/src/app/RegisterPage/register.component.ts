import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder, 
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.registerForm = this.formBuilder.group({
      fullname: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      termsAccepted: [false, Validators.requiredTrue]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(group: FormGroup): { [key: string]: any } | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    
    if (password && confirmPassword && password !== confirmPassword) {
      return { 'passwordMismatch': true };
    }
    return null;
  }

  get f() {
    return this.registerForm.controls;
  }

  handleRegister(): void {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    const { fullname, email, password, confirmPassword, termsAccepted } = this.registerForm.value;

    if (!fullname || !email || !password || !confirmPassword) {
      alert('Please fill in all fields');
      return;
    }

    if (!this.validateEmail(email)) {
      alert('Please enter a valid email');
      return;
    }

    if (password.length < 6) {
      alert('Password must be at least 6 characters long');
      return;
    }

    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    if (!termsAccepted) {
      alert('You must accept the Terms and Conditions');
      return;
    }

    // call backend API
    this.authService.register({
      fullName: fullname,
      email: email,
      password: password,
      confirmPassword: confirmPassword
    }).subscribe({
      next: (response) => {
        if (response.success) {
          alert(response.message || 'Account created successfully!');
          this.router.navigate(['/login']);
        } else {
          alert('Registration failed: ' + response.message);
        }
      },
      error: (error) => {
        console.error('Error:', error);
        alert('Registration error: ' + (error.error?.message || error.message));
      }
    });
  }

  goHome(): void {
    this.router.navigate(['/home']);
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
}
