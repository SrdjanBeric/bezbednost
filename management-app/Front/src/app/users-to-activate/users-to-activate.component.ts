import { Component, OnInit } from '@angular/core';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-users-to-activate',
  templateUrl: './users-to-activate.component.html',
  styleUrls: ['./users-to-activate.component.css'],
})
export class UsersToActivateComponent implements OnInit {
  usersToActivate: any[] = [];
  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.getUsersToActivate().subscribe((users) => {
      this.usersToActivate = users;
    });
  }
}
