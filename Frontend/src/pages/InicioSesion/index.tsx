import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/common/InicioSesion/Header';
import Button from '../../components/ui/InicioSesion/Button';
import InputField from '../../components/ui/InicioSesion/InputField';
import { LoginFormData } from '../../types/InicioSesion';

const InicioSesion: React.FC = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<LoginFormData>({
    username: '',
    password: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    // In a real application, you would handle authentication here
    console.log('Login attempt with:', formData);
    // For demo purposes, we'll just log the attempt
    alert('Inicio de sesión: ' + JSON.stringify(formData));
  };

  const handleRegister = () => {
    // In a real application, this would navigate to the registration page
    console.log('Navigate to registration');
    alert('Redirigiendo a la página de registro');
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-white">
      <Header logoSrc="/images/img_616ab3338e874f96ab6b56159dd87ea5_2.png" />

      <div className="w-full max-w-md px-4">
        <h1 className="text-2xl font-semibold text-center text-black mb-6">
          Inicia Sesión
        </h1>

        <form onSubmit={handleLogin} className="space-y-4">
          <div className="relative">
            <InputField
              type="text"
              placeholder="Nombre de usuario"
              value={formData.username}
              onChange={handleChange}
              name="username"
              required
            />
          </div>

          <div className="relative">
            <InputField
              type="password"
              placeholder="Contraseña"
              value={formData.password}
              onChange={handleChange}
              name="password"
              required
            />
          </div>

          <Button
            type="submit"
            variant="primary"
            fullWidth
          >
            Inicia Sesión
          </Button>
        </form>

        <div className="text-center my-4">
          <p className="text-base font-semibold text-[#828282]">
            o regístrate si aún no tienes una cuenta
          </p>
        </div>

        <Button
          variant="secondary"
          onClick={handleRegister}
          fullWidth
        >
          Regístrate
        </Button>
      </div>
    </div>
  );
};

export default InicioSesion;