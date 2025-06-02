import React, { useState, useEffect } from "react";
import { fetchIngredients } from "../../services/ingredientService";
import { useNavigate } from "react-router-dom";

const ShoppingList = ({ userId }) => {
  const [selectedIngredients, setSelectedIngredients] = useState([]);
  const [independentIngredients, setIndependentIngredients] = useState([]);
  const [shoppingLists, setShoppingLists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  
  // Kiti ingredientai iš localStorage (independent)
  useEffect(() => {
  if (!userId) return;

  try {
    const saved = localStorage.getItem("independentIngredientsByUser");
    if (!saved) return;

    const parsed = JSON.parse(saved);
    const userIngredients = parsed[userId] || [];

    setIndependentIngredients(userIngredients);
  } catch (err) {
    console.error("❌ Nepavyko nuskaityti ingredientų pagal userId iš localStorage", err);
  }
  }, [userId]);

  // Ingredientų sąrašas iš serverio
  const fetchData = async () => {
    try {
      setLoading(true);
      const data = await fetchIngredients(userId);
      setShoppingLists(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!userId) return;
    fetchData();
  }, [userId]);

  const handleBack = () => {
    navigate('/create-shoppinglists'); // norimas adresas
  };

  return (
    <div className="min-h-screen w-full bg-orange-50 flex flex-col">
      <div className="bg-orange-200 p-4">
        <h1 className="text-2xl font-bold text-center">Pirkinių krepšelis</h1>
      </div>

      <div className="flex-grow p-4 max-w-6xl mx-auto w-full">
        <div className="bg-orange-100 p-4 rounded-lg border border-orange-200 shadow-md h-full">
          <div className="mb-6">
            <div className="text-xl font-semibold mb-4 text-center">Ingredientai</div>

            <div className="overflow-x-auto">
              {/* Lentelės antraštės */}
              <div className="grid grid-cols-6 w-full border border-orange-300">
                <div className="bg-orange-200 p-2 font-medium text-center">#</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Ingredientai</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Kiekis</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Vienetas</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Kategorija</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Įsigytas</div>
              </div>

              {/* Receptų ingredientai */}
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-200 p-2 font-medium border border-orange-300 text-center">
                  Receptų ingredientai
                </div>
              </div>

              {selectedIngredients.length > 0 ? (
                selectedIngredients.map((ing, i) => (
                  <div key={i} className="grid grid-cols-6 border border-orange-300 bg-orange-50 text-center">
                    <div className="p-2">{i + 1}</div>
                    <div className="p-2">{ing.ingredientName}</div>
                    <div className="p-2">{ing.quantity}</div>
                    <div className="p-2">{ing.unitName}</div>
                    <div className="p-2">{ing.categoryName}</div>
                    {/* <div className="p-2">
                      {ing.ingredientCategory?.map(cat => cat.categoryName).join(", ") || "–"}
                    </div> */}
                    <div className="p-2">✘</div>
                  </div>
                ))
              ) : (
                <div className="bg-orange-100 p-2 text-center border border-orange-300 text-orange-800">
                  Dar nepridėjote jokių receptų ingredientų
                </div>
              )}

              {/* Kiti ingredientai */}
              <div className="grid grid-cols-1 w-full mt-4">
                <div className="bg-orange-200 p-2 font-medium border border-orange-300 text-center">
                  Kiti ingredientai
                </div>
              </div>

              {/* Lentelė su nepriklausomais ingredientais */}
              {independentIngredients.length > 0 ? (
                <table className="w-full border border-orange-300 text-center mb-4">
                  <tbody>
                    {independentIngredients.map((ing, i) => (
                      <tr key={ing.ingredientId || i} className="grid grid-cols-6 border border-orange-300 bg-orange-50 text-center">
                        <td className="p-2 border-orange-300">{i + 1}</td>
                        <td className="p-2 border-orange-300">{ing.ingredientName || "–"}</td>
                        <td className="p-2 border-orange-300">{ing.quantity}</td>
                        <td className="p-2 border-orange-300">{ing.unitName || "–"}</td>
                        <td className="p-2 border-orange-300">{ing.categoryName || "–"}</td>
                        <td className="p-2 border-orange-300">✘</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              ) : (
                <div className="bg-orange-100 p-2 text-center border border-orange-300 text-orange-800">
                  Nėra papildomų ingredientų
                </div>
              )}
            </div>
          </div>

          <div className="flex justify-center gap-4 mt-8">
            <button className="bg-orange-200 hover:bg-orange-300 px-4 py-2 rounded text-orange-900 border border-orange-300">
              Ištrinti
            </button>
            <button
              onClick={handleBack}
              className="bg-green-500 hover:bg-green-700 px-4 py-2 rounded text-white border border-green-600">
              Grįžti atgal
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ShoppingList;

