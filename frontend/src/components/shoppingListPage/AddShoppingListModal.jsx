// import { useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import { fetchShoppingLists, createShoppingList } from "../../services/shoppingListService";

// export default function AddShoppingListModal({ userId }) {
//     const navigate = useNavigate();
//     const [name, setName] = useState("");
//     const [createdAt, setCreatedAt] = useState("");
//     const [recipeIngredients, setRecipeIngredients] = useState("");
//     const [independentIngredients, setIndependentIngredients] = useState([]); // Pasirinkti ingredientai
//     const [ingredients, setIngredients] = useState([]); // Galimi ingredientai
//     const [shoppingLists, setShoppingLists] = useState([]);
//     const [error, setError] = useState(null);
//     const [loading, setLoading] = useState(false);

//     const handleCancel = () => {
//         navigate("/"); // Nukreipiame vartotoją į pagrindinį puslapį
//     };

//     useEffect(() => {
//         if (!userId) return;

//         const fetchData = async () => {
//             try {
//                 const data = await fetchShoppingLists(userId);
//                 setShoppingLists(data);

//                 // Ištraukia ingredientus su apsauga nuo `undefined`
//                 const extractedIngredients = data.flatMap(list =>
//                     list.items?.map(item => ({
//                     ingredientId: item.ingredientId ?? item.id,
//                     ingredientName: item.ingredientName,
//                     quantity: item.quantity,
//                     unit: item.unit,
//                     unitId: item.unitId,
//                     ingredientCategory: Array.isArray(item.ingredientCategory)
//                         ? item.ingredientCategory.map(cat => ({
//                         id: cat.id,
//                         categoryName: cat.categoryName
//                     }))
//                     : []
//                 })) || []
//                 );

//                 //console.log("ingredients:", extractedIngredients);

//                 setIngredients(extractedIngredients);
//             } catch (err) {
//                 setError("Nepavyko gauti ingredientų.");
//             } finally {
//                 setLoading(false);
//             }
//         };

//         fetchData();
//     }, [userId]);

//     // Funkcija kelių ingredientų pasirinkimui
//     const handleAddIngredient = (e) => {
//     const selectedOptions = Array.from(e.target.selectedOptions).map(option => Number(option.value));

//     const selectedIngredients = ingredients.filter(ingredient =>
//         selectedOptions.includes(ingredient.ingredientId)
//     );

//     setIndependentIngredients(selectedIngredients);
//     };

//     const handleSubmit = async (e) => {
//     e.preventDefault();
//     setLoading(true);
//     setError(null);

//     try {
//         // 🔍 Patikrink, ar yra ingredientų ir visi turi reikiamus laukus
//         const items = independentIngredients.map(i => ({
//             ingredientId: Number(i.ingredientId),
//             //ingredientName: i.ingredientName,
//             quantity: Number(i.quantity),
//             unit: i.unit,
//             //unitId: Number(i.unitId),
//             ingredientCategory: i.ingredientCategory?.map(cat => ({
//                 //id: Number(cat.id),
//                 categoryName: cat.categoryName
//             })) //|| [] // Jei nėra kategorijų, siųsk tuščią masyvą
//         }));

//         // console.log("Siunčiami duomenys į serverį:", {
//         //     userId,
//         //     name,
//         //     createdAt,
//         //     recipeIngredients,
//         //     items
//         // });

//         await createShoppingList({
//             userId,
//             //createdAt,
//             //recipeIngredients,
//             items
//         });
        
//         // Peradresavimas į sąrašų puslapį
//         localStorage.setItem("selectedIngredients", JSON.stringify(independentIngredients));
//         navigate("/shoppinglists");


//     } catch (err) {
//         console.error("❌ Klaida kuriant prekių krepšelį:", err);
//         setError("Nepavyko sukurti prekių krepšelio.");
//     } finally {
//         setLoading(false);
//     }
//     };

//     const clearForm = () => {
//         setName("");
//         setCreatedAt("");
//         setRecipeIngredients("");
//         setIndependentIngredients([]);
//     };

//     return (
//         <section className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50">
//             <div className="bg-white p-6 rounded shadow-lg w-full max-w-md">
//                 <h2 className="text-xl font-bold mb-4 text-center">Pridėti naują pirkinių krepšelį</h2>

//                 {error && <p className="text-red-600 mb-2">{error}</p>}
//                 {loading && <p>⏳ Įkeliama...</p>}
//                 {!loading && shoppingLists.length === 0 && <p>⚠️ Nėra pirkinių sąrašų.</p>}

//                 <form onSubmit={handleSubmit} className="space-y-4">
//                     <input
//                         type="text"
//                         value={name}
//                         onChange={(e) => setName(e.target.value)}
//                         className="w-full px-3 py-2 border rounded"
//                         placeholder="Prekių krepšelio pavadinimas"
//                         required
//                     />

//                     <select
//                         value={recipeIngredients}
//                         onChange={(e) => setRecipeIngredients(e.target.value)}
//                         className="block w-full mb-4 p-2 border rounded"
//                     >
//                         <option value="">Pasirinkite receptus</option>
//                         <option value="1">Receptas 1</option>
//                         <option value="2">Receptas 2</option>
//                         <option value="3">Receptas 3</option>
//                     </select>

//                     <label className="block text-center font-medium">Pasirinkite ingredientus:</label>
//                     <select
//                         multiple // 🔥 Leidžia pasirinkti kelis ingredientus
//                         onChange={handleAddIngredient}
//                         className="block w-full p-2 border rounded mt-2"
//                     >
//                         {ingredients.map((ingredient, index) => (
//                             <option key={ingredient.ingredientId || index} value={ingredient.ingredientId}>
//                                 {ingredient.ingredientName} - {ingredient.quantity} {ingredient.unit}
//                             </option>
//                         ))}
//                     </select>

//                     {/* Rodomi pasirinkti ingredientai */}
//                     <ul className="mb-4">
//                         {independentIngredients.map((ingredient) => (
//                             <li key={ingredient.ingredientId}>
//                                 {ingredient.ingredientName} - {ingredient.quantity} {ingredient.unit}
//                             </li>
//                         ))}
//                     </ul>

//                     <div className="flex gap-[1rem] justify-center">
//                         <button
//                             type="button"
//                             onClick={handleCancel}
//                             className="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white rounded"
//                         > Atšaukti </button>

//                         <button
//                             type="button"
//                             onClick={clearForm}
//                             className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded"
//                         > Išvalyti </button>

//                         <button
//                             type="submit"
//                             disabled={loading}
//                             className="px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded"
//                         > {loading ? "Kuriama..." : "Pridėti"} </button>
//                     </div>
//                 </form>
//             </div>
//         </section>
//     );
// }