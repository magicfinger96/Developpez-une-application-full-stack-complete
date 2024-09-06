import {
  AbstractControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';

/**
 * Validator that checks if the form fields values are different from the initial ones.
 *
 * @param initialValues values of the form at initialization.
 * @returns
 */
export function noChangesValidator(initialValues: any): ValidatorFn {
  return (formGroup: AbstractControl): ValidationErrors | null => {
    const controls = (formGroup as FormGroup).controls;
    let hasChanges = false;

    // Check if at least one value is different from the initialized one.
    for (const key in controls) {
      if (controls[key].value !== initialValues[key]) {
        hasChanges = true;
        break;
      }
    }

    return hasChanges ? null : { noChanges: true };
  };
}
