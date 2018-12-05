import { NgModule } from '@angular/core';

import { GdsadminSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [GdsadminSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [GdsadminSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class GdsadminSharedCommonModule {}
