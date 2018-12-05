/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { AirportNameDetailComponent } from 'app/entities/airport-name/airport-name-detail.component';
import { AirportName } from 'app/shared/model/airport-name.model';

describe('Component Tests', () => {
    describe('AirportName Management Detail Component', () => {
        let comp: AirportNameDetailComponent;
        let fixture: ComponentFixture<AirportNameDetailComponent>;
        const route = ({ data: of({ airportName: new AirportName(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [AirportNameDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AirportNameDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AirportNameDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.airportName).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
