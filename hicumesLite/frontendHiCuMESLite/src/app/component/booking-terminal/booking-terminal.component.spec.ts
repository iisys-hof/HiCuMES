import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingTerminalComponent } from './booking-terminal.component';

describe('BookingTerminalComponent', () => {
  let component: BookingTerminalComponent;
  let fixture: ComponentFixture<BookingTerminalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookingTerminalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingTerminalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
