import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { LoginPasswordPageComponent } from './login-password-page/login-password-page.component';
import { LoginEmailPageComponent } from './login-email-page/login-email-page.component';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpClientModule,
} from '@angular/common/http';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { EditUserProfileComponent } from './edit-user-profile/edit-user-profile.component';
import { UserCVComponent } from './user-cv/user-cv.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AuthGuard } from './service/auth.guard';
import { UsersToActivateComponent } from './users-to-activate/users-to-activate.component';
import { AdminProjectPageComponent } from './admin-project-page/admin-project-page.component';
import { AdminAllUsersComponent } from './admin-all-users/admin-all-users.component';
import { AddEngineerPageComponent } from './add-engineer-page/add-engineer-page.component';
import { ManagerProfileComponent } from './manager-profile/manager-profile.component';
import { EditManagerProfileComponent } from './edit-manager-profile/edit-manager-profile.component';
import { ManagerProjectsComponent } from './user-projects/user-projects.component';
import { GuestGuard } from './service/guest.guard';
import { FrontTokenExtractComponent } from './front-token-extract/front-token-extract.component';
import { EditAdminProfileComponent } from './edit-admin-profile/edit-admin-profile.component';
import { AuthInterceptor } from './service/interceptor';
import { AdminRegAdminComponent } from './admin-reg-admin/admin-reg-admin.component';

const appRoutes: Routes = [
  {
    path: 'registration',
    component: RegistrationPageComponent,
    canActivate: [GuestGuard],
  },
  {
    path: 'login-pass',
    component: LoginPasswordPageComponent,
    canActivate: [GuestGuard],
  },
  {
    path: 'login-email',
    component: LoginEmailPageComponent,
    canActivate: [GuestGuard],
  },
  { path: 'user-profile', component: UserProfileComponent },
  { path: 'edit-user-profile', component: EditUserProfileComponent },
  { path: 'user-cv', component: UserCVComponent },
  { path: 'manager-profile/:id', component: ManagerProfileComponent },
  { path: 'edit-manager-profile/:id', component: EditManagerProfileComponent },
  {
    path: 'user-projects',
    component: ManagerProjectsComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['SOFTWARE_ENGINEER'] },
  },
  {
    path: 'edit-admin-profile',
    component: EditAdminProfileComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
  {
    path: 'edit-admin-profile',
    component: EditAdminProfileComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
  {
    path: 'admin',
    component: AdminPageComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
  {
    path: 'users-activate',
    component: UsersToActivateComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
  {
    path: 'admin-project',
    component: AdminProjectPageComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN', 'PROJECT_MANAGER'] },
  },
  {
    path: 'admin-users',
    component: AdminAllUsersComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
  {
    path: 'admin-reg-admin',
    component: AdminRegAdminComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN'] },
  },
  {
    path: 'add-engineer',
    component: AddEngineerPageComponent,
    canActivate: [AuthGuard],
    data: { allowedRoles: ['ADMIN', 'PROJECT_MANAGER'] },
  },
  {
    path: 'login/:token',
    component: FrontTokenExtractComponent,
  },
  {
    path: 'login/:token',
    component: FrontTokenExtractComponent,
  },
];

@NgModule({
  declarations: [
    AppComponent,
    RegistrationPageComponent,
    LoginPasswordPageComponent,
    LoginEmailPageComponent,
    UserProfileComponent,
    EditUserProfileComponent,
    UserCVComponent,
    AdminPageComponent,
    UsersToActivateComponent,
    AdminProjectPageComponent,
    AdminAllUsersComponent,
    AddEngineerPageComponent,
    ManagerProfileComponent,
    EditManagerProfileComponent,
    ManagerProjectsComponent,
    FrontTokenExtractComponent,
    EditAdminProfileComponent,
    AdminRegAdminComponent,
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
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
