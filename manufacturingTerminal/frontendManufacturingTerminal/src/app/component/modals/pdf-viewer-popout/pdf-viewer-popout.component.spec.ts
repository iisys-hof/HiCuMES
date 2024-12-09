import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfViewerPopoutComponent } from './pdf-viewer-popout.component';

describe('PdfViewerPopoutComponent', () => {
  let component: PdfViewerPopoutComponent;
  let fixture: ComponentFixture<PdfViewerPopoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PdfViewerPopoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfViewerPopoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
