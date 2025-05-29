// import React, { useEffect, useState } from "react";
// import { createShoppingList } from "../services/shoppingListService"; // tavo POST funkcija

// const ShoppingListForm = ({userId}) => {
//   const [ingredients, setIngredients] = useState([]);
//   const [units, setUnits] = useState([]);

//   const [selectedIngredient, setSelectedIngredient] = useState("");
//   const [selectedUnit, setSelectedUnit] = useState("");
//   const [quantity, setQuantity] = useState("");

//   useEffect(() => {
//     // Uzkrauname ingredientus is backo
//     fetch("http://localhost:8080/api/ingredients")
//   .then(async (res) => {
//     if (!res.ok) {
//       const text = await res.text();
//       throw new Error(`Serverio klaida: ${text}`);
//     }
//     const text = await res.text();
//     return text ? JSON.parse(text) : [];
//   })
//   .then(setIngredients)
//   .catch((err) => console.error("❌ Klaida kraunant ingredientus:", err));

//     // Uzkrauname vienetus is backo
//     fetch("http://localhost:8080/api/units")
//   .then(async (res) => {
//     if (!res.ok) {
//       const text = await res.text();
//       throw new Error(`Serverio klaida: ${text}`);
//     }
//     const text = await res.text();
//     return text ? JSON.parse(text) : [];
//   })
//   .then(setUnits)
//   .catch((err) => console.error("❌ Klaida kraunant vienetus:", err));
//   }, [userId]);

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     const data = {
//       items: [
//         {
//           ingredientId: parseInt(selectedIngredient),
//           unitId: parseInt(selectedUnit),
//           quantity: parseFloat(quantity),
//         },
//       ],
//     };

//     try {
//       const result = await createShoppingList(data);
//       console.log("✅ Sąrašas sukurtas:", result);
//       alert("✅ Sąrašas sėkmingai pridėtas!");
//       // Reset formos laukai
//       setSelectedIngredient("");
//       setSelectedUnit("");
//       setQuantity("");
//     } catch (err) {
//       console.error("❌ Klaida kuriant sąrašą:", err.message);
//       alert("❌ Nepavyko pridėti sąrašo!");
//     }
//   };

//   return (
//     <form onSubmit={handleSubmit}>
//       <label>Pasirink ingredientą:</label>
//       <select
//         value={selectedIngredient}
//         onChange={(e) => setSelectedIngredient(e.target.value)}
//         required
//       >
//         <option value="">-- Pasirink --</option>
//         {ingredients.map((ingredient) => (
//           <option key={ingredient.id} value={ingredient.id}>
//             {ingredient.name} ({ingredient.category})
//           </option>
//         ))}
//       </select>

//       <label>Pasirink vienetą:</label>
//       <select
//         value={selectedUnit}
//         onChange={(e) => setSelectedUnit(e.target.value)}
//         required
//       >
//         <option value="">-- Pasirink --</option>
//         {units.map((unit) => (
//           <option key={unit.id} value={unit.id}>
//             {unit.name}
//           </option>
//         ))}
//       </select>

//       <label>Kiekis:</label>
//       <input
//         type="number"
//         step="0.01"
//         value={quantity}
//         onChange={(e) => setQuantity(e.target.value)}
//         required
//       />

//       <button type="submit">Pridėti į sąrašą</button>
//     </form>
//   );
// };

// export default ShoppingListForm;