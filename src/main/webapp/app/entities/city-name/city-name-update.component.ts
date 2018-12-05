import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICityName } from 'app/shared/model/city-name.model';
import { CityNameService } from './city-name.service';

@Component({
    selector: 'jhi-city-name-update',
    templateUrl: './city-name-update.component.html'
})
export class CityNameUpdateComponent implements OnInit {
    cityName: ICityName;
    isSaving: boolean;

    constructor(private cityNameService: CityNameService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cityName }) => {
            this.cityName = cityName;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cityName.id !== undefined) {
            this.subscribeToSaveResponse(this.cityNameService.update(this.cityName));
        } else {
            this.subscribeToSaveResponse(this.cityNameService.create(this.cityName));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICityName>>) {
        result.subscribe((res: HttpResponse<ICityName>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
