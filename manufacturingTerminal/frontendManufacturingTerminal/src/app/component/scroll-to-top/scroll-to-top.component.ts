import {Component, HostListener, Inject, Input, OnInit} from '@angular/core';
import {DOCUMENT} from "@angular/common";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-scroll-to-top',
  templateUrl: './scroll-to-top.component.html',
  styleUrls: ['./scroll-to-top.component.scss']
})
export class ScrollToTopComponent implements OnInit {

  oldScrollValue: number = 0;
  lastScrollWasUp: boolean = false;
  sameScrollDirectionAmount: number = 0;
  windowScrolled: boolean = true;

  @Input()
  scrollEvent!: Observable<Event>;
  constructor() { }

  ngOnInit() {
    this.scrollEvent.subscribe((event: Event) => this.onScroll(event))
  }

  onScroll($event: any) {
    //console.log($event)
    let scrollValue = $event.target.scrollTop
    if (scrollValue - this.oldScrollValue < 0) {
      if (this.lastScrollWasUp) {
        this.sameScrollDirectionAmount++
      } else {
        this.sameScrollDirectionAmount = 0
      }

      if (this.sameScrollDirectionAmount > 2) {
        this.windowScrolled = true
      }
      this.lastScrollWasUp = true
    } else if (scrollValue - this.oldScrollValue > 0) {
      if (!this.lastScrollWasUp) {
        this.sameScrollDirectionAmount++
      } else {
        this.sameScrollDirectionAmount = 0
      }
      if (this.sameScrollDirectionAmount > 2) {
        this.windowScrolled = false
      }
      this.lastScrollWasUp = false
    }
    this.oldScrollValue = scrollValue
  }

}
