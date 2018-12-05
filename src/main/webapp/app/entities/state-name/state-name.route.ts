import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StateName } from 'app/shared/model/state-name.model';
import { StateNameService } from './state-name.service';
import { StateNameComponent } from './state-name.component';
import { StateNameDetailComponent } from './state-name-detail.component';
import { StateNameUpdateComponent } from './state-name-update.component';
import { StateNameDeletePopupComponent } from './state-name-delete-dialog.component';
import { IStateName } from 'app/shared/model/state-name.model';

@Injectable({ providedIn: 'root' })
export class StateNameResolve implements Resolve<IStateName> {
    constructor(private service: StateNameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StateName> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StateName>) => response.ok),
                map((stateName: HttpResponse<StateName>) => stateName.body)
            );
        }
        return of(new StateName());
    }
}

export const stateNameRoute: Routes = [
    {
        path: 'state-name',
        component: StateNameComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'state-name/:id/view',
        component: StateNameDetailComponent,
        resolve: {
            stateName: StateNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'state-name/new',
        component: StateNameUpdateComponent,
        resolve: {
            stateName: StateNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateNames'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'state-name/:id/edit',
        component: StateNameUpdateComponent,
        resolve: {
            stateName: StateNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateNames'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stateNamePopupRoute: Routes = [
    {
        path: 'state-name/:id/delete',
        component: StateNameDeletePopupComponent,
        resolve: {
            stateName: StateNameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
