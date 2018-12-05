import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParameterPair } from 'app/shared/model/parameter-pair.model';
import { ParameterPairService } from './parameter-pair.service';
import { ParameterPairComponent } from './parameter-pair.component';
import { ParameterPairDetailComponent } from './parameter-pair-detail.component';
import { ParameterPairUpdateComponent } from './parameter-pair-update.component';
import { ParameterPairDeletePopupComponent } from './parameter-pair-delete-dialog.component';
import { IParameterPair } from 'app/shared/model/parameter-pair.model';

@Injectable({ providedIn: 'root' })
export class ParameterPairResolve implements Resolve<IParameterPair> {
    constructor(private service: ParameterPairService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ParameterPair> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ParameterPair>) => response.ok),
                map((parameterPair: HttpResponse<ParameterPair>) => parameterPair.body)
            );
        }
        return of(new ParameterPair());
    }
}

export const parameterPairRoute: Routes = [
    {
        path: 'parameter-pair',
        component: ParameterPairComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParameterPairs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parameter-pair/:id/view',
        component: ParameterPairDetailComponent,
        resolve: {
            parameterPair: ParameterPairResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParameterPairs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parameter-pair/new',
        component: ParameterPairUpdateComponent,
        resolve: {
            parameterPair: ParameterPairResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParameterPairs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parameter-pair/:id/edit',
        component: ParameterPairUpdateComponent,
        resolve: {
            parameterPair: ParameterPairResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParameterPairs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parameterPairPopupRoute: Routes = [
    {
        path: 'parameter-pair/:id/delete',
        component: ParameterPairDeletePopupComponent,
        resolve: {
            parameterPair: ParameterPairResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParameterPairs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
