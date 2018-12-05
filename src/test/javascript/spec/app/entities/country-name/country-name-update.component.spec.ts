/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { CountryNameUpdateComponent } from 'app/entities/country-name/country-name-update.component';
import { CountryNameService } from 'app/entities/country-name/country-name.service';
import { CountryName } from 'app/shared/model/country-name.model';

describe('Component Tests', () => {
    describe('CountryName Management Update Component', () => {
        let comp: CountryNameUpdateComponent;
        let fixture: ComponentFixture<CountryNameUpdateComponent>;
        let service: CountryNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [CountryNameUpdateComponent]
            })
                .overrideTemplate(CountryNameUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CountryNameUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryNameService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CountryName(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.countryName = entity;
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
                    const entity = new CountryName();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.countryName = entity;
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
