import { AfterViewInit, Component } from '@angular/core';
import build from "../build";
import { environment } from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {

  title = 'frontendHiCuMESLite';
  showFiller = false;

  constructor() {}

  ngAfterViewInit(): void {
  }
}
