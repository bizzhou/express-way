import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserReservationDialogComponent } from './user-reservation-dialog.component';

describe('UserReservationDialogComponent', () => {
  let component: UserReservationDialogComponent;
  let fixture: ComponentFixture<UserReservationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserReservationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserReservationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
