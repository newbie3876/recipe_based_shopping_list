import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/recipePage/RecipePage";
import { Routes, Route } from "react-router-dom";
import ShoppingList from "./components/ShoppingList"
import PhotoAlbum from "./components/PhotoAlbum";
import AddIngredient from "./components/AddIngredient";
//import AddShoppingListModal from "./components/shoppingListPage/AddShoppingListModal";
import ShoppingListForm from "./components/ShoppingListForm";


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
          <Route path="/photoalbum" element={<PhotoAlbum />} />
          <Route path="/add-ingredient" element={<AddIngredient userId={1}/>} />
          <Route path="/create-shoppinglists" element={<ShoppingListForm userId={1}/>} />
        </Routes>
      </div>
    </main>

  );
}

export default App;

//<Route path="/create-shoppinglists" element={<AddShoppingListModal userId={1}/>} />