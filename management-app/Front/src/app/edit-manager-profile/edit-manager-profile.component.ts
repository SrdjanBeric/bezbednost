import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-manager-profile',
  templateUrl: './edit-manager-profile.component.html',
  styleUrls: ['./edit-manager-profile.component.css']
})
export class EditManagerProfileComponent {
  
  public user: User=new User();

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getUser(params['id']).subscribe(res => {
        this.user = res;
      })
    });
  }
  goBack() {
    this.router.navigate(['/manager-profile/{id}', { id: this.route.snapshot.paramMap.get('id')}]);
  }

}
