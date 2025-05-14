import { useAuth } from "../context/AuthContext";
import { Link } from "react-router-dom";

function Header() {
  const { logout, isAuthenticated } = useAuth();

  return (
    <header className="p-4 bg-gray-200 flex justify-evenly">
      <h1>My App</h1>
      {isAuthenticated ? (
        <div>
          <nav className="flex justify-evenly gap-[1rem]">
            <Link to="/">
              <button>Į pradinį puslapį</button>
            </Link>
            <Link to="/recipes">
              <button>Į receptų puslapį</button>
            </Link>
            <Link to="/shoppinglists">
              <button>Į pirkinių krepšelių puslapį</button>
            </Link>
            <Link to="/reciperegistration">
              <button>Į receptų registracijos formą</button>
            </Link>
            <Link to="/photoalbum">
              <button>Foto albumas</button>
            </Link>
          </nav>

          <button onClick={logout} className="text-red-500">
            Atsijungti
          </button>
        </div>
      ) : (
        <p className="text-green-500">Neprisijungęs</p>
      )}
    </header>
  );
}

export default Header;
