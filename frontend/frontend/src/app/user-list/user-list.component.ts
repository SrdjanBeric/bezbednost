import { Component, OnInit } from '@angular/core';
import { User } from '../data/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {


  public users: User[] = [];

  

  constructor(private userService:UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(res => {
      this.users = res;      
    });
    const userCopy = [...this.users]; 



  }
}
