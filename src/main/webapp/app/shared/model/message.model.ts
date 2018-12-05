export interface IMessage {
    id?: number;
    messageId?: number;
    messageKey?: string;
    messageValue?: string;
    language?: string;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public messageId?: number,
        public messageKey?: string,
        public messageValue?: string,
        public language?: string
    ) {}
}
