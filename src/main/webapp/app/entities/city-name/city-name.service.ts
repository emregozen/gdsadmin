import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICityName } from 'app/shared/model/city-name.model';

type EntityResponseType = HttpResponse<ICityName>;
type EntityArrayResponseType = HttpResponse<ICityName[]>;

@Injectable({ providedIn: 'root' })
export class CityNameService {
    public resourceUrl = SERVER_API_URL + 'api/city-names';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/city-names';

    constructor(private http: HttpClient) {}

    create(cityName: ICityName): Observable<EntityResponseType> {
        return this.http.post<ICityName>(this.resourceUrl, cityName, { observe: 'response' });
    }

    update(cityName: ICityName): Observable<EntityResponseType> {
        return this.http.put<ICityName>(this.resourceUrl, cityName, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICityName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICityName[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICityName[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
