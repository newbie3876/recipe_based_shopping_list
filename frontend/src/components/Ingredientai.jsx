// Ingredientai.jsx
import { useLocation } from "react-router-dom";

export default function Ingredientai() {
  const location = useLocation();
  const selectedIngredients = location.state?.selectedIngredients || [];

  return (
    <div>
      <h2>Pasirinkti ingredientai:</h2>
      {selectedIngredients.length === 0 && <p>Nieko nepasirinkta.</p>}
      <ul>
        {selectedIngredients.map(ing => (
          <li key={ing.id}>{ing.name}</li>
        ))}
      </ul>
    </div>
  );
}