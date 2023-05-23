import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
@Component({
  selector: 'app-user-cv',
  templateUrl: './user-cv.component.html',
  styleUrls: ['./user-cv.component.css']
})
export class UserCVComponent {
  
  public skills: string[]=[];

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getSkills().subscribe(res => {
        this.skills = res;
        console.log(this.skills);
      })
    });
  }
  goBack() {
    this.router.navigate(['/user-profile/']);
  }
  EditCV()
  {

  }
}
