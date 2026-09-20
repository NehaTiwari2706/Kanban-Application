import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyWorkComponent } from './my-work.component';

describe('MyWorkComponent', () => {
  let component: MyWorkComponent;
  let fixture: ComponentFixture<MyWorkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyWorkComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open the create task modal', () => {
    component.isCreateTaskModalOpen = false;

    component.openCreateTaskModal();

    expect(component.isCreateTaskModalOpen).toBeTrue();
  });
});
