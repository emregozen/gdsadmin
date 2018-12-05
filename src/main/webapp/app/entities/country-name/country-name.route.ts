import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CountryName } from 'app/shared/model/country-name.model';
import { CountryNameService } from './country-name.service';
import { CountryNameComponent } from './country-name.component';
import { CountryNameDetailComponent } from './country-name-detail.component';
import { CountryNameUpdateComponent } from './country-name-update.component';
import { CountryNameDeletePopupComponent } from './country-name-delete-dialog.component';
import { ICountryName } from 'app/shared/model/country-name.model';

@Injectable({ providedIn: 'root' })
export class CountryNameResolve implements Resolve<ICountryName> {
    constructor(private service: CountryNameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CountryName> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CountryName>) => response.ok),
                map((countryName: HttpResponse<CountryName>) => countryName.body)
            );
        }
        return of(new CountryName());
    }
}

export const countryNameRoute: Routes = [
    {
        path: 'country-name',
        component: CountryNameComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country-name/:id/view',
        component: CountryNameDetailComponent,
        resolve: {
            countryName: CountryNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country-name/new',
        component: CountryNameUpdateComponent,
        resolve: {
            countryName: CountryNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'country-name/:id/edit',
        component: CountryNameUpdateComponent,
        resolve: {
            countryName: CountryNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryNames'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countryNamePopupRoute: Routes = [
    {
        path: 'country-name/:id/delete',
        component: CountryNameDeletePopupComponent,
        resolve: {
            countryName: CountryNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountryNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
