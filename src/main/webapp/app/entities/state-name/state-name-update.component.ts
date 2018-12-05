import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IStateName } from 'app/shared/model/state-name.model';
import { StateNameService } from './state-name.service';

@Component({
    selector: 'jhi-state-name-update',
    templateUrl: './state-name-update.component.html'
})
export class StateNameUpdateComponent implements OnInit {
    stateName: IStateName;
    isSaving: boolean;

    constructor(private stateNameService: StateNameService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stateName }) => {
            this.stateName = stateName;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stateName.id !== undefined) {
            this.subscribeToSaveResponse(this.stateNameService.update(this.stateName));
        } else {
            this.subscribeToSaveResponse(this.stateNameService.create(this.stateName));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStateName>>) {
        result.subscribe((res: HttpResponse<IStateName>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
