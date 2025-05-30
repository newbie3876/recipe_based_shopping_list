import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/recipePage/RecipePage";
import { Routes, Route } from "react-router-dom";
import PhotoAlbum from "./components/PhotoAlbum";
import AddIngredient from "./components/ingredientPage/AddIngredient";
import AllIngredients from "./components/ingredientPage/AllIngredients";
import CreateShoppingLists from "./components/shoppingListPage/CreateShoppingLists";
import ShoppingList from "./components/shoppingListPage/ShoppingList";
import ShoppingListReview from "./components/shoppingListPage/ShoppingListReview";


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
          <Route path="/shoppinglist" element={<ShoppingList userId={1} />} />
          <Route path="/shoppinglists" element={<ShoppingListReview userId={1} />} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />
          <Route path="/add-ingredient" element={<AddIngredient userId={1}/>} />
          <Route path="/all-ingredients" element={<AllIngredients userId={1}/>} />
          <Route path="/create-shoppinglists" element={<CreateShoppingLists userId={1}/>} />
        </Routes>
      </div>
    </main>

  );
}

export default App;