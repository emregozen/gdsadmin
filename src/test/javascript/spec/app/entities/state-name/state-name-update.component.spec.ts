/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GdsadminTestModule } from '../../../test.module';
import { StateNameUpdateComponent } from 'app/entities/state-name/state-name-update.component';
import { StateNameService } from 'app/entities/state-name/state-name.service';
import { StateName } from 'app/shared/model/state-name.model';

describe('Component Tests', () => {
    describe('StateName Management Update Component', () => {
        let comp: StateNameUpdateComponent;
        let fixture: ComponentFixture<StateNameUpdateComponent>;
        let service: StateNameService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GdsadminTestModule],
                declarations: [StateNameUpdateComponent]
            })
                .overrideTemplate(StateNameUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StateNameUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateNameService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StateName(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stateName = entity;
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
                    const entity = new StateName();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stateName = entity;
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
