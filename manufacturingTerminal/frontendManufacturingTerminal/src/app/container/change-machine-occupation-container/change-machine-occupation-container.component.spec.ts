import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeMachineOccupationContainerComponent } from './change-machine-occupation-container.component';

describe('ChangeMachineOccupationContainerComponent', () => {
  let component: ChangeMachineOccupationContainerComponent;
  let fixture: ComponentFixture<ChangeMachineOccupationContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeMachineOccupationContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeMachineOccupationContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
