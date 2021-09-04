import { Aid } from "./aid";

export class ACLMessage {
    constructor(
        public performative : string,
        public sender : Aid,
        public receivers : Aid[],
        public replyTo : Aid,
        public content : string,
        public contentObj : Object,
        public userArgs : Object,
        public language : string,
        public encoding : string,
        public ontology : string,
        public protocol : string,
        public conversationId : string,
        public replyWith : string,
        public inReplyTo : string,
        public replyBy : number
    ) {}
}
