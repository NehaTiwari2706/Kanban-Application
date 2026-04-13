import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    console.log('HomePage loaded');
  }

  openLogin(): void {
    this.router.navigate(['/login']);
    console.log('Navigating to Login Page');
  }

  openRegister(): void {
    this.router.navigate(['/register']);
    console.log('Navigating to Register Page');
  }

  goHome(): void {
    this.router.navigate(['/home']);
    console.log('Navigating to Home Page');
  }
}
