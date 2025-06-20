import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavbarComponent} from '../../parcials/navbar/navbar';
import {Sidebar} from '../../parcials/sidebar/sidebar';

@Component({
  selector: 'app-layout',
  imports: [
    Sidebar,
    NavbarComponent,
    RouterOutlet
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.css'
})
export class LayoutComponent {

}
