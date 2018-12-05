export interface ICountryName {
    id?: number;
    countryNameId?: number;
    countryCode?: string;
    countryName?: string;
    language?: string;
}

export class CountryName implements ICountryName {
    constructor(
        public id?: number,
        public countryNameId?: number,
        public countryCode?: string,
        public countryName?: string,
        public language?: string
    ) {}
}
