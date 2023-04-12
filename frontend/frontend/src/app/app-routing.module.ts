import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { CertificateFormComponent } from './certificate-form/certificate-form.component';
<<<<<<< HEAD
import { CertificateListComponent } from './certificate-list/certificate-list.component';
=======
import { UserListComponent } from './user-list/user-list.component';
>>>>>>> 4521f927ba2a6edd2b58142dbb00a138843f7b5b

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'registration', component: RegistrationPageComponent },
  { path: 'certificate-form', component: CertificateFormComponent },
<<<<<<< HEAD
  { path: 'certificate-list', component: CertificateListComponent},
=======
  { path: 'user-list', component: UserListComponent}
>>>>>>> 4521f927ba2a6edd2b58142dbb00a138843f7b5b
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
