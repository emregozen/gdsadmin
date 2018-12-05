/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GdsadminTestModule } from '../../../test.module';
import { StateNameComponent } from 'app/entities/state-name/state-name.component';
import { StateNameService } from 'app/entities/state-name/state-name.service';
import { StateName } from 'app/shared/model/state-name.model';

describe('Component Tests', () => {
    describe('StateName Management Component', () => {
        let comp: StateNameComponent;
        let fixture: ComponentFixture<StateNameComponent>;
        let service: StateNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [StateNameComponent],
                providers: []
            })
                .overrideTemplate(StateNameComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StateNameComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateNameService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StateName(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stateNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
