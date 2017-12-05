import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoundtripFlightSearchComponent } from './roundtrip-flight-search.component';

describe('RoundtripFlightSearchComponent', () => {
  let component: RoundtripFlightSearchComponent;
  let fixture: ComponentFixture<RoundtripFlightSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoundtripFlightSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoundtripFlightSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
