/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GdsadminTestModule } from '../../../test.module';
import { CityNameDeleteDialogComponent } from 'app/entities/city-name/city-name-delete-dialog.component';
import { CityNameService } from 'app/entities/city-name/city-name.service';

describe('Component Tests', () => {
    describe('CityName Management Delete Component', () => {
        let comp: CityNameDeleteDialogComponent;
        let fixture: ComponentFixture<CityNameDeleteDialogComponent>;
        let service: CityNameService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CityNameDeleteDialogComponent]
            })
                .overrideTemplate(CityNameDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CityNameDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CityNameService);
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
