import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionOrderModalComponent } from './collection-order-modal.component';

describe('CollectionOrderModalComponent', () => {
  let component: CollectionOrderModalComponent;
  let fixture: ComponentFixture<CollectionOrderModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CollectionOrderModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CollectionOrderModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
