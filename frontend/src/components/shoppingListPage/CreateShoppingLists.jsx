import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { fetchIngredients } from "../../services/ingredientService";
import { createShoppingList } from "../../services/shoppingListService";

export default function CreateShoppingLists({ userId }) {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [createdAt, setCreatedAt] = useState("");
  const [recipeIngredients, setRecipeIngredients] = useState("");
  const [independentIngredients, setIndependentIngredients] = useState([]); // Pasirinkti ingredientai
  const [ingredients, setIngredients] = useState([]); // Galimi ingredientai
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [selectedIds, setSelectedIds] = useState([]);
  const [selectedIngredients, setSelectedIngredients] = useState([]);

  const handleCancel = () => {
    navigate("/"); // Nukreipiame vartotoją į pagrindinį puslapį
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const data = await fetchIngredients(); // Gaunami ingredientai iš API

        const formattedIngredients = data.map((item) => ({
          ingredientId: item.id, // ar `item.ingredientId` – priklauso nuo API struktūros
          ingredientName: item.ingredientName,
          quantity: item.quantity,
          unitName: item.unitName,
          categoryName: item.categoryName || []
        }));

        setIngredients(formattedIngredients);
      } catch (err) {
        console.error(err);
        setError("Nepavyko gauti ingredientų iš serverio.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [userId]);

  const handleAddIngredient = (e, ingredient) => {
    const ingredientId = ingredient.ingredientId;
    let updatedSelectedIds = [];

    if (e.target.checked) {
      updatedSelectedIds = [...selectedIds, ingredientId];
    } else {
      updatedSelectedIds = selectedIds.filter((id) => id !== ingredientId);
    }

    const updatedSelectedIngredients = ingredients.filter((ing) =>
      updatedSelectedIds.includes(ing.ingredientId)
    );

    setSelectedIds(updatedSelectedIds);
    setIndependentIngredients(updatedSelectedIngredients);
  };

  // const handleSubmit = (e) => {
  //   e.preventDefault();

  //   const selectedIngredientsDetailed = ingredients
  //     .filter((ingredient) => selectedIds.includes(ingredient.ingredientId))
  //     .map((ingredient) => ({
  //       ingredientId: ingredient.ingredientId,
  //       ingredientName: ingredient.ingredientName,
  //       quantity: ingredient.quantity,
  //       unitName: ingredient.unitName,
  //       categoryName: ingredient.categoryName || "–",
  //     }));

  //   if (selectedIngredientsDetailed.length === 0) {
  //     setError("Pasirinkite bent vieną ingredientą.");
  //     return;
  //   }

  //   // Saugojimas į localStorage pagal userId
  //   const allData = JSON.parse(localStorage.getItem("independentIngredientsByUser") || "{}");
  //   allData[userId] = selectedIngredientsDetailed;
  //   localStorage.setItem("independentIngredientsByUser", JSON.stringify(allData));

  //   // Navigavimas į pirkinių krepšelio puslapį
  //   navigate("/shoppinglist");
  // };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    if (!name.trim()) {
      setError("Įveskite pirkinių krepšelio pavadinimą.");
      setLoading(false);
      return;
    }

    const selectedIngredientsDetailed = ingredients
      .filter((ingredient) => selectedIds.includes(ingredient.ingredientId))
      .map((ingredient) => ({
        ingredientId: ingredient.ingredientId,
        ingredientName: ingredient.ingredientName,
        quantity: ingredient.quantity,
        unitName: ingredient.unitName,
        categoryName: ingredient.categoryName || "–",
      }));

    if (selectedIngredientsDetailed.length === 0) {
      setError("Pasirinkite bent vieną ingredientą.");
      setLoading(false);
      return;
    }

    const token = localStorage.getItem("authToken"); // jei naudoji JWT
    if (!token) {
      setError("Reikia būti prisijungus.");
      setLoading(false);
      return;
    }

    const newList = {
      name,
      createdAt: new Date().toISOString(),
      userId,
      items: selectedIngredientsDetailed,
    };

    try {
      await createShoppingList(newList, token);
      navigate("/shoppinglist");
    } catch (err) {
      console.error("❌ Klaida:", err);
      setError("Nepavyko išsaugoti krepšelio į duomenų bazę.");
    } finally {
      setLoading(false);
    }
  };

  const clearForm = () => {
    setName("");
    setCreatedAt("");
    setSelectedIds([]);
    setRecipeIngredients("");
    //setIndependentIngredients([]);
  };

  

  return (
    <div className="bg-orange-200 h-screen">
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
          <div
            style={{
              maxHeight: "100px",
              overflowY: "auto",
              border: "1px solid #ccc",
              borderRadius: "5px",
              padding: "10px",
            }}
          >
            {ingredients.map((ingredient) => (
              <label
                key={ingredient.ingredientId}
                style={{
                  display: "flex",
                  alignItems: "center",
                  marginBottom: "8px",
                  cursor: "pointer",
                }}
              >
                <input
                  type="checkbox"
                  checked={selectedIds.includes(ingredient.ingredientId)}
                  onChange={(e) => handleAddIngredient(e, ingredient)}
                  style={{ marginRight: "10px" }}
                />
                <div>
                  <strong>{ingredient.ingredientName}</strong>{" "}
                  <span style={{ color: "#555", fontSize: "0.9em" }}>
                    ({ingredient.quantity} {ingredient.unitName})
                  </span>
                </div>
              </label>
            ))}
          </div>
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
    </div>
  );
}