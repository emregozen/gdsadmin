import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdsadminSharedModule } from 'app/shared';
import {
    CityNameComponent,
    CityNameDetailComponent,
    CityNameUpdateComponent,
    CityNameDeletePopupComponent,
    CityNameDeleteDialogComponent,
    cityNameRoute,
    cityNamePopupRoute
} from './';

const ENTITY_STATES = [...cityNameRoute, ...cityNamePopupRoute];

@NgModule({
    imports: [GdsadminSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CityNameComponent,
        CityNameDetailComponent,
        CityNameUpdateComponent,
        CityNameDeleteDialogComponent,
        CityNameDeletePopupComponent
    ],
    entryComponents: [CityNameComponent, CityNameUpdateComponent, CityNameDeleteDialogComponent, CityNameDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdsadminCityNameModule {}
