import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICountryName } from 'app/shared/model/country-name.model';

type EntityResponseType = HttpResponse<ICountryName>;
type EntityArrayResponseType = HttpResponse<ICountryName[]>;

@Injectable({ providedIn: 'root' })
export class CountryNameService {
    public resourceUrl = SERVER_API_URL + 'api/country-names';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/country-names';

    constructor(private http: HttpClient) {}

    create(countryName: ICountryName): Observable<EntityResponseType> {
        return this.http.post<ICountryName>(this.resourceUrl, countryName, { observe: 'response' });
    }

    update(countryName: ICountryName): Observable<EntityResponseType> {
        return this.http.put<ICountryName>(this.resourceUrl, countryName, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICountryName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICountryName[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICountryName[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
