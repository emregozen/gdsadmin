import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdsadminSharedModule } from 'app/shared';
import {
    AirportNameComponent,
    AirportNameDetailComponent,
    AirportNameUpdateComponent,
    AirportNameDeletePopupComponent,
    AirportNameDeleteDialogComponent,
    airportNameRoute,
    airportNamePopupRoute
} from './';

const ENTITY_STATES = [...airportNameRoute, ...airportNamePopupRoute];

@NgModule({
    imports: [GdsadminSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AirportNameComponent,
        AirportNameDetailComponent,
        AirportNameUpdateComponent,
        AirportNameDeleteDialogComponent,
        AirportNameDeletePopupComponent
    ],
    entryComponents: [AirportNameComponent, AirportNameUpdateComponent, AirportNameDeleteDialogComponent, AirportNameDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdsadminAirportNameModule {}
