import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/recipePage/RecipePage";
import { Routes, Route } from "react-router-dom";
import ShoppingList from "./components/ShoppingList";
import PhotoAlbum from "./components/PhotoAlbum";
import Admin from "./components/Admin";
import { ToastContainer } from "react-toastify";

function App() {
  const { token, user } = useAuth();

  if (!token) {
    return (
      <>
        <LoginPage />
        <ToastContainer position="top-center" autoClose={3000} />
      </>
    );
  }

  if (!user) {
    return <div>Loading...</div>;
  }

  const isAdmin = user?.roles?.some((role) => role.name === "ROLE_ADMIN");

  return (
    <main>
      <div className="App">
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/recipes" element={<RecipePage />} />
          <Route path="/shoppinglists" element={<ShoppingList />} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />
          {isAdmin && <Route path="/admin" element={<Admin />} />}
        </Routes>
        <ToastContainer position="top-center" autoClose={3000} />
      </div>
    </main>
  );
}

export default App;
