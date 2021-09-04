import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { WsService } from '../services/ws.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TypeSocketService } from '../services/type-socket.service';
import { AgentType } from '../model/agent-type';


@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  liveData$ = this.typeSocketService.messages$;

  constructor(private userService : UserService, private typeSocketService: TypeSocketService) { 
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  types : AgentType[] =[];
  type : AgentType
  agentName : string = ''

  public start() {
    if(this.types.includes(this.type) && this.agentName.length > 0)
      this.userService.startAgent(this.type, this.agentName).subscribe()
  }

  ngOnInit(): void {
    this.typeSocketService.connect();
    this.userService.getAgentTypes().subscribe(
      data => this.types = data
    )
  }

  handleMessage(message : string) {
    this.types = JSON.parse(message);
  }
}
