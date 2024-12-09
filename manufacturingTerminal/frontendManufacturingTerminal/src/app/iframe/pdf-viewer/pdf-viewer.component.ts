import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-pdf-viewer',
  templateUrl: './pdf-viewer.component.html',
  styleUrls: ['./pdf-viewer.component.scss']
})
export class PdfViewerComponent {

  constructor( private activatedRoute: ActivatedRoute) {
    let param = this.activatedRoute.snapshot.queryParams
    console.log(activatedRoute)
    if(param) {
      this.src = param['src'];
    }
  }
  src: string = '';

}
