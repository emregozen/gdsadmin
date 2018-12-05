export interface IParameterPair {
    id?: number;
    parameterPairId?: number;
    parameterKey?: string;
    parameterValue?: string;
}

export class ParameterPair implements IParameterPair {
    constructor(public id?: number, public parameterPairId?: number, public parameterKey?: string, public parameterValue?: string) {}
}
