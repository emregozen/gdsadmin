/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { CityNameDetailComponent } from 'app/entities/city-name/city-name-detail.component';
import { CityName } from 'app/shared/model/city-name.model';

describe('Component Tests', () => {
    describe('CityName Management Detail Component', () => {
        let comp: CityNameDetailComponent;
        let fixture: ComponentFixture<CityNameDetailComponent>;
        const route = ({ data: of({ cityName: new CityName(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CityNameDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CityNameDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CityNameDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cityName).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
