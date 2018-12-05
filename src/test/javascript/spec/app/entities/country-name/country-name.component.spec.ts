/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GdsadminTestModule } from '../../../test.module';
import { CountryNameComponent } from 'app/entities/country-name/country-name.component';
import { CountryNameService } from 'app/entities/country-name/country-name.service';
import { CountryName } from 'app/shared/model/country-name.model';

describe('Component Tests', () => {
    describe('CountryName Management Component', () => {
        let comp: CountryNameComponent;
        let fixture: ComponentFixture<CountryNameComponent>;
        let service: CountryNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CountryNameComponent],
                providers: []
            })
                .overrideTemplate(CountryNameComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CountryNameComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryNameService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CountryName(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.countryNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
