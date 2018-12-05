import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAirline } from 'app/shared/model/airline.model';
import { AirlineService } from './airline.service';

@Component({
    selector: 'jhi-airline-update',
    templateUrl: './airline-update.component.html'
})
export class AirlineUpdateComponent implements OnInit {
    airline: IAirline;
    isSaving: boolean;

    constructor(private airlineService: AirlineService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ airline }) => {
            this.airline = airline;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.airline.id !== undefined) {
            this.subscribeToSaveResponse(this.airlineService.update(this.airline));
        } else {
            this.subscribeToSaveResponse(this.airlineService.create(this.airline));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAirline>>) {
        result.subscribe((res: HttpResponse<IAirline>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
