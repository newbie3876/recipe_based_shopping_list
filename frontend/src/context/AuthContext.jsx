import React, { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(() => localStorage.getItem("token"));
  const [user, setUser] = useState(null);

  // Fetch vartotojo duomenys pagal token
  const fetchUser = async (token) => {
    try {
      const response = await axios.get("/api/users", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUser(response.data); // Nustatyti gautus vartotojo duomenis
    } catch (error) {
      console.error("Nepavyko gauti vartotojo duomenų:", error);
      setUser(null); // Jei klaida, nustatyti null vartotoją
    }
  };

  // Auto-login jei yra token ir gauti vartotojo duomenis
  useEffect(() => {
    if (token) {
      fetchUser(token); // Užklausa į backend gauti vartotojo duomenis
    }
  }, [token]);

  const login = (newToken) => {
    localStorage.setItem("token", newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
    setUser(null);
  };

  const isAuthenticated = !!token;

  return (
    <AuthContext.Provider
      value={{ token, user, login, logout, isAuthenticated }}
    >
      {children}
    </AuthContext.Provider>
  );
};

// Custom hook
export const useAuth = () => useContext(AuthContext);
