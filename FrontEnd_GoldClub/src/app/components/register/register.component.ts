import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      codigoEmpleado: ['', Validators.required],  // Ajuste de la clave para coincidir con el backend
      password: ['', [Validators.required, Validators.minLength(7)]]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formData = this.registerForm.value;

      this.http.post('https://goldclub-production.up.railway.app/api/usuarios/registro', formData)
        .subscribe({
          next: () => {
            alert('Registro exitoso');
            this.router.navigate(['/login']);
          },
          error: (error) => {
            if (error.status === 400) {
              alert('El correo electrónico ya está en uso o los datos no son válidos');
            } else {
              alert('Error al registrar el usuario. Por favor, inténtalo de nuevo más tarde.');
            }
          }
        });
    } else {
      alert('Por favor, complete todos los campos correctamente.');
    }
  }
}
