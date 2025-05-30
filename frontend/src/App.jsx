import LoginPage from "./components/LoginPage";
import Header from "./components/Header";
import HomePage from "./components/HomePage";
import { useAuth } from "./context/AuthContext";
import RecipePage from "./components/recipePage/RecipePage";
import { Routes, Route } from "react-router-dom";

import PhotoAlbum from "./components/PhotoAlbum";
import AddIngredient from "./components/ingredientPage/AddIngredient";
import CreateShoppingLists from "./components/shoppingListPage/CreateShoppingLists";
import ShoppingList from "./components/shoppingListPage/ShoppingList";
import ShoppingListReview from "./components/shoppingListPage/ShoppingListReview";
//import ShoppingList from "./components/shoppingListPage/ShoppingList";
import ShoppingListPage from "./components/shoppingListPage/ShoppingListPage";
//import PhotoAlbum from "./components/PhotoAlbum";
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

          <Route path="/shoppinglist" element={<ShoppingList userId={1} />} />
          <Route path="/shoppinglists" element={<ShoppingListReview userId={1} />} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />
          <Route path="/add-ingredient" element={<AddIngredient userId={1}/>} />
          <Route path="/create-shoppinglists" element={<CreateShoppingLists userId={1}/>} />

          {/* <Route path="/shoppinglists" element={<ShoppingList />} /> */}
          <Route path="/shoppinglists" element={<ShoppingListPage />} />
          <Route path="/photoalbum" element={<PhotoAlbum />} />

          {isAdmin && <Route path="/admin" element={<Admin />} />}

        </Routes>
        <ToastContainer position="top-center" autoClose={3000} />
      </div>
    </main>

  );
}

export default App;