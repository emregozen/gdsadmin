export interface ICountry {
    id?: number;
    countryId?: number;
    countryCode?: string;
    country3Code?: string;
    phoneCode?: string;
    numericCode?: string;
}

export class Country implements ICountry {
    constructor(
        public id?: number,
        public countryId?: number,
        public countryCode?: string,
        public country3Code?: string,
        public phoneCode?: string,
        public numericCode?: string
    ) {}
}
