import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MulticityFlightSearchComponent } from './multicity-flight-search.component';

describe('MulticityFlightSearchComponent', () => {
  let component: MulticityFlightSearchComponent;
  let fixture: ComponentFixture<MulticityFlightSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MulticityFlightSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MulticityFlightSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
