import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/RecipePage";
import { Routes, Route } from "react-router-dom";
import NavBar from "./components/NavBar";
import ShoppingListPage from "./components/shoppingListPage/ShoppingListPage";
import AddRecipeForm from "./components/RecipeRegistration";
import PhotoAlbum from "./components/PhotoAlbum";

function App() {
  const { token } = useAuth();
  if (!token) {
    return <LoginPage />;
  }

  return (
    <main>
      <div className="App">
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/recipes" element={<RecipePage />} />
          <Route path="/shoppinglists" element={<ShoppingListPage />} />
          <Route path="/reciperegistration" element={<AddRecipeForm />} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />
        </Routes>
      </div>
    </main>
  );
}

export default App;
