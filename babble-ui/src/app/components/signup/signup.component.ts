import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService) {
    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator]]
    });

    this.signupForm.valueChanges.subscribe(() => console.log(this.signupForm.get('password')?.errors));
  }

  passwordValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const password = control.value;
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#@$!%*?&-])[A-Za-z\d#@$!%*?&-]{6,}$/;
    if (!regex.test(password)) {
      return { 'invalidPassword': true };
    }
    return null;
  }

  isInvalidPassword() {
    const password = this.signupForm.get('password');
    return password?.errors && password?.errors?.invalidPassword;
  }

  get noOfCharsStyle() {
    return this.signupForm.get('password')?.value?.length >= 6 ? 'color: green' : 'color:red';
  }

  get capitalLetterStyle() {
    return /[A-Z]/.test(this.signupForm.get('password')?.value) ? 'color: green' : 'color:red';
  }

  get numberStyle() {
    return /\d/.test(this.signupForm.get('password')?.value) ? 'color: green' : 'color:red';
  }

  get specialCharacterStyle() {
    return /[#@$!%*?&-]/.test(this.signupForm.get('password')?.value) ? 'color: green' : 'color:red';
  }

  onSubmit(): void {
    const newUser: User = this.signupForm.value;

    this.userService.signUp(newUser)
      .subscribe(
        response => {
          console.log('Sign up successful:', response);
        },
        error => {
          console.error('Error signing up:', error);
        }
      );
  }
}
