/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { AirportNameUpdateComponent } from 'app/entities/airport-name/airport-name-update.component';
import { AirportNameService } from 'app/entities/airport-name/airport-name.service';
import { AirportName } from 'app/shared/model/airport-name.model';

describe('Component Tests', () => {
    describe('AirportName Management Update Component', () => {
        let comp: AirportNameUpdateComponent;
        let fixture: ComponentFixture<AirportNameUpdateComponent>;
        let service: AirportNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [AirportNameUpdateComponent]
            })
                .overrideTemplate(AirportNameUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AirportNameUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirportNameService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AirportName(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.airportName = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AirportName();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.airportName = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
