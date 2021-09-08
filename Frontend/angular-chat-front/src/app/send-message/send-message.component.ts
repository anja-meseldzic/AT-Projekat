import { Component, OnInit } from '@angular/core';
import { ACLMessage } from '../model/aclmessage';
import { Aid } from '../model/aid';
import { MessageService } from '../services/message.service';
import { UserService } from '../services/user.service';
import { WsService } from '../services/ws.service';

@Component({
  selector: 'app-send-message',
  templateUrl: './send-message.component.html',
  styleUrls: ['./send-message.component.css']
})
export class SendMessageComponent implements OnInit {

  constructor(private agentService : UserService, private messageService : MessageService, private agentSocket : WsService) { 
    this.liveData$.subscribe({
      next : msg => this.handleMessage(msg as string)
    });
  }

  agents : Aid[] = []
  performatives : string[] = []

  sender : Aid;
  receiver : Aid;
  performative : string;
  username : string = '';
  password : string = '';
  senderUsername : string = '';
  receiverUsername : string = '';
  subject : string = '';
  content : string = '';


  liveData$ = this.agentSocket.messages$;

  ngOnInit(): void {
    this.agentSocket.connect();
    this.agentService.getRunningAgents().subscribe(
      data => { this.agents = data;
                this.messageService.getPerformatives().subscribe(
                  data => this.performatives = data
                )
      }
    )
  }

  public send() {
    if(this.agents.includes(this.sender) && this.agents.includes(this.receiver) && this.performatives.includes(this.performative)) {
      const userArgs = this.parseUserArgs();
      const content = this.getContent();
      const message : ACLMessage = new ACLMessage(this.performative, this.sender, [this.receiver], null, content, null, userArgs, '', '', '', '', '', '', '', 0);
      this.messageService.sendMessage(message).subscribe()
    }
  }

  parseUserArgs() {
    var result : Object = {}
    if(this.performative === 'LOG_IN' || this.performative === 'REGISTER') {
      result['username'] = this.username
      result['password'] = this.password
      return result;
    }
    if(this.performative === 'SEND_MESSAGE_ALL') {
      result['sender'] = this.senderUsername
      result['subject'] = this.subject
      result['content'] = this.content
      return result
     }
     if(this.performative === 'SEND_MESSAGE_USER') {
      result['sender'] = this.senderUsername
      result['receiver'] = this.receiverUsername
      result['subject'] = this.subject
      result['content'] = this.content
      return result
     }
     
     else {
      result['username'] = this.username
      return result;
     }
  }

  getContent() {
    var result =  this.content;
    return JSON.stringify(result);
  }

  handleMessage(msg : string) {
    this.agents = JSON.parse(msg)
  }

}
