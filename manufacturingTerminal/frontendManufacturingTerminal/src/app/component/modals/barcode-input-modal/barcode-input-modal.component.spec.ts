import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarcodeInputModalComponent } from './barcode-input-modal.component';

describe('BarcodeInputModalComponent', () => {
  let component: BarcodeInputModalComponent;
  let fixture: ComponentFixture<BarcodeInputModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BarcodeInputModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BarcodeInputModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
