import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {LayoutComponent} from './components/layout/layout/layout';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,LayoutComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'delivery-frontend';
}
