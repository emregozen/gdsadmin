import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdsadminSharedModule } from 'app/shared';
import {
    CountryNameComponent,
    CountryNameDetailComponent,
    CountryNameUpdateComponent,
    CountryNameDeletePopupComponent,
    CountryNameDeleteDialogComponent,
    countryNameRoute,
    countryNamePopupRoute
} from './';

const ENTITY_STATES = [...countryNameRoute, ...countryNamePopupRoute];

@NgModule({
    imports: [GdsadminSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CountryNameComponent,
        CountryNameDetailComponent,
        CountryNameUpdateComponent,
        CountryNameDeleteDialogComponent,
        CountryNameDeletePopupComponent
    ],
    entryComponents: [CountryNameComponent, CountryNameUpdateComponent, CountryNameDeleteDialogComponent, CountryNameDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdsadminCountryNameModule {}
