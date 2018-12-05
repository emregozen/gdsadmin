export interface IStateName {
    id?: number;
    stateNameId?: number;
    countryCode?: string;
    stateCode?: string;
    stateName?: string;
    language?: string;
}

export class StateName implements IStateName {
    constructor(
        public id?: number,
        public stateNameId?: number,
        public countryCode?: string,
        public stateCode?: string,
        public stateName?: string,
        public language?: string
    ) {}
}
