/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { ParameterPairUpdateComponent } from 'app/entities/parameter-pair/parameter-pair-update.component';
import { ParameterPairService } from 'app/entities/parameter-pair/parameter-pair.service';
import { ParameterPair } from 'app/shared/model/parameter-pair.model';

describe('Component Tests', () => {
    describe('ParameterPair Management Update Component', () => {
        let comp: ParameterPairUpdateComponent;
        let fixture: ComponentFixture<ParameterPairUpdateComponent>;
        let service: ParameterPairService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [ParameterPairUpdateComponent]
            })
                .overrideTemplate(ParameterPairUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParameterPairUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParameterPairService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ParameterPair(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.parameterPair = entity;
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
                    const entity = new ParameterPair();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.parameterPair = entity;
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
