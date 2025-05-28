import { useEffect, useState } from "react";
import { fetchShoppingLists } from "../services/shoppingListService";

export default function AllIngredients({userId}) {
  const [independentIngredients, setIngredients] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
      if (!userId) return;
  
      const fetchData = async () => {
        setLoading(true);
        try {
          //fetchIngredients turėtų tiesiog grąžinti ingredientus, o ne pirkinių sąrašus
          const data = await fetchShoppingLists(userId);
          //Jei fetchIngredients grąžina tiesiog ingredientų masyvą, nereikia daryti flatMap
  
          const allIngredients = data.flatMap(list =>
              list.items?.map(item => ({
              ingredientId: item.ingredientId ?? item.id,
              ingredientName: item.ingredientName,
              quantity: item.quantity,
              //unitId: item.unitId,
              unit: item.unit,
              ingredientCategory: item.ingredientCategory ?? []
          })) || []
        );
  
        setIngredients(allIngredients);
  
        } catch (err) {
          setError("Nepavyko gauti ingredientų.");
        } finally {
          setLoading(false);
        }
      };
  
      fetchData();
    }, [userId]);

    

    const fetchIngredients = async () => {
    try {
      const response = await fetch("/api/ingredients", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
 
      if (response.ok) {
        const data = await response.json();
        setIngredients(data);
      } else {
        console.error("Nepavyko gauti paveikslėlių");
      }
    } catch (error) {
      console.error("Klaida gaunant paveikslėlius:", error);
    }
    };

    useEffect(() => {
    fetchIngredients();
    }, []);

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
          {independentIngredients.map((ing, i) => (
            <tr key={ing.ingredientId || i}>
              <td className="p-2 border border-orange-300">{i + 1}</td>
              <td className="p-2 border border-orange-300">{ing.ingredientName || "–"}</td>
              <td className="p-2 border border-orange-300">{ing.quantity ?? 1}</td>
              <td className="p-2 border border-orange-300">{ing.unit || "–"}</td>
              <td className="p-2 border border-orange-300">
                {Array.isArray(ing.ingredientCategory) && ing.ingredientCategory.length > 0
                  ? ing.ingredientCategory.map(cat => cat.categoryName).join(", ")
                  : "–"}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
