import React, { useState, useEffect } from "react";
import { fetchShoppingLists } from "../services/shoppingListService";

const ShoppingList = ({ userId }) => {
  const [shoppingLists, setShoppingLists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!userId) return;

    const fetchData = async () => {
      try {
        const data = await fetchShoppingLists(userId);
        setShoppingLists(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [userId]);

  return (
    <div>
      <h2>🛒 Pirkinių sąrašai</h2>
      {loading && <p>⏳ Įkeliama...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
      {!loading && shoppingLists.length === 0 && <p>⚠️ Nėra pirkinių sąrašų.</p>}

      {shoppingLists.map((list) => (
        <div key={list.id} className="shopping-list">
          <h3>{`Sąrašas #${list.id}`}</h3>
          <p>Sukurta: {new Date(list.createdAt).toLocaleString()}</p>
          
          <table className="shopping-table">
            <thead>
              <tr>
                <th>Ingredientas</th>
                <th>Kiekis</th>
                <th>Vienetas</th>
                <th>Kategorija</th>
              </tr>
            </thead>
            <tbody>
              {list.items.map((item, index) => (
                <tr key={index}>
                  <td>{item.ingredientName}</td>
                  <td>{item.quantity}</td>
                  <td>{item.unit}</td>
                  <td>
                    {item.ingredientCategory.map(category => category.categoryName).join(", ")}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
};

export default ShoppingList;