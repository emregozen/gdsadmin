import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParameterPair } from 'app/shared/model/parameter-pair.model';
import { Principal } from 'app/core';
import { ParameterPairService } from './parameter-pair.service';

@Component({
    selector: 'jhi-parameter-pair',
    templateUrl: './parameter-pair.component.html'
})
export class ParameterPairComponent implements OnInit, OnDestroy {
    parameterPairs: IParameterPair[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private parameterPairService: ParameterPairService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.parameterPairService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IParameterPair[]>) => (this.parameterPairs = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.parameterPairService.query().subscribe(
            (res: HttpResponse<IParameterPair[]>) => {
                this.parameterPairs = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInParameterPairs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParameterPair) {
        return item.id;
    }

    registerChangeInParameterPairs() {
        this.eventSubscriber = this.eventManager.subscribe('parameterPairListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
