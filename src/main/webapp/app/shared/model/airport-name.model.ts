export interface IAirportName {
    id?: number;
    airportNameId?: number;
    airportCode?: string;
    language?: string;
}

export class AirportName implements IAirportName {
    constructor(public id?: number, public airportNameId?: number, public airportCode?: string, public language?: string) {}
}
