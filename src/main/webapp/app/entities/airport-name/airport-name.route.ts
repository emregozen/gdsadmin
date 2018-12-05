import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AirportName } from 'app/shared/model/airport-name.model';
import { AirportNameService } from './airport-name.service';
import { AirportNameComponent } from './airport-name.component';
import { AirportNameDetailComponent } from './airport-name-detail.component';
import { AirportNameUpdateComponent } from './airport-name-update.component';
import { AirportNameDeletePopupComponent } from './airport-name-delete-dialog.component';
import { IAirportName } from 'app/shared/model/airport-name.model';

@Injectable({ providedIn: 'root' })
export class AirportNameResolve implements Resolve<IAirportName> {
    constructor(private service: AirportNameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AirportName> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AirportName>) => response.ok),
                map((airportName: HttpResponse<AirportName>) => airportName.body)
            );
        }
        return of(new AirportName());
    }
}

export const airportNameRoute: Routes = [
    {
        path: 'airport-name',
        component: AirportNameComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirportNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'airport-name/:id/view',
        component: AirportNameDetailComponent,
        resolve: {
            airportName: AirportNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirportNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'airport-name/new',
        component: AirportNameUpdateComponent,
        resolve: {
            airportName: AirportNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirportNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'airport-name/:id/edit',
        component: AirportNameUpdateComponent,
        resolve: {
            airportName: AirportNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirportNames'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airportNamePopupRoute: Routes = [
    {
        path: 'airport-name/:id/delete',
        component: AirportNameDeletePopupComponent,
        resolve: {
            airportName: AirportNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AirportNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
