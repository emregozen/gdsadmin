import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAirportName } from 'app/shared/model/airport-name.model';
import { AirportNameService } from './airport-name.service';

@Component({
    selector: 'jhi-airport-name-update',
    templateUrl: './airport-name-update.component.html'
})
export class AirportNameUpdateComponent implements OnInit {
    airportName: IAirportName;
    isSaving: boolean;

    constructor(private airportNameService: AirportNameService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ airportName }) => {
            this.airportName = airportName;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.airportName.id !== undefined) {
            this.subscribeToSaveResponse(this.airportNameService.update(this.airportName));
        } else {
            this.subscribeToSaveResponse(this.airportNameService.create(this.airportName));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAirportName>>) {
        result.subscribe((res: HttpResponse<IAirportName>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
