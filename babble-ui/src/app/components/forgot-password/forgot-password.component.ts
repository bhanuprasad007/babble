import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
  email!: string;

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    // Implement logic to reset the password
    console.log('Email submitted:', this.email);
    // You can call a service to trigger the password reset process here
  }
}
