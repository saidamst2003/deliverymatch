// navbar.component.ts
import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  role: 'conducteur' | 'expediteur' | 'admin';
  isVerified: boolean;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class NavbarComponent {

}

