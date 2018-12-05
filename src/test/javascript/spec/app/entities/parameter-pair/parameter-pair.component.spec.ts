/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GdsadminTestModule } from '../../../test.module';
import { ParameterPairComponent } from 'app/entities/parameter-pair/parameter-pair.component';
import { ParameterPairService } from 'app/entities/parameter-pair/parameter-pair.service';
import { ParameterPair } from 'app/shared/model/parameter-pair.model';

describe('Component Tests', () => {
    describe('ParameterPair Management Component', () => {
        let comp: ParameterPairComponent;
        let fixture: ComponentFixture<ParameterPairComponent>;
        let service: ParameterPairService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [ParameterPairComponent],
                providers: []
            })
                .overrideTemplate(ParameterPairComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParameterPairComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParameterPairService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ParameterPair(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.parameterPairs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
