/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GdsadminTestModule } from '../../../test.module';
import { AirportNameComponent } from 'app/entities/airport-name/airport-name.component';
import { AirportNameService } from 'app/entities/airport-name/airport-name.service';
import { AirportName } from 'app/shared/model/airport-name.model';

describe('Component Tests', () => {
    describe('AirportName Management Component', () => {
        let comp: AirportNameComponent;
        let fixture: ComponentFixture<AirportNameComponent>;
        let service: AirportNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [AirportNameComponent],
                providers: []
            })
                .overrideTemplate(AirportNameComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirportNameComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirportNameService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AirportName(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.airportNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
