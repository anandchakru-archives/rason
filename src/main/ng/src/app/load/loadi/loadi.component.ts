import { Component, OnInit } from '@angular/core';
import { Loadi } from '../../model/ui/loadi';

@Component({
  selector: 'app-loadi',
  templateUrl: './loadi.component.html',
  styleUrls: ['./loadi.component.scss']
})
export class LoadiComponent implements OnInit {
  public loadis: Loadi[] = new Array<Loadi>();
  constructor() { }

  ngOnInit() {  }

}
