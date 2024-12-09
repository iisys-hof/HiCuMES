import { AfterViewInit, Component } from '@angular/core';
import build from "../build";
import { environment } from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {

  title = 'frontendManufacturingTerminal';
  showFiller = false;

  constructor() {}

  ngAfterViewInit(): void {
    $.fn.dataTable.ext.errMode = 'none';
    console.log(
      `\nBuild Info:\n\n` +
      `❯ Environment: ${environment.production ? "production 🏭" : "development 🚧"}\n` +
      `❯ Build Timestamp: ${build.buildTimestamp}\n` +
      `❯ Commit Hash: ${build.commitHash}\n`,
    );
  }
}
