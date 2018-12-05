/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GdsadminTestModule } from '../../../test.module';
import { CountryNameDeleteDialogComponent } from 'app/entities/country-name/country-name-delete-dialog.component';
import { CountryNameService } from 'app/entities/country-name/country-name.service';

describe('Component Tests', () => {
    describe('CountryName Management Delete Component', () => {
        let comp: CountryNameDeleteDialogComponent;
        let fixture: ComponentFixture<CountryNameDeleteDialogComponent>;
        let service: CountryNameService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CountryNameDeleteDialogComponent]
            })
                .overrideTemplate(CountryNameDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CountryNameDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryNameService);
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
