import {AfterViewChecked, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {fromEvent, Observable} from "rxjs";
import {map} from "rxjs/operators";


@Component({
  selector: 'app-scroll',
  templateUrl: './scroll.component.html',
  styleUrls: ['./scroll.component.scss']
})
export class ScrollComponent implements OnInit, AfterViewChecked{
  @Input() containerToScroll: HTMLElement | string | undefined;

   showScroll$: Observable<boolean> | undefined ;
  _containerToScroll: HTMLElement;
  scrollId = ""
  scrollBtn: HTMLElement | undefined | null;

  constructor() {

    this._containerToScroll =  <HTMLElement>document.getElementsByClassName("mat-sidenav-content")[0];


  }
  onScrollTop(): void {
    //console.log("typeof this.containerToScroll ", typeof this._containerToScroll );
    this._containerToScroll.scrollTo({top: 0, behavior: 'smooth'});
  }

  private positionScrollButton() {
    //console.log("this._containerToScroll.getBoundingClientRect()",this._containerToScroll.getBoundingClientRect());
    let top = this._containerToScroll.getBoundingClientRect().top + this._containerToScroll.getBoundingClientRect().height - 50;
    let left = this._containerToScroll.getBoundingClientRect().left + this._containerToScroll.getBoundingClientRect().width - 50;


    if (!this.scrollBtn) {
      this.scrollBtn = document.getElementById(this.scrollId);
    } else {
      this.scrollBtn.style.top = top + "px";
      this.scrollBtn.style.left = left + "px";
    }
  }

  ngOnInit(): void {
    //console.log("typeof this.containerToScroll ", typeof this.containerToScroll );
    //console.log("this.containerToScroll ",  this.containerToScroll );

    if (typeof this.containerToScroll === "string"){
      //console.log("document.getElementById(this.containerToScroll)", document.getElementById(this.containerToScroll))
      this._containerToScroll  = document.getElementById(this.containerToScroll) ?? this._containerToScroll;
    }
    else if (typeof this.containerToScroll === "object"){
      this._containerToScroll = this.containerToScroll;
    }

    this.scrollId = this._containerToScroll.id + "-scrollbtn"

    window.addEventListener("resize", x => {
      this.positionScrollButton();
    });

    if( this._containerToScroll !== undefined) {
      this.showScroll$ = fromEvent(
        this._containerToScroll,
        'scroll'
      ).pipe(
        map(() =>
          this._containerToScroll.scrollTop > 0
        )
      );
    }
  }
  ngAfterViewChecked() {
    this.positionScrollButton();
  }
}
