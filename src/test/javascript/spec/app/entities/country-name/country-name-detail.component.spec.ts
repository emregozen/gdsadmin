/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { CountryNameDetailComponent } from 'app/entities/country-name/country-name-detail.component';
import { CountryName } from 'app/shared/model/country-name.model';

describe('Component Tests', () => {
    describe('CountryName Management Detail Component', () => {
        let comp: CountryNameDetailComponent;
        let fixture: ComponentFixture<CountryNameDetailComponent>;
        const route = ({ data: of({ countryName: new CountryName(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CountryNameDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CountryNameDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CountryNameDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.countryName).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
