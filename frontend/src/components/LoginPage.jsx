import React from "react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useAuth } from "../context/AuthContext";
import dinner from "../assets/dinner.svg";
import { loginUser } from "../services/authService";

function LoginPage() {
  const { login } = useAuth();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ mode: "onBlur" });

  const [loginError, setLoginError] = useState("");
  const [loading, setLoading] = useState(false);

  const onSubmit = async ({ username, password }) => {
    setLoading(true);

    try {
      const token = await loginUser(username, password); // Naudojame service
      login(token);
    } catch (error) {
      setLoginError(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-orange-200">
      <div className="flex flex-col items-center">
        <h1 className="text-xl font-bold text-center mb-3">
          Sveiki atvykę į paprastesnį apsipirkimo pasaulį.
        </h1>
        <img
          src={dinner}
          alt="dinner plate"
          className="w-20 mb-3 animate-spin"
          style={{ animationDuration: "6s" }}
        />
      </div>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="bg-orange-100 p-6 rounded-xl shadow-md space-y-4 w-full max-w-sm"
        noValidate
      >
        <h2 className="text-xl font-bold text-center">Prisijungti</h2>

        {loginError && (
          <p className="text-red-500 text-sm text-center">{loginError}</p>
        )}

        <div>
          <label htmlFor="username">Vartotojo vardas</label>
          <input
            type="text"
            id="username"
            className="w-full border rounded p-2 mt-1"
            {...register("username", { required: "Būtina įvesti vardą" })}
          />
          <p className="text-red-500 text-sm">{errors.username?.message}</p>
        </div>

        <div>
          <label htmlFor="password">Slaptažodis</label>
          <input
            type="password"
            id="password"
            className="w-full border rounded p-2 mt-1"
            {...register("password", { required: "Būtina įvesti slaptažodį" })}
          />
          <p className="text-red-500 text-sm">{errors.password?.message}</p>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-500 hover:bg-blue-600 cursor-pointer text-white font-bold py-2 px-4 rounded"
        >
          {loading ? "Jungiamasi..." : "Prisijungti"}
        </button>
      </form>
      <div className="flex gap-2">
        <p>Dar neturi paskyros?</p>
        <a href="/register" className="text-blue-500">
          Registracija
        </a>
      </div>
    </div>
  );
}

export default LoginPage;
