import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingTerminalViewComponent } from './booking-terminal-view.component';

describe('BookingTerminalViewComponent', () => {
  let component: BookingTerminalViewComponent;
  let fixture: ComponentFixture<BookingTerminalViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookingTerminalViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingTerminalViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
