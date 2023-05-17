import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-user-profile',
  templateUrl: './edit-user-profile.component.html',
  styleUrls: ['./edit-user-profile.component.css']
})
export class EditUserProfileComponent {
  
  public user: User=new User();

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getMyInfo().subscribe(res => {
        this.user = res;
      })
    });
  }
  goBack() {
    this.router.navigate(['/user-profile/{id}', { id: this.route.snapshot.paramMap.get('id')}]);
  }

}
