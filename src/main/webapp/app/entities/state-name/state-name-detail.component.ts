import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStateName } from 'app/shared/model/state-name.model';

@Component({
    selector: 'jhi-state-name-detail',
    templateUrl: './state-name-detail.component.html'
})
export class StateNameDetailComponent implements OnInit {
    stateName: IStateName;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stateName }) => {
            this.stateName = stateName;
        });
    }

    previousState() {
        window.history.back();
    }
}
