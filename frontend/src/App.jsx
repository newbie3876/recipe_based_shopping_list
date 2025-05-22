import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/recipePage/RecipePage";
import { Routes, Route } from "react-router-dom";

import ShoppingList from "./components/ShoppingList";
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
          <Route path="/shoppinglists" element={<ShoppingList />} />
          <Route path="/reciperegistration" element={<AddRecipeForm />} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />
        </Routes>
      </div>
    </main>
  );
}

export default App;
