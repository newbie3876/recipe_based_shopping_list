import React from "react";
import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import PhotoAlbum from "./components/PhotoAlbum";

function App() {
  const { token } = useAuth();
  if (!token) {
    return <LoginPage />;
  }

  return (
    <div className="App">
      <Header />
      <PhotoAlbum />
    </div>
  );
}

export default App;
