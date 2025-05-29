import { useEffect, useState } from "react";
import { fetchIngredients } from "../services/ingredientService";

export default function AllIngredients({userId}) {
  const [ingredients, setIngredients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
      if (!userId) return;
  
      const fetchData = async () => {
        setLoading(true);
        try {

          const data = await fetchIngredients(userId);

          setIngredients(
            data.map(item => ({
              ingredientName: item.ingredientName,
              quantity: item.quantity,
              unitName: item.unitName,
              categoryName: item.categoryName
            }))
          );
  
        } catch (err) {
          setError("Nepavyko gauti ingredientų.");
        } finally {
          setLoading(false);
        }
      };
  
      fetchData();
    }, [userId]);

  return (
    <div>
      <h1 className="bg-orange-200 text-2xl font-bold text-center p-5">Visi ingredientai:</h1>
      <table className="bg-orange-200 w-full border border-orange-300 text-center mb-4">
        <thead>
          <tr>
            <th className="p-2 border border-orange-300">#</th>
            <th className="p-2 border border-orange-300">Pavadinimas</th>
            <th className="p-2 border border-orange-300">Kiekis</th>
            <th className="p-2 border border-orange-300">Vienetas</th>
            <th className="p-2 border border-orange-300">Kategorija</th>
          </tr>
        </thead>
        <tbody>
          {ingredients.map((ing, i) => (
            <tr key={ing.ingredientId || i}>
              <td className="p-2 border border-orange-300">{i + 1}</td>
              <td className="p-2 border border-orange-300">{ing.ingredientName || "–"}</td>
              <td className="p-2 border border-orange-300">{ing.quantity || "–"}</td>
              <td className="p-2 border border-orange-300">{ing.unitName || "–"}</td>
              <td className="p-2 border border-orange-300">{ing.categoryName || "–"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}