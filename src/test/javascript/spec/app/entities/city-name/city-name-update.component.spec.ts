/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { CityNameUpdateComponent } from 'app/entities/city-name/city-name-update.component';
import { CityNameService } from 'app/entities/city-name/city-name.service';
import { CityName } from 'app/shared/model/city-name.model';

describe('Component Tests', () => {
    describe('CityName Management Update Component', () => {
        let comp: CityNameUpdateComponent;
        let fixture: ComponentFixture<CityNameUpdateComponent>;
        let service: CityNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CityNameUpdateComponent]
            })
                .overrideTemplate(CityNameUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CityNameUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CityNameService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CityName(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cityName = entity;
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
                    const entity = new CityName();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cityName = entity;
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
