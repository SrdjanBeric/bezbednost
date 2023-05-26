import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import {AuthService} from '../service/auth.service';
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent {
  
  public user: User=new User();
  public type:string="";
  public hasCV:boolean=false;

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router,private authService:AuthService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getMyInfo().subscribe(res => {
        this.user = res;
        this.user.role=res.role.name;
        console.log(res.role.name);
        this.type=this.authService.getRole();
        if(this.type=="SOFTWARE_ENGINEER"){
        this.hasCV=true;
        }
        console.log(this.hasCV);
      })
    });
  }
  goToEditPage() {
    this.router.navigate(['/edit-user-profile']);
  }
  goToCV()
  {
    this.router.navigate(['/user-cv/']);

  }
  viewProjects()
  {
    if(this.type=='PROJECT_MANAGER')
    this.router.navigate(['/admin-project']);
    else 
    this.router.navigate(['/user-projects']);
  }
}
