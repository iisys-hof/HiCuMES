import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverheadCostsComponent } from './overhead-costs.component';

describe('OverheadCostsComponent', () => {
  let component: OverheadCostsComponent;
  let fixture: ComponentFixture<OverheadCostsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverheadCostsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OverheadCostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
