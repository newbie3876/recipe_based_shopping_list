import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { fetchShoppingLists, createShoppingList  } from "../../services/shoppingListService";

export default function AddShoppingListModal({ userId }) {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [createdAt, setCreatedAt] = useState("");
  const [recipeIngredients, setRecipeIngredients] = useState("");
  const [independentIngredients, setIndependentIngredients] = useState([]); // Pasirinkti ingredientai
  const [ingredients, setIngredients] = useState([]); // Galimi ingredientai
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [selectedIds, setSelectedIds] = useState([]);

  const handleCancel = () => {
    navigate("/"); // Nukreipiame vartotoją į pagrindinį puslapį
  };

  useEffect(() => {
    if (!userId) return;

    const fetchData = async () => {
      setLoading(true);
      try {
        // fetchIngredients turėtų tiesiog grąžinti ingredientus, o ne pirkinių sąrašus
        const data = await fetchShoppingLists(userId);
        // Jei fetchIngredients grąžina tiesiog ingredientų masyvą, nereikia daryti flatMap

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

  // Funkcija kelių ingredientų pasirinkimui
  const handleAddIngredient = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions).map((option) => Number(option.value));

    const selectedIngredients = ingredients.filter((ingredient) => selectedOptions.includes(ingredient.ingredientId));

    setIndependentIngredients(selectedIngredients);
    setSelectedIds(selectedOptions);
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);
  setError(null);

    const selectedData = ingredients.filter(i => selectedIds.includes(i.id));
    navigate("/create-shoppinglists", { state: { selectedIngredients: selectedData } });

  try {
    const validItems = independentIngredients.filter(i =>
      ingredients.some(ing => ing.ingredientId === i.ingredientId)
    );

    if (validItems.length !== independentIngredients.length) {
      setError("Kai kurie ingredientai turi netinkamą ID – prašome pasirinkti iš naujo.");
      setLoading(false);
      return;
    }

    const items = validItems.map((i) => ({
        ingredientId: Number(i.ingredientId),
        quantity: Number(i.quantity) || 1,
        unitId: i.unitId,
        //ingredientCategory: i.ingredientCategory?.map((cat) => ({
        //categoryName: cat.categoryName,
    }));

    console.log("Siunčiami ingredientai į createShoppingList:", items);

    await createShoppingList({
      userId,
      createdAt,
      items,
    });

    //navigate("/create-shoppinglists");
    // Siunčiame į kitą puslapį su visais ingredientais
    //navigate("/shoppinglist-review", { state: { selectedIngredients: validItems } });

    setLoading(false);
  } catch (err) {
    console.error("❌ Klaida kuriant prekių krepšelį:", err);
    setError("Nepavyko sukurti prekių krepšelio.");
  } finally {
    setLoading(false);
  }
};

  const clearForm = () => {
    setName("");
    setCreatedAt("");
    setRecipeIngredients("");
    setIndependentIngredients([]);
  };

  

  return (
    <section className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white p-6 rounded shadow-lg w-full max-w-md">
        <h2 className="text-xl font-bold mb-4 text-center">Pridėti naują pirkinių krepšelį</h2>

        {error && <p className="text-red-600 mb-2">{error}</p>}
        {loading && <p>⏳ Įkeliama...</p>}
        {!loading && ingredients.length === 0 && <p>⚠️ Nėra ingredientų.</p>}

        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full px-3 py-2 border rounded"
            placeholder="Prekių krepšelio pavadinimas"
            //required
          />

          <select
            value={recipeIngredients}
            onChange={(e) => setRecipeIngredients(e.target.value)}
            className="block w-full mb-4 p-2 border rounded"
          >
            <option value="">Pasirinkite receptus</option>
            <option value="1">Receptas 1</option>
            <option value="2">Receptas 2</option>
            <option value="3">Receptas 3</option>
          </select>

          <label className="block text-center font-medium">Pasirinkite ingredientus:</label>
          <select
            multiple
            onChange={handleAddIngredient}
            className="block w-full p-2 border rounded mt-2"
            style={{ maxHeight: "150px", overflowY: "auto" }}
          >
            {ingredients.map((ingredient, index) => (
              <option key={ingredient.ingredientId || index} value={ingredient.ingredientId}>
                {ingredient.ingredientName} - {ingredient.quantity} {ingredient.unit}
              </option>
            ))}
          </select>

          {/* Pasirinktų ingredientų lentelė */}
            <table className="w-full border border-orange-300 text-center mb-4">
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

          <div className="flex gap-[1rem] justify-center">
            <button
              type="button"
              onClick={handleCancel}
              className="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white rounded"
            >
              Atšaukti
            </button>

            <button
              type="button"
              onClick={clearForm}
              className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded"
            >
              Išvalyti
            </button>

            <button
              type="submit"
              disabled={loading}
              className="px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded"
            >
              {loading ? "Kuriama..." : "Pridėti"}
            </button>
          </div>
        </form>
      </div>
    </section>
  );
}