import React from "react";
import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";

function App() {
  const { token } = useAuth();
  if (!token) {
    return <LoginPage />;
  }

  return (
    <div className="App">
      <Header />
      <HomePage />
    </div>
  );
}

export default App;
