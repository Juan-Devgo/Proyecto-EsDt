import React, { useState } from "react";

import Header from "../../components/common/Registro/Header";
import Button from "../../components/ui/Registro/Button";


interface FormData {
  fullName: string;
  username: string;
  password: string;
}

interface FormErrors {
  fullName?: string;
  username?: string;
  password?: string;
}

const Registro: React.FC = () => {
  const [formData, setFormData] = useState<FormData>({
    fullName: "",
    username: "",
    password: "",
  });

  const [errors, setErrors] = useState<FormErrors>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validate = (): boolean => {
    const newErrors: FormErrors = {};

    if (!formData.fullName.trim()) {
      newErrors.fullName = "El nombre completo es requerido";
    }

    if (!formData.username.trim()) {
      newErrors.username = "El nombre de usuario es requerido";
    }

    if (!formData.password.trim()) {
      newErrors.password = "La contraseña es requerida";
    } else if (formData.password.length < 6) {
      newErrors.password = "La contraseña debe tener al menos 6 caracteres";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (validate()) {
      // Here you would typically send the data to your API
      console.log("Form submitted:", formData);
      // For demo purposes, show an alert
      alert("Registro exitoso!");
    }
  };

  const handleLogin = () => {
    // Navigate to login page
    console.log("Redirecting to login page");
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-white">
      <Header logoSrc="/images/img_616ab3338e874f96ab6b56159dd87ea5_1.png" />

      <div className="w-full max-w-md px-4">
        <h1 className="text-2xl font-semibold text-center text-black mb-2">
          Regístrate
        </h1>
        <p className="text-base text-center text-black mb-6">
          Crea tu cuenta para acceder
        </p>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="relative h-[40px]">
            <input
              type="text"
              name="fullName"
              placeholder="Nombre completo"
              value={formData.fullName}
              onChange={handleChange}
              className="w-full h-full px-4 py-2 text-gray-700 bg-white border border-[#dfdfdf] rounded-lg focus:outline-none focus:ring-1 focus:ring-[#335c67]"
            />
          </div>

          <div className="relative h-[40px]">
            <input
              type="text"
              name="username"
              placeholder="Nombre de usuario"
              value={formData.username}
              onChange={handleChange}
              className="w-full h-full px-4 py-2 text-gray-700 bg-white border border-[#dfdfdf] rounded-lg focus:outline-none focus:ring-1 focus:ring-[#335c67]"
            />
          </div>

          <div className="relative h-[40px]">
            <input
              type="password"
              name="password"
              placeholder="Contraseña"
              value={formData.password}
              onChange={handleChange}
              className="w-full h-full px-4 py-2 text-gray-700 bg-white border border-[#dfdfdf] rounded-lg focus:outline-none focus:ring-1 focus:ring-[#335c67]"
            />
          </div>

          <Button
            type="submit"
            variant="primary"
            fullWidth
            className="h-[40px] flex items-center justify-center"
          >
            Registrarse Ahora
          </Button>

          <p className="text-center text-base font-semibold text-[#828282] my-3">
            o inicia sesión si ya tienes una
          </p>

          <Button
            type="button"
            variant="secondary"
            fullWidth
            className="h-[40px] flex items-center justify-center"
            onClick={handleLogin}
          >
            Iniciar Sesión
          </Button>
        </form>
      </div>
    </div>
  );
};

export default Registro;