import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAirport } from 'app/shared/model/airport.model';
import { AirportService } from './airport.service';

@Component({
    selector: 'jhi-airport-update',
    templateUrl: './airport-update.component.html'
})
export class AirportUpdateComponent implements OnInit {
    airport: IAirport;
    isSaving: boolean;

    constructor(private airportService: AirportService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ airport }) => {
            this.airport = airport;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.airport.id !== undefined) {
            this.subscribeToSaveResponse(this.airportService.update(this.airport));
        } else {
            this.subscribeToSaveResponse(this.airportService.create(this.airport));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAirport>>) {
        result.subscribe((res: HttpResponse<IAirport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
