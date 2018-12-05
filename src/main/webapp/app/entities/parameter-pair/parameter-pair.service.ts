import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParameterPair } from 'app/shared/model/parameter-pair.model';

type EntityResponseType = HttpResponse<IParameterPair>;
type EntityArrayResponseType = HttpResponse<IParameterPair[]>;

@Injectable({ providedIn: 'root' })
export class ParameterPairService {
    public resourceUrl = SERVER_API_URL + 'api/parameter-pairs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/parameter-pairs';

    constructor(private http: HttpClient) {}

    create(parameterPair: IParameterPair): Observable<EntityResponseType> {
        return this.http.post<IParameterPair>(this.resourceUrl, parameterPair, { observe: 'response' });
    }

    update(parameterPair: IParameterPair): Observable<EntityResponseType> {
        return this.http.put<IParameterPair>(this.resourceUrl, parameterPair, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParameterPair>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParameterPair[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParameterPair[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
