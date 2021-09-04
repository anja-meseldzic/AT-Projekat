import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AgentType } from '../model/agent-type';
import { AgentCenter } from '../model/agent-center';
import { Aid } from '../model/aid';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) { }
  private baseUrl : string = 'http://localhost:8081/ChatWAR/rest/chat/agents/';

  getAgentTypes(): Observable<AgentType[]>{
    return this.http.get<AgentType[]>(this.baseUrl + 'classes');
  }

  getRunningAgents(): Observable<Aid[]>{
    return this.http.get<Aid[]>(this.baseUrl + 'running');
  }

  startAgent(type:AgentType, name: string) : Observable<any>{
    return this.http.put(this.baseUrl + 'running/'+ name, type );
  }
  
  stopAgent(aid :Aid) : Observable<any>{
    return this.http.put(this.baseUrl + 'running', aid);
  }
}
