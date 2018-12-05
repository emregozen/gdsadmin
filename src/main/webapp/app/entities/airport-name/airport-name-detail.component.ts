import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirportName } from 'app/shared/model/airport-name.model';

@Component({
    selector: 'jhi-airport-name-detail',
    templateUrl: './airport-name-detail.component.html'
})
export class AirportNameDetailComponent implements OnInit {
    airportName: IAirportName;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ airportName }) => {
            this.airportName = airportName;
        });
    }

    previousState() {
        window.history.back();
    }
}
