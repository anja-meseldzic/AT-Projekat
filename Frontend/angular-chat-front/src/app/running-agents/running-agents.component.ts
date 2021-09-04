import { Component, OnInit } from '@angular/core';
import { Aid } from '../model/aid';
import { UserService } from '../services/user.service';
import { WsService } from '../services/ws.service';

@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  liveData$ = this.wsSocket.messages$;

  constructor(private userService : UserService, private wsSocket : WsService) {
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  running : Aid[] = []
  
  ngOnInit(): void {
    this.wsSocket.connect();
    this.userService.getRunningAgents().subscribe(
      data => this.running = data
    )
  }

  public stop(agent : Aid) {
    this.userService.stopAgent(agent).subscribe()
  }

  handleMessage(msg : string) {
    this.running = JSON.parse(msg)
  }

}
