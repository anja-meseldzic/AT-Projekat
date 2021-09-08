import { Component, OnInit } from '@angular/core';
import { LoggerService } from '../services/logger.service';

@Component({
  selector: 'app-console-logger',
  templateUrl: './console-logger.component.html',
  styleUrls: ['./console-logger.component.css']
})
export class ConsoleLoggerComponent implements OnInit {

  liveData$ = this.loggerSocket.messages$;

  constructor(private loggerSocket : LoggerService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  logs : string[] = []

  ngOnInit(): void {
    this.loggerSocket.connect()
  }

  handleMessage(msg : string) {
    console.log(msg)
    this.logs.push(msg)
    console.log(this.logs)
  }

  clear() {
    this.logs = []
  }

}
