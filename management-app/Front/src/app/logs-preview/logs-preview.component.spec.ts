import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogsPreviewComponent } from './logs-preview.component';

describe('LogsPreviewComponent', () => {
  let component: LogsPreviewComponent;
  let fixture: ComponentFixture<LogsPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogsPreviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogsPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
