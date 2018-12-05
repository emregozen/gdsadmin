/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { StateNameDetailComponent } from 'app/entities/state-name/state-name-detail.component';
import { StateName } from 'app/shared/model/state-name.model';

describe('Component Tests', () => {
    describe('StateName Management Detail Component', () => {
        let comp: StateNameDetailComponent;
        let fixture: ComponentFixture<StateNameDetailComponent>;
        const route = ({ data: of({ stateName: new StateName(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [StateNameDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StateNameDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StateNameDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stateName).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
