/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { ParameterPairDetailComponent } from 'app/entities/parameter-pair/parameter-pair-detail.component';
import { ParameterPair } from 'app/shared/model/parameter-pair.model';

describe('Component Tests', () => {
    describe('ParameterPair Management Detail Component', () => {
        let comp: ParameterPairDetailComponent;
        let fixture: ComponentFixture<ParameterPairDetailComponent>;
        const route = ({ data: of({ parameterPair: new ParameterPair(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [ParameterPairDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParameterPairDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParameterPairDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parameterPair).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
