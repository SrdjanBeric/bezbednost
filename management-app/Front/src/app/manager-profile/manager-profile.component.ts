import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
@Component({
  selector: 'manager-user-profile',
  templateUrl: './manager-profile.component.html',
  styleUrls: ['./manager-profile.component.css']
})
export class ManagerProfileComponent {
  
  public user: User=new User();

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getUser(params['id']).subscribe(res => {
        this.user = res;
        this.user.role=res.role.name;
      })
    });
  }
  goToEditPage() {
    this.router.navigate(['/edit-manager-profile/{id}', { id: this.route.snapshot.paramMap.get('id')}]);
  }

  viewProjects()
  {

  }
}
