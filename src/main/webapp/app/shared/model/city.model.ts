export interface ICity {
    id?: number;
    cityId?: number;
    cityCode?: string;
    countryCode?: string;
}

export class City implements ICity {
    constructor(public id?: number, public cityId?: number, public cityCode?: string, public countryCode?: string) {}
}
