import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GdsadminAirlineModule } from './airline/airline.module';
import { GdsadminAirportModule } from './airport/airport.module';
import { GdsadminAirportNameModule } from './airport-name/airport-name.module';
import { GdsadminCityModule } from './city/city.module';
import { GdsadminCityNameModule } from './city-name/city-name.module';
import { GdsadminCountryModule } from './country/country.module';
import { GdsadminCountryNameModule } from './country-name/country-name.module';
import { GdsadminStateNameModule } from './state-name/state-name.module';
import { GdsadminParameterPairModule } from './parameter-pair/parameter-pair.module';
import { GdsadminMessageModule } from './message/message.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GdsadminAirlineModule,
        GdsadminAirportModule,
        GdsadminAirportNameModule,
        GdsadminCityModule,
        GdsadminCityNameModule,
        GdsadminCountryModule,
        GdsadminCountryNameModule,
        GdsadminStateNameModule,
        GdsadminParameterPairModule,
        GdsadminMessageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GdsadminEntityModule {}
