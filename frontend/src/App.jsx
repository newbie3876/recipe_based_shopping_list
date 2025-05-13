import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/RecipePage";
import { Routes, Route } from "react-router-dom";
import NavBar from "./components/NavBar";
import ShoppingList from "./components/ShoppingList"
import AddRecipeForm from "./components/RecipeRegistration";

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
          <Route path="/" element={<HomePage/>} />
          <Route path="/recipes" element={<RecipePage/>} />
          <Route path="/shoppinglists" element={<ShoppingList/>} />
          <Route path="/reciperegistration" element={<AddRecipeForm/>} />
        </Routes>
      </div>
    </main>
  );
}

export default App;
