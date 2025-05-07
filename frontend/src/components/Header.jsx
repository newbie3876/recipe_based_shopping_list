import React from "react";
import { useAuth } from "../context/AuthContext";

function Header() {
  const { logout, isAuthenticated } = useAuth();

  return (
    <header className="p-4 bg-gray-200 flex justify-between">
      <h1>My App</h1>
      {isAuthenticated ? (
        <div>
          <button onClick={logout} className="text-red-500">
            Atsijungti
          </button>
        </div>
      ) : (
        <p className="text-green-500">Neprisijungęs</p>
      )}
    </header>
  );
}

export default Header;
