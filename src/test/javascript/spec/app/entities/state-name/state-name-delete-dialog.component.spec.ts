/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GdsadminTestModule } from '../../../test.module';
import { StateNameDeleteDialogComponent } from 'app/entities/state-name/state-name-delete-dialog.component';
import { StateNameService } from 'app/entities/state-name/state-name.service';

describe('Component Tests', () => {
    describe('StateName Management Delete Component', () => {
        let comp: StateNameDeleteDialogComponent;
        let fixture: ComponentFixture<StateNameDeleteDialogComponent>;
        let service: StateNameService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [StateNameDeleteDialogComponent]
            })
                .overrideTemplate(StateNameDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StateNameDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateNameService);
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
