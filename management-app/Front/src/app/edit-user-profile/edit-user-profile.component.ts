import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth.service';
@Component({
  selector: 'app-user-profile',
  templateUrl: './edit-user-profile.component.html',
  styleUrls: ['./edit-user-profile.component.css']
})
export class EditUserProfileComponent {
  
  public user: User=new User();
  public originalUsername: string="";
  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router,private authService:AuthService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getMyInfo().subscribe(res => {
        this.user = res;
        this.originalUsername=this.user.username;
      })
    });
  }
  goBack() {
    this.router.navigate(['/user-profile/']);
  }
  submit(){
    this.userService.update(this.user).subscribe();
    if(this.originalUsername!=this.user.username){
      this.authService.logout();
      this.router.navigate(['/login-email']);
    }
  }

}
