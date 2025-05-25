import React, { useState, useEffect } from "react";
//import { fetchShoppingLists } from "../services/shoppingListService";
import { useNavigate } from "react-router-dom";

const ShoppingListReview = ({ userId }) => {
    const [shoppingLists, setShoppingLists] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

  useEffect(() => {
  const allData = localStorage.getItem("shoppingListsByUser");
  if (allData) {
    try {
      const parsed = JSON.parse(allData);
      const userLists = parsed[userId] || [];
      setShoppingLists(userLists);
    } catch (err) {
      console.error("Klaida nuskaitant sąrašus:", err);
      setError("Nepavyko nuskaityti duomenų.");
    }
    }
    }, [userId]);

  const handleView = () => {
    navigate(`/shoppinglist`); // pvz., detalesnis vaizdas pagal ID
  };

  const handleDelete = (id) => {
  const allData = JSON.parse(localStorage.getItem("shoppingListsByUser") || "{}");
  const userLists = allData[userId] || [];

  const updated = userLists.filter(list => list.id !== id);
    allData[userId] = updated;

    localStorage.setItem("shoppingListsByUser", JSON.stringify(allData));
    setShoppingLists(updated);
    };

  const handleCreate = () => {
    navigate('/create-shoppinglists');
  };


  return (
    <div className="min-h-screen w-full bg-orange-50 flex flex-col">
      <div className="bg-orange-200 p-4">
        <h1 className="text-2xl font-bold text-center">Pirkinių krepšeliai</h1>
      </div>

      <div className="flex-grow p-4 max-w-6xl mx-auto w-full">
        <div className="bg-orange-100 p-4 rounded-lg border border-orange-200 shadow-md h-full">
          <div className="mb-6">
            {/* <div className="text-xl font-semibold mb-4 text-center">Ingredientai</div> */}

            <div className="overflow-x-auto">
              {/* Lentelės antraštės */}
              <div className="grid grid-cols-4 w-full border border-orange-300">
                <div className="bg-orange-200 p-2 font-medium text-center">#</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Pirkinių krepšelio pavadinimas</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Sukūrimo data</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Veiksmai</div>
              </div>

              {/* Lentelė su prekių krepšeliais */}
              {shoppingLists.length > 0 ? (
                <table className="w-full border border-orange-300 text-center mb-4">
                  <tbody>
                    {shoppingLists.map((ing, i) => (
                      <tr key={ing.ingredientId || i} className="grid grid-cols-4 border border-orange-300 bg-orange-50 text-center">
                        <td className="p-2 border-orange-300">{i + 1}</td>
                        <td className="p-2 border-orange-300">{ing.name || "–"}</td>
                        <td className="p-2 border-orange-300">{ing.createdAt ?? 1}</td>
                        <td className="p-2  border-orange-300">
                            <button
                                type="button"
                                onClick={() => handleView(ing.ingredientId)}
                                className="px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded"
                            >
                            Peržiūrėti
                            </button>
                            <button
                                type="button"
                                onClick={() => handleDelete(ing.ingredientId)}
                                className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded"
                            >
                            Ištrinti
                            </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              ) : (
                <div className="bg-orange-100 p-2 text-center border border-orange-300 text-orange-800">
                  Nėra sukurta pirkinių krepšelių
                </div>
              )}
            </div>
          </div>

          <div className="flex justify-center gap-4 mt-8">
            <button
              onClick={handleCreate}
              className="bg-green-500 hover:bg-green-700 px-4 py-2 rounded text-white border border-green-600">
              Sukurti pirkinių sąrašą
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ShoppingListReview;