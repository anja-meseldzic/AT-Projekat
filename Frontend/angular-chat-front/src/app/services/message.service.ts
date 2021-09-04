import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ACLMessage } from '../model/aclmessage';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http : HttpClient) { }

  private baseUrl : string = 'http://localhost:8081/ChatWAR/rest/chat/messages/';

  sendMessage(message: ACLMessage) : Observable<any> {
    return this.http.post(this.baseUrl, message);
  }

  getPerformatives() : Observable<string[]>{
    return this.http.get<string[]>(this.baseUrl);
  }
}
