import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/recipePage/RecipePage";
import { Routes, Route } from "react-router-dom";
import ShoppingList from "./components/ShoppingList"
import PhotoAlbum from "./components/PhotoAlbum";
import AddIngredient from "./components/AddIngredient";

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
          <Route path="/shoppinglists" element={<ShoppingList userId={1}/>} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />
          <Route path="/add-ingredient" element={<AddIngredient />} />
        </Routes>
      </div>
    </main>

  );
}

export default App;