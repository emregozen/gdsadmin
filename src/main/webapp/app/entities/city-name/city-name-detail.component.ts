import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICityName } from 'app/shared/model/city-name.model';

@Component({
    selector: 'jhi-city-name-detail',
    templateUrl: './city-name-detail.component.html'
})
export class CityNameDetailComponent implements OnInit {
    cityName: ICityName;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cityName }) => {
            this.cityName = cityName;
        });
    }

    previousState() {
        window.history.back();
    }
}
