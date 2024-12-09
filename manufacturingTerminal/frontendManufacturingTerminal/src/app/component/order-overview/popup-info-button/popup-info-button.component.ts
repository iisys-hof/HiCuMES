import {
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  ElementRef,

  Input
} from '@angular/core';
import {ProgressAnimationEnd} from "@angular/material/progress-bar";
import {animate, style, transition, trigger} from "@angular/animations";
@Component({
  selector: 'app-popup-info-button',
  templateUrl: './popup-info-button.component.html',
  styleUrls: ['./popup-info-button.component.scss'],
  animations: [
    trigger('animateFade', [
      transition(':enter', [style({opacity: '0'}), animate(500)]),
      transition(':leave', [style({opacity: '1'}), animate(500, style({opacity: '0'}))]),
    ]),
    trigger('animateFadeFast', [
      transition(':enter', [style({opacity: '0'}), animate(150)]),
      transition(':leave', [style({opacity: '1'}), animate(150, style({opacity: '0'}))]),
    ])],
})
export class PopupInfoButtonComponent implements AfterViewChecked{

  popupMarginLeft = 0;
  buttonPressProgressValue = 0;
  interval: any;
  buttonPressedFinished = false;
  showPopup = false;
  showLoading = false;

  @Input()
  popupWidth = "87";
  @Input()
    icon: string = "";
  @Input()
    buttonText: string = "START";
  @Input()
    popupText: string = "Zum Starten halten";

  constructor(private _elementRef : ElementRef, private cdRef : ChangeDetectorRef ) {
  }

  ngAfterViewChecked(): void {

    let widthButton = this._elementRef.nativeElement.getElementsByClassName("start_button")[0].offsetWidth;
    this.popupMarginLeft = 0-((Number(this.popupWidth)-widthButton)/2);
    this.cdRef.detectChanges();
  }
  handleMouseDown(event: MouseEvent) {
    this.showPopup = true;
    event.stopImmediatePropagation();
    if (event.button === 0) {
      this.startTimer();
    }
  }

  handleTouchStart(event: TouchEvent) {
    this.showPopup = true;
    if (event.touches.length === 1) {
      this.startTimer();
    }
  }

  startTimer() {
    let tickTimeout = 150;
    this.interval = setInterval(() => {
      this.buttonPressProgressValue += 4
      if(this.buttonPressProgressValue > 220)
      {
        this.stopTimer()
      }
    }, tickTimeout);
  }

  stopTimer() {
    this.buttonPressProgressValue = 0;
    this.buttonPressedFinished = false;
    this.showPopup = false;
    clearInterval(this.interval);
  }

  handleMouseUp() {
    this.stopTimer()
  }

  handleMouseLeave() {
    this.stopTimer()
  }

  end($event: ProgressAnimationEnd) {
    this.buttonPressedFinished = this.buttonPressProgressValue > 99;
    this.showLoading = this.buttonPressProgressValue > 99;
  }

  stopLoading() {
    this.showLoading = false;
  }
}
