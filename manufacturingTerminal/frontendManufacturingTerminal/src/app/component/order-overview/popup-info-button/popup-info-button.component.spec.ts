import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupInfoButtonComponent } from './popup-info-button.component';

describe('PopupInfoButtonComponent', () => {
  let component: PopupInfoButtonComponent;
  let fixture: ComponentFixture<PopupInfoButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PopupInfoButtonComponent]
    });
    fixture = TestBed.createComponent(PopupInfoButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
