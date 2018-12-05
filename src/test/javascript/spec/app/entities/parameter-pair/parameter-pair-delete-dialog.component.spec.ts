/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GdsadminTestModule } from '../../../test.module';
import { ParameterPairDeleteDialogComponent } from 'app/entities/parameter-pair/parameter-pair-delete-dialog.component';
import { ParameterPairService } from 'app/entities/parameter-pair/parameter-pair.service';

describe('Component Tests', () => {
    describe('ParameterPair Management Delete Component', () => {
        let comp: ParameterPairDeleteDialogComponent;
        let fixture: ComponentFixture<ParameterPairDeleteDialogComponent>;
        let service: ParameterPairService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [ParameterPairDeleteDialogComponent]
            })
                .overrideTemplate(ParameterPairDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParameterPairDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParameterPairService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
