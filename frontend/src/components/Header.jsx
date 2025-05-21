import { useAuth } from "../context/AuthContext";
import { NavLink } from "react-router-dom";
import dinner from "../assets/dinner.svg";

function Header() {
  const { logout, isAuthenticated, user } = useAuth();

  // Čia deklaruojam isAdmin
  const isAdmin =
    user?.roles?.some((role) => role?.name === "ROLE_ADMIN") || false;

  const getNavLinkClass = (isActive) =>
    isActive ? "underline font-semibold" : "hover:underline";

  return (
    <header className="p-4 bg-orange-300 flex items-center">
      <div className="flex-shrink-0">
        <img className="w-15" src={dinner} alt="dinner plate logo" />
      </div>

      {isAuthenticated && (
        <nav className="flex-1 flex justify-center gap-6">
          <NavLink
            to="/"
            className={({ isActive }) => getNavLinkClass(isActive)}
          >
            Pradinis
          </NavLink>

          <NavLink
            to="/recipes"
            className={({ isActive }) => getNavLinkClass(isActive)}
          >
            Mano receptai
          </NavLink>

          <NavLink
            to="/shoppinglists"
            className={({ isActive }) => getNavLinkClass(isActive)}
          >
            Pirkinių krepšeliai
          </NavLink>

          <NavLink
            to="/photoalbum"
            className={({ isActive }) => getNavLinkClass(isActive)}
          >
            Foto albumas
          </NavLink>

          {user && isAdmin && (
            <NavLink
              to="/admin"
              className={({ isActive }) => getNavLinkClass(isActive)}
            >
              Admin
            </NavLink>
          )}
        </nav>
      )}

      <div className="flex-shrink-0">
        <button
          onClick={logout}
          className="text-red-500 cursor-pointer hover:text-red-300"
        >
          Atsijungti
        </button>
      </div>
    </header>
  );
}

export default Header;
