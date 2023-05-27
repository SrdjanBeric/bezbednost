import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRegAdminComponent } from './admin-reg-admin.component';

describe('AdminRegAdminComponent', () => {
  let component: AdminRegAdminComponent;
  let fixture: ComponentFixture<AdminRegAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminRegAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRegAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
