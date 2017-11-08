import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeControlComponent } from './employee-control.component';

describe('EmployeeControlComponent', () => {
  let component: EmployeeControlComponent;
  let fixture: ComponentFixture<EmployeeControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeControlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
