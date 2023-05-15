import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { LoginPasswordPageComponent } from './login-password-page/login-password-page.component';
import { LoginEmailPageComponent } from './login-email-page/login-email-page.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AuthGuard } from './service/auth.guard';

const appRoutes: Routes = [
  { path: 'registration', component: RegistrationPageComponent },
  { path: 'login-pass', component: LoginPasswordPageComponent },
  { path: 'login-email', component: LoginEmailPageComponent },
  {
    path: 'admin',
    component: AdminPageComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
];

@NgModule({
  declarations: [
    AppComponent,
    RegistrationPageComponent,
    LoginPasswordPageComponent,
    LoginEmailPageComponent,
    AdminPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
