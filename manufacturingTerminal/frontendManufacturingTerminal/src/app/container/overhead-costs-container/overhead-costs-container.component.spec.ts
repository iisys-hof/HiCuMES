import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverheadCostsContainerComponent } from './overhead-costs-container.component';

describe('OverheadCostsContainerComponent', () => {
  let component: OverheadCostsContainerComponent;
  let fixture: ComponentFixture<OverheadCostsContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverheadCostsContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OverheadCostsContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
