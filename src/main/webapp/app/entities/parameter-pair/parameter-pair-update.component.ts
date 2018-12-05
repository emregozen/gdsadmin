import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IParameterPair } from 'app/shared/model/parameter-pair.model';
import { ParameterPairService } from './parameter-pair.service';

@Component({
    selector: 'jhi-parameter-pair-update',
    templateUrl: './parameter-pair-update.component.html'
})
export class ParameterPairUpdateComponent implements OnInit {
    parameterPair: IParameterPair;
    isSaving: boolean;

    constructor(private parameterPairService: ParameterPairService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parameterPair }) => {
            this.parameterPair = parameterPair;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parameterPair.id !== undefined) {
            this.subscribeToSaveResponse(this.parameterPairService.update(this.parameterPair));
        } else {
            this.subscribeToSaveResponse(this.parameterPairService.create(this.parameterPair));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParameterPair>>) {
        result.subscribe((res: HttpResponse<IParameterPair>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
