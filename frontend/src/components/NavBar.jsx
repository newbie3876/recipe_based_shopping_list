import { Link } from "react-router-dom";

function NavBar() {
  return (
    <nav className="flex justify-center gap-[1rem] bg-orange-200 text-white">
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
    </nav>
  );
}

export default NavBar;