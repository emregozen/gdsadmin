import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAirportName } from 'app/shared/model/airport-name.model';

type EntityResponseType = HttpResponse<IAirportName>;
type EntityArrayResponseType = HttpResponse<IAirportName[]>;

@Injectable({ providedIn: 'root' })
export class AirportNameService {
    public resourceUrl = SERVER_API_URL + 'api/airport-names';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/airport-names';

    constructor(private http: HttpClient) {}

    create(airportName: IAirportName): Observable<EntityResponseType> {
        return this.http.post<IAirportName>(this.resourceUrl, airportName, { observe: 'response' });
    }

    update(airportName: IAirportName): Observable<EntityResponseType> {
        return this.http.put<IAirportName>(this.resourceUrl, airportName, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAirportName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAirportName[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAirportName[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
