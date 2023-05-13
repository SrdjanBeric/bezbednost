import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login-password-page',
  templateUrl: './login-password-page.component.html',
  styleUrls: ['./login-password-page.component.css'],
})
export class LoginPasswordPageComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  submitForm(form: NgForm) {}
}
