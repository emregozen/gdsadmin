import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAirportName } from 'app/shared/model/airport-name.model';
import { Principal } from 'app/core';
import { AirportNameService } from './airport-name.service';

@Component({
    selector: 'jhi-airport-name',
    templateUrl: './airport-name.component.html'
})
export class AirportNameComponent implements OnInit, OnDestroy {
    airportNames: IAirportName[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private airportNameService: AirportNameService,
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
            this.airportNameService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IAirportName[]>) => (this.airportNames = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.airportNameService.query().subscribe(
            (res: HttpResponse<IAirportName[]>) => {
                this.airportNames = res.body;
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
        this.registerChangeInAirportNames();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAirportName) {
        return item.id;
    }

    registerChangeInAirportNames() {
        this.eventSubscriber = this.eventManager.subscribe('airportNameListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
