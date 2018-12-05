import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GdsadminSharedModule } from 'app/shared';
import {
    StateNameComponent,
    StateNameDetailComponent,
    StateNameUpdateComponent,
    StateNameDeletePopupComponent,
    StateNameDeleteDialogComponent,
    stateNameRoute,
    stateNamePopupRoute
} from './';

const ENTITY_STATES = [...stateNameRoute, ...stateNamePopupRoute];

@NgModule({
    imports: [GdsadminSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StateNameComponent,
        StateNameDetailComponent,
        StateNameUpdateComponent,
        StateNameDeleteDialogComponent,
        StateNameDeletePopupComponent
    ],
    entryComponents: [StateNameComponent, StateNameUpdateComponent, StateNameDeleteDialogComponent, StateNameDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdsadminStateNameModule {}
