import { Component, OnInit } from '@angular/core';
import {
  trigger,
  transition,
  animate,
  style,
} from '@angular/animations';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss'],
  animations: [
    trigger('scrollAnimation', [
      transition('* => *', [
        style({ transform: 'translateX(-100%)' }),
        animate('2000ms linear', style({ transform: 'translateX(0%)' })),
      ]),
    ]),
  ],
})
export class LandingComponent implements OnInit {
  currentIndex = 0;
  features = [
    {
      name: 'Real-time messaging',
      image: 'assets/images/real-time-messaging.jpeg',
    },
    { name: 'Group chats', image: 'assets/images/group-chats.jpeg' },
    {
      name: 'Multimedia support',
      image: 'assets/images/multimedia-support.jpeg',
    },
    {
      name: 'Security features',
      image: 'assets/images/security-features.jpeg',
    },
  ];

  ngOnInit(): void {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.features.length;
    }, 3000);
  }
}
