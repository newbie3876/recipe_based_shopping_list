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
  
    <div className="min-h-screen w-full bg-orange-50 flex flex-col">
      <div className="bg-orange-200 p-4">
        <h1 className="text-2xl font-bold text-center">Pirkinių krepšelis</h1>
      </div>
      
      <div className="flex-grow p-4 max-w-6xl mx-auto w-full">
        <div className="bg-orange-100 p-4 rounded-lg border border-orange-200 shadow-md h-full">
          <div className="mb-6">
            <div className="text-xl font-semibold mb-4 text-center">Ingredientai</div>
            
            <div className="overflow-x-auto">
              <div className="grid grid-cols-6 w-full border border-orange-300">
                <div className="bg-orange-200 p-2 font-medium text-center">#</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Ingredientai</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Kiekis</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Vienetas</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Kategorija</div>
                <div className="bg-orange-200 p-2 font-medium text-center">Įsigytas</div>
              </div>
              
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-200 p-2 font-medium border border-orange-300 text-center">
                  Receptų ingredientai
                </div>
              </div>
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-100 p-2 text-center border border-orange-300 text-orange-800">
                  Dar nepridėjote jokių receptų ingredientų
                </div>
              </div>
              
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-200 p-2 font-medium border border-orange-300 text-center">
                  Kiti ingredientai
                </div>
              </div>
              <div className="bg-orange-100 rounded-lg border border-orange-200 shadow-md h-full">
                <div>
                  {loading && <p>⏳ Įkeliama...</p>}
                  {error && <p style={{ color: "red" }}>{error}</p>}
                  {!loading && shoppingLists.length === 0 && <p>⚠️ Nėra pirkinių sąrašų.</p>}
                  {shoppingLists.map((list) => (
                    <div key={list.id} className="shopping-list grid grid-cols-1 text-center">
                      <table>
                        <tbody>
                          {list.items.map((item, index) => (
                            <tr key={index} className="grid grid-cols-6 w-full">
                              <td className="p-2 text-center">{item.id}</td>
                              <td className="p-2 text-center">{item.ingredientName}</td>
                              <td className="p-2 text-center">{item.quantity}</td>
                              <td className="p-2 text-center">{item.unit}</td>
                              <td className="p-2 text-center">
                                {item.ingredientCategory.map(category => category.categoryName).join(", ")}
                              </td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
          
          <div className="flex justify-center gap-4 mt-8">
            <button className="bg-orange-200 hover:bg-orange-300 px-4 py-2 rounded text-orange-900 border border-orange-300">
              Ištrinti
            </button>
            <button className="bg-green-500 hover:bg-green-700 px-4 py-2 rounded text-white border border-green-600">
              Grįžti atgal
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ShoppingList;

// <div>
    //   <h2>🛒 Pirkinių sąrašai</h2>
    //   {loading && <p>⏳ Įkeliama...</p>}
    //   {error && <p style={{ color: "red" }}>{error}</p>}
    //   {!loading && shoppingLists.length === 0 && <p>⚠️ Nėra pirkinių sąrašų.</p>}

    //   {shoppingLists.map((list) => (
    //     <div key={list.id} className="shopping-list">
    //       <h3>{`Sąrašas #${list.id}`}</h3>
    //       <p>Sukurta: {new Date(list.createdAt).toLocaleString()}</p>
          
    //       <table className="shopping-table">
    //         <thead>
    //           <tr>
    //             <th>Ingredientas</th>
    //             <th>Kiekis</th>
    //             <th>Vienetas</th>
    //             <th>Kategorija</th>
    //           </tr>
    //         </thead>
    //         <tbody>
    //           {list.items.map((item, index) => (
    //             <tr key={index}>
    //               <td>{item.ingredientName}</td>
    //               <td>{item.quantity}</td>
    //               <td>{item.unit}</td>
    //               <td>
    //                 {item.ingredientCategory.map(category => category.categoryName).join(", ")}
    //               </td>
    //             </tr>
    //           ))}
    //         </tbody>
    //       </table>
    //     </div>
    //   ))}
    // </div>
  //);
