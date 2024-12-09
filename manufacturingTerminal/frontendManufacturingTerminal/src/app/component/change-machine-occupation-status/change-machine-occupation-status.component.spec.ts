import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeMachineOccupationStatusComponent } from './change-machine-occupation-status.component';

describe('ChangeMachineOccupationStatusComponent', () => {
  let component: ChangeMachineOccupationStatusComponent;
  let fixture: ComponentFixture<ChangeMachineOccupationStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeMachineOccupationStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeMachineOccupationStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
