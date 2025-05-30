import { useState } from "react";
import { addIngredient } from "../../services/ingredientService";
import { useEffect} from "react";
import { fetchIngredients } from "../../services/ingredientService";

export default function AddIngredient({userId}) {
  const [ingredientName, setIngredientName] = useState("");
  const [quantity, setQuantity] = useState("");
  const [unitId, setUnitId] = useState("");
  const [ingredientCategoryId, setIngredientCategoryId] = useState("");
  const [ingredients, setIngredients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Vienetų pasirinkimai
  const unitOptions = [
    { id: 1, label: "Gramai (g)" },
    { id: 2, label: "Kilogramai (kg)" },
    { id: 3, label: "Litrai (l)" },
    { id: 4, label: "Mililitrai (ml)" },
    { id: 5, label: "Vienetai (vnt.)" },
  ];

  // Kategorijų pasirinkimai
  const categoryOptions = [
    { id: 1, label: "Pienas ir jo gaminiai" },
    { id: 2, label: "Mėsa, žuvis ir kiaušiniai" },
    { id: 3, label: "Bulvės, ankštiniai augalai ir riešutai" },
    { id: 4, label: "Daržovės" },
    { id: 5, label: "Vaisiai" },
    { id: 6, label: "Duona, makaronai, grūdai, cukrus ir saldainiai" },
    { id: 7, label: "Riebalai, aliejus ir sviestas" },
  ];

  useEffect(() => {
    fetchIngredients(); // iškart užkraunam
    const interval = setInterval(fetchIngredients, 10); // kas 1 sek.
    return () => clearInterval(interval); // išvalom intervalą
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!ingredientName.trim() || !quantity.trim() || !unitId.trim() || !ingredientCategoryId.trim()) {
      alert("Visi laukai privalomi!");
      return;
    }

    const newIngredient = {
      ingredientName: ingredientName.trim(),
      quantity: parseFloat(quantity),
      unitId: unitId.toString(), // Siunčiame kaip string
      ingredientCategoryId: ingredientCategoryId.toString(), // Siunčiame kaip string
    };

    try {
      await addIngredient(newIngredient);
      alert("✅ Ingredientas sėkmingai pridėtas!");

      await fetchIngredients(); // Iškart atnaujinam sąrašą po pridėjimo

      // Gali išvalyti formą jei nori
      setIngredientName("");
      setQuantity("");
      setUnitId("");
      setIngredientCategoryId("");
    } catch (error) {
      alert(`❌ Klaida pridedant ingredientą: ${error.message}`);
    }
  };

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
    <div className="bg-orange-200 h-screen">
      <div style={{ textAlign: "center", padding: "20px", backgroundColor: "#ffffff", border: "1px solid gray", boxShadow: "3px 3px 12px rgba(0, 0, 0, 0.3)", borderRadius: "8px", maxWidth: "400px", margin: "auto" }}>
        <h2 style={{ color: "#333" }}>Pridėti naują ingredientą</h2>
        <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px", padding: "20px" }}>
          <input type="text" placeholder="Ingrediento pavadinimas" value={ingredientName} onChange={(e) => setIngredientName(e.target.value)} required style={{ padding: "10px", borderRadius: "5px", border: "1px solid gray" }} />
          <input type="number" placeholder="Kiekis" value={quantity} onChange={(e) => setQuantity(e.target.value)} required style={{ padding: "10px", borderRadius: "5px", border: "1px solid gray" }} />

          {/* Dropdown vienetams */}
          <select value={unitId} onChange={(e) => setUnitId(e.target.value)} required style={{ padding: "10px", borderRadius: "5px", border: "1px solid gray" }}>
            <option value="">Pasirinkti vienetus</option>
            {unitOptions.map((unit) => (
              <option key={unit.id} value={unit.id}>{unit.label}</option>
            ))}
          </select>

          {/* Dropdown kategorijoms */}
          <select value={ingredientCategoryId} onChange={(e) => setIngredientCategoryId(e.target.value)} required style={{ padding: "10px", borderRadius: "5px", border: "1px solid gray" }}>
            <option value="">Pasirinkti kategoriją</option>
            {categoryOptions.map((category) => (
              <option key={category.id} value={category.id}>{category.label}</option>
            ))}
          </select>

          <button type="submit" style={{ padding: "10px", backgroundColor: "#4CAF50", color: "white", border: "none", cursor: "pointer", borderRadius: "5px", fontWeight: "bold", boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.2)" }}>
            Išsaugoti ingredientą
          </button>
        </form>
      </div>

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
    
    </div> 
  );
}
