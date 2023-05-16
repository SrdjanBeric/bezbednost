import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent {
  
  public user: User=new User();

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getUser(params['id']).subscribe(res => {
        this.user = res;
        this.user.role=res.role.name;
        console.log(res.role.name);
      })
    });
  }
  goToEditPage() {
    this.router.navigate(['/edit-user-profile/{id}', { id: this.route.snapshot.paramMap.get('id')}]);
  }
  goToCV()
  {
    this.router.navigate(['/user-cv/{id}', { id: this.route.snapshot.paramMap.get('id')}]);

  }
  viewProjects()
  {

  }
}
