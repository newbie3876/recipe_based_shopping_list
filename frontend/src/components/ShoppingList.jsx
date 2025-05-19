import React, { useState, useEffect } from "react";
import { fetch } from "../services/shoppingListService";

const ShoppingList = ({ userId }) => {
  const [shoppingLists, setShoppingLists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!userId) return; // Patikriname, ar vartotojo ID egzistuoja

    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/shoppinglists/${userId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`, // 👈 Jei API reikalauja autentifikacijos
          },
          credentials: "include", // 👈 Jei backend'as naudoja session cookies
        });

        if (!response.ok) throw new Error(`HTTP klaida! Statusas: ${response.status}`);
        
        const data = await response.json();
        console.log("Gauti duomenys:", data); // 👀 Patikrina API atsakymą
        setShoppingLists(data);
      } catch (err) {
        setError(`❌ Klaida gaunant ingredientų sąrašą: ${err.message}`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [userId]); // 👈 `useEffect` priklausomybė: kai `userId` keičiasi, paleidžiama nauja užklausa

  if (loading) return <p>⏳ Įkeliama...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!shoppingLists.length) return <p>⚠️ Nėra pirkinių sąrašų.</p>;

  return (
    <div>
      <h2>🛒 Pirkinių sąrašai</h2>
      {shoppingLists.map((list) => (
        <div key={list.id} className="mb-4">
          <h3 className="text-lg font-bold">{`Sąrašas #${list.id}`}</h3>
          <p>Sukurta: {new Date(list.createdAt).toLocaleString()}</p>

          <table className="w-full border border-gray-300 mt-2">
            <thead>
              <tr className="bg-gray-200">
                <th className="p-2">Ingredientas</th>
                <th className="p-2">Kiekis</th>
                <th className="p-2">Vienetas</th>
                <th className="p-2">Kategorija</th>
              </tr>
            </thead>
            <tbody>
              {list.items && list.items.length > 0 ? (
                list.items.map((item, index) => (
                  <tr key={index} className="border-t border-gray-300">
                    <td className="p-2">{item.ingredientName}</td>
                    <td className="p-2">{item.quantity}</td>
                    <td className="p-2">{item.unit}</td>
                    <td className="p-2">
                      {item.ingredientCategory?.length > 0
                        ? item.ingredientCategory.map((category) => category.categoryName).join(", ")
                        : "Nėra kategorijos"}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="4" className="p-2 text-center text-red-500">
                    ⚠️ Šis sąrašas neturi ingredientų.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
};

export default ShoppingList;