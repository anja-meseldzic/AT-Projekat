import { AgentCenter } from "./agent-center";
import { AgentType } from "./agent-type";

export class Aid {
    constructor(
        public name : string,
        public host : AgentCenter,
        public type : AgentType
    ) {}
}
