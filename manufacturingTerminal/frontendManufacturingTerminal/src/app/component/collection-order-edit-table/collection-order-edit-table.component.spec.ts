import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionOrderEditTableComponent } from './collection-order-edit-table.component';

describe('CollectionOrderEditTableComponent', () => {
  let component: CollectionOrderEditTableComponent;
  let fixture: ComponentFixture<CollectionOrderEditTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CollectionOrderEditTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CollectionOrderEditTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
