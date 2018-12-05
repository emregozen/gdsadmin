export interface IAirline {
    id?: number;
    airlineId?: number;
    airlineCode?: string;
    airlineName?: string;
}

export class Airline implements IAirline {
    constructor(public id?: number, public airlineId?: number, public airlineCode?: string, public airlineName?: string) {}
}
