import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParameterPair } from 'app/shared/model/parameter-pair.model';

@Component({
    selector: 'jhi-parameter-pair-detail',
    templateUrl: './parameter-pair-detail.component.html'
})
export class ParameterPairDetailComponent implements OnInit {
    parameterPair: IParameterPair;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parameterPair }) => {
            this.parameterPair = parameterPair;
        });
    }

    previousState() {
        window.history.back();
    }
}
