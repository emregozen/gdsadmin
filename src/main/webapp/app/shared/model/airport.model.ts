export interface IAirport {
    id?: number;
    airportId?: number;
    airportCode?: string;
    cityCode?: string;
    countryCode?: string;
    isDomestic?: boolean;
}

export class Airport implements IAirport {
    constructor(
        public id?: number,
        public airportId?: number,
        public airportCode?: string,
        public cityCode?: string,
        public countryCode?: string,
        public isDomestic?: boolean
    ) {
        this.isDomestic = this.isDomestic || false;
    }
}
