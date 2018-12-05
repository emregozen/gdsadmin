/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GdsadminTestModule } from '../../../test.module';
import { CityNameComponent } from 'app/entities/city-name/city-name.component';
import { CityNameService } from 'app/entities/city-name/city-name.service';
import { CityName } from 'app/shared/model/city-name.model';

describe('Component Tests', () => {
    describe('CityName Management Component', () => {
        let comp: CityNameComponent;
        let fixture: ComponentFixture<CityNameComponent>;
        let service: CityNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CityNameComponent],
                providers: []
            })
                .overrideTemplate(CityNameComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CityNameComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CityNameService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CityName(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cityNames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
