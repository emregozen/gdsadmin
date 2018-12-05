import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CityName } from 'app/shared/model/city-name.model';
import { CityNameService } from './city-name.service';
import { CityNameComponent } from './city-name.component';
import { CityNameDetailComponent } from './city-name-detail.component';
import { CityNameUpdateComponent } from './city-name-update.component';
import { CityNameDeletePopupComponent } from './city-name-delete-dialog.component';
import { ICityName } from 'app/shared/model/city-name.model';

@Injectable({ providedIn: 'root' })
export class CityNameResolve implements Resolve<ICityName> {
    constructor(private service: CityNameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CityName> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CityName>) => response.ok),
                map((cityName: HttpResponse<CityName>) => cityName.body)
            );
        }
        return of(new CityName());
    }
}

export const cityNameRoute: Routes = [
    {
        path: 'city-name',
        component: CityNameComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CityNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'city-name/:id/view',
        component: CityNameDetailComponent,
        resolve: {
            cityName: CityNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CityNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'city-name/new',
        component: CityNameUpdateComponent,
        resolve: {
            cityName: CityNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CityNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'city-name/:id/edit',
        component: CityNameUpdateComponent,
        resolve: {
            cityName: CityNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CityNames'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cityNamePopupRoute: Routes = [
    {
        path: 'city-name/:id/delete',
        component: CityNameDeletePopupComponent,
        resolve: {
            cityName: CityNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CityNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
