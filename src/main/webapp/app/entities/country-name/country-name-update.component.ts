import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICountryName } from 'app/shared/model/country-name.model';
import { CountryNameService } from './country-name.service';

@Component({
    selector: 'jhi-country-name-update',
    templateUrl: './country-name-update.component.html'
})
export class CountryNameUpdateComponent implements OnInit {
    countryName: ICountryName;
    isSaving: boolean;

    constructor(private countryNameService: CountryNameService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ countryName }) => {
            this.countryName = countryName;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.countryName.id !== undefined) {
            this.subscribeToSaveResponse(this.countryNameService.update(this.countryName));
        } else {
            this.subscribeToSaveResponse(this.countryNameService.create(this.countryName));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICountryName>>) {
        result.subscribe((res: HttpResponse<ICountryName>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
