import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const formData = {
        email: this.loginForm.value.email,
        password: this.loginForm.value.password,
        codigoEmpleado: null // Enviar código de empleado como null
      };

      this.http.post('https://goldclub-production.up.railway.app/api/usuarios/login', formData, { responseType: 'text' })
        .subscribe((token: string) => {
          localStorage.setItem('token', token);
          this.router.navigate(['/products']);
        }, error => {
          if (error.status === 401) {
            alert('Credenciales incorrectas');
          } else {
            alert('Error al iniciar sesión. Por favor, inténtalo de nuevo más tarde.');
          }
        });
    } else {
      alert('Por favor, complete todos los campos correctamente.');
    }
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }
}
