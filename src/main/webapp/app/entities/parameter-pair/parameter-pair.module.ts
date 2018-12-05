import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdsadminSharedModule } from 'app/shared';
import {
    ParameterPairComponent,
    ParameterPairDetailComponent,
    ParameterPairUpdateComponent,
    ParameterPairDeletePopupComponent,
    ParameterPairDeleteDialogComponent,
    parameterPairRoute,
    parameterPairPopupRoute
} from './';

const ENTITY_STATES = [...parameterPairRoute, ...parameterPairPopupRoute];

@NgModule({
    imports: [GdsadminSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParameterPairComponent,
        ParameterPairDetailComponent,
        ParameterPairUpdateComponent,
        ParameterPairDeleteDialogComponent,
        ParameterPairDeletePopupComponent
    ],
    entryComponents: [
        ParameterPairComponent,
        ParameterPairUpdateComponent,
        ParameterPairDeleteDialogComponent,
        ParameterPairDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdsadminParameterPairModule {}
