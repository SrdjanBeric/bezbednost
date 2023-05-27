import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-edit-admin-profile',
  templateUrl: './edit-admin-profile.component.html',
  styleUrls: ['./edit-admin-profile.component.css'],
})
export class EditAdminProfileComponent implements OnInit {
  adminId: number = 0;
  admin: any;
  editMode: boolean = false;
  adminEdit: any = {};
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.myInfo().subscribe((response) => {
      console.log(response);
      this.admin = response;
      console.log(this.admin.username);
    });
  }

  enableEdit() {
    this.editMode = true;
  }

  submitForm(form: NgForm) {
    if (form.valid) {
      this.userService.updateUser(this.admin).subscribe((response) => {
        console.log(`nesto se desava`);
        console.log(response);
        this.editMode = false;
      });
    }
  }
}
