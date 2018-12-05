import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICountryName } from 'app/shared/model/country-name.model';

@Component({
    selector: 'jhi-country-name-detail',
    templateUrl: './country-name-detail.component.html'
})
export class CountryNameDetailComponent implements OnInit {
    countryName: ICountryName;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ countryName }) => {
            this.countryName = countryName;
        });
    }

    previousState() {
        window.history.back();
    }
}
