import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoteFormFieldComponent } from './note-form-field.component';

describe('NoteFormFieldComponent', () => {
  let component: NoteFormFieldComponent;
  let fixture: ComponentFixture<NoteFormFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoteFormFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteFormFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
