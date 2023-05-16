import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-admin-all-users',
  templateUrl: './admin-all-users.component.html',
  styleUrls: ['./admin-all-users.component.css'],
})
export class AdminAllUsersComponent implements OnInit {
  constructor(private userService: UserService) {}
  users: any[] = [];

  ngOnInit(): void {
    this.userService.getUsers().subscribe((response) => {
      this.users = response;
      console.log(this.users);
    });
  }
}
