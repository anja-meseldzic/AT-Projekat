import { Injectable } from '@angular/core';
import { EMPTY, Subject } from 'rxjs';
import { catchError, share, switchAll, tap } from 'rxjs/operators';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class TypeSocketService {

  constructor() { }

  private WS_ENDPOINT : string = 'ws://localhost:8081/ChatWAR/ws/type';

  private socket$: WebSocketSubject<any>;
  private messagesSubject$ = new Subject();
  public messages$ = this.messagesSubject$.pipe(switchAll(), share(), catchError(e => { throw e }));
  
  public connect(): void {
    if (!this.socket$ || this.socket$.closed) {
      this.socket$ = this.getNewWebSocket();
      const messages = this.socket$.pipe(
        tap({
          error: error => console.log(error),
        }), catchError(_ => EMPTY));
      this.messagesSubject$.next(messages);
      this.socket$.subscribe({
        complete: () => { console.log('connection with agent socket closed') }
      })
    }
  }
  
  private getNewWebSocket() {
    return webSocket({url: this.WS_ENDPOINT, deserializer: msg => msg.data});
  }

  sendMessage(msg: any) {
    this.socket$.next(msg);
  }

  close() {
    this.socket$.complete();
  }
}
