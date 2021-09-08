import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleLoggerComponent } from './console-logger.component';

describe('ConsoleLoggerComponent', () => {
  let component: ConsoleLoggerComponent;
  let fixture: ComponentFixture<ConsoleLoggerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsoleLoggerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsoleLoggerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
