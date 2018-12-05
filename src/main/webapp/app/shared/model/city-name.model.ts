export interface ICityName {
    id?: number;
    cityNameId?: number;
    cityCode?: string;
    cityName?: string;
    language?: string;
}

export class CityName implements ICityName {
    constructor(
        public id?: number,
        public cityNameId?: number,
        public cityCode?: string,
        public cityName?: string,
        public language?: string
    ) {}
}
