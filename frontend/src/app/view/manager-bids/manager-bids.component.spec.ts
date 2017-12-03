import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerBidsComponent } from './manager-bids.component';

describe('ManagerBidsComponent', () => {
  let component: ManagerBidsComponent;
  let fixture: ComponentFixture<ManagerBidsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerBidsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerBidsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
