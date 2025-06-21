import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet, Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { NavbarComponent } from '../../parcials/navbar/navbar';
import { Sidebar } from '../../parcials/sidebar/sidebar';
import { filter, map } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  imports: [CommonModule, Sidebar, NavbarComponent, RouterOutlet],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class LayoutComponent implements OnInit {
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);

  isFullScreen = false;

  ngOnInit() {
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        map(() => {
          let route = this.activatedRoute.firstChild;
          while (route?.firstChild) {
            route = route.firstChild;
          }
          return route?.snapshot.data['fullScreen'] || false;
        })
      )
      .subscribe((fullScreen: boolean) => {
        this.isFullScreen = fullScreen;
      });
  }
}
