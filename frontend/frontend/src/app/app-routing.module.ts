import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { CertificateFormComponent } from './certificate-form/certificate-form.component';
import { UserListComponent } from './user-list/user-list.component';
import { CertificateListComponent } from './certificate-list/certificate-list/certificate-list.component';
import { UserCertificates } from './user-certificates/user-certificates';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'registration', component: RegistrationPageComponent },
  { path: 'certificate-form', component: CertificateFormComponent },
  { path: 'user-list', component: UserListComponent },
  { path: 'certificate-list', component: CertificateListComponent },
  { path: 'user-certificates', component: UserCertificates },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
