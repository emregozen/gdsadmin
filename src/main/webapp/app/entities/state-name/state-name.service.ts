import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStateName } from 'app/shared/model/state-name.model';

type EntityResponseType = HttpResponse<IStateName>;
type EntityArrayResponseType = HttpResponse<IStateName[]>;

@Injectable({ providedIn: 'root' })
export class StateNameService {
    public resourceUrl = SERVER_API_URL + 'api/state-names';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/state-names';

    constructor(private http: HttpClient) {}

    create(stateName: IStateName): Observable<EntityResponseType> {
        return this.http.post<IStateName>(this.resourceUrl, stateName, { observe: 'response' });
    }

    update(stateName: IStateName): Observable<EntityResponseType> {
        return this.http.put<IStateName>(this.resourceUrl, stateName, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStateName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStateName[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStateName[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
