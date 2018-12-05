import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParameterPair } from 'app/shared/model/parameter-pair.model';
import { ParameterPairService } from './parameter-pair.service';

@Component({
    selector: 'jhi-parameter-pair-delete-dialog',
    templateUrl: './parameter-pair-delete-dialog.component.html'
})
export class ParameterPairDeleteDialogComponent {
    parameterPair: IParameterPair;

    constructor(
        private parameterPairService: ParameterPairService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parameterPairService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'parameterPairListModification',
                content: 'Deleted an parameterPair'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parameter-pair-delete-popup',
    template: ''
})
export class ParameterPairDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parameterPair }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParameterPairDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.parameterPair = parameterPair;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
