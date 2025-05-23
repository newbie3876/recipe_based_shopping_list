
//   return (
//     <div style={{ textAlign: "center", padding: "20px" }}>
//       <h2>Pridėti naują ingredientą</h2>
//       <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px", maxWidth: "350px", margin: "auto" }}>
//         <input type="text" placeholder="Ingrediento pavadinimas" value={name} onChange={(e) => setName(e.target.value)} required />
//         <input type="number" placeholder="Kiekis" value={amount} onChange={(e) => setAmount(e.target.value)} required />
//         <input type="text" placeholder="Vienetai (pvz., g, ml, vnt.)" value={unit} onChange={(e) => setUnit(e.target.value)} required />
//         <select value={category} onChange={(e) => setCategory(e.target.value)} required>
//           <option value="">Pasirinkti kategoriją</option>
//           <option value="Vaisiai">Vaisiai</option>
//           <option value="Daržovės">Daržovės</option>
//           <option value="Baltymai">Baltymai</option>
//           <option value="Grūdai">Grūdai</option>
//           <option value="Pieno produktai">Pieno produktai</option>
//           <option value="Prieskoniai">Prieskoniai</option>
//         </select>
//         <button type="submit">Išsaugoti ingredientą</button>
//       </form>

//       {/* 🔹 Ingredientų sąrašas */}
//       {loading && <p>Įkeliama ingredientų sąrašas...</p>}
//       {error && <p>Klaida: {error}</p>}
//       {ingredients.length > 0 && (
//         <div>
//           <h3>Esami ingredientai</h3>
//           <ul>
//             {ingredients.map((ingredient) => (
//                 <li key={ingredient.id}> {/* 🔹 Užtikrink, kad naudojamas unikalus ID */}
//                     {ingredient.ingredientName} – {ingredient.quantity} {ingredient.unitId} ({ingredient.ingredientCategoryId})
//                 </li>
//             ))}
//           </ul>
//         </div>
//       )}
//     </div>
//   );
// }



// import { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { addIngredient } from "../services/ingredientService"; // 🔹 Importuojame API funkciją

// export default function AddIngredient() {
//   const [ingredientName, setIngredientName] = useState("");
//   const [quantity, setQuantity] = useState("");
//   const [unitId, setUnitId] = useState("");
//   const [ingredientCategoryId, setIngredientCategoryId] = useState("");
//   const navigate = useNavigate();

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (!ingredientName.trim() || !quantity.trim() || !unitId.trim() || !ingredientCategoryId.trim()) {
//       alert("Visi laukai privalomi!");
//       return;
//     }

//     const newIngredient = {
//       ingredientName: ingredientName.trim(),
//       quantity: parseFloat(quantity),
//       unitId: unitId.toString(),
//       ingredientCategoryId: ingredientCategoryId.toString(),
//     };

//     try {
//       const response = await addIngredient(newIngredient);
//       alert("✅ Ingredientas sėkmingai pridėtas!");
//       navigate("/");
//     } catch (error) {
//       alert(`❌ Klaida pridedant ingredientą: ${error.message}`);
//     }
//   };

//   return (
//     <div style={{ textAlign: "center", padding: "20px" }}>
//       <h2>Pridėti naują ingredientą</h2>
//       <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px", maxWidth: "350px", margin: "auto" }}>
//         <input type="text" placeholder="Ingrediento pavadinimas" value={ingredientName} onChange={(e) => setIngredientName(e.target.value)} required />
//         <input type="number" placeholder="Kiekis" value={quantity} onChange={(e) => setQuantity(e.target.value)} required />
//         <input type="text" placeholder="Vienetai (pvz., g, ml, vnt.)" value={unitId} onChange={(e) => setUnitId(e.target.value)} required />
//         <input type="text" placeholder="Ingrediento kategorija (ID)" value={ingredientCategoryId} onChange={(e) => setIngredientCategoryId(e.target.value)} required />
//         <button type="submit">Išsaugoti ingredientą</button>
//       </form>
//     </div>
//   );
// }






// import { useState, useEffect } from "react";
// import { useNavigate } from "react-router-dom";
// import { addIngredient } from "../services/ingredientService"; 
// import { fetchUnits, fetchCategories } from "../services/ingredientService"; 

// export default function AddIngredient() {
//   const [ingredientName, setIngredientName] = useState("");
//   const [quantity, setQuantity] = useState("");
//   const [unitId, setUnitId] = useState("");
//   const [ingredientCategoryId, setIngredientCategoryId] = useState("");
//   const [units, setUnits] = useState([]); // Vienetų sąrašas iš duomenų bazės
//   const [categories, setCategories] = useState([]); // Kategorijų sąrašas iš duomenų bazės
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState(null);
//   const navigate = useNavigate();

//   // 🔹 Gauti vienetus ir kategorijas iš API
//   useEffect(() => {
//     const fetchData = async () => {
//       try {
//         const unitData = await fetchUnits();
//         const categoryData = await fetchCategories();
//         setUnits(unitData);
//         setCategories(categoryData);
//       } catch (err) {
//         setError(err.message);
//       } finally {
//         setLoading(false);
//       }
//     };

//     fetchData();
//   }, []);

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (!ingredientName.trim() || !quantity.trim() || !unitId.trim() || !ingredientCategoryId.trim()) {
//       alert("Visi laukai privalomi!");
//       return;
//     }

//     const newIngredient = {
//       ingredientName: ingredientName.trim(),
//       quantity: parseFloat(quantity),
//       unitId: unitId.toString(),
//       ingredientCategoryId: ingredientCategoryId.toString(),
//     };

//     try {
//       await addIngredient(newIngredient);
//       alert("✅ Ingredientas sėkmingai pridėtas!");
//       navigate("/");
//     } catch (error) {
//       alert(`❌ Klaida pridedant ingredientą: ${error.message}`);
//     }
//   };

//   return (
//     <div style={{ textAlign: "center", padding: "20px" }}>
//       <h2>Pridėti naują ingredientą</h2>
//       {loading && <p>🔄 Įkeliami duomenys...</p>}
//       {error && <p>❌ Klaida: {error}</p>}

//       <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px", maxWidth: "350px", margin: "auto" }}>
//         <input type="text" placeholder="Ingrediento pavadinimas" value={ingredientName} onChange={(e) => setIngredientName(e.target.value)} required />
//         <input type="number" placeholder="Kiekis" value={quantity} onChange={(e) => setQuantity(e.target.value)} required />

//         {/* Dinaminis vienetų dropdown */}
//         <select value={unitId} onChange={(e) => setUnitId(e.target.value)} required>
//           <option value="">Pasirinkti vienetus</option>
//           {units.map((unit) => (
//             <option key={unit.id} value={unit.id}>{unit.name}</option>
//           ))}
//         </select>

//         {/* Dinaminis kategorijų dropdown */}
//         <select value={ingredientCategoryId} onChange={(e) => setIngredientCategoryId(e.target.value)} required>
//           <option value="">Pasirinkti kategoriją</option>
//           {categories.map((category) => (
//             <option key={category.id} value={category.id}>{category.name}</option>
//           ))}
//         </select>

//         <button type="submit">Išsaugoti ingredientą</button>
//       </form>
//     </div>
//   );
// }




// import { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { addIngredient } from "../services/ingredientService";

// export default function AddIngredient() {
//   const [ingredientName, setIngredientName] = useState("");
//   const [quantity, setQuantity] = useState("");
//   const [unitId, setUnitId] = useState("");
//   const [ingredientCategoryId, setIngredientCategoryId] = useState("");
//   const navigate = useNavigate();

//   // Vienetų pasirinkimai
//   const unitOptions = [
//     { id: 1, label: "Gramai (g)" },
//     { id: 2, label: "Mililitrai (ml)" },
//     { id: 3, label: "Vienetai (vnt.)" },
//   ];

//   // Kategorijų pasirinkimai
//   const categoryOptions = [
//     { id: 1, label: "Vaisiai" },
//     { id: 2, label: "Daržovės" },
//     { id: 3, label: "Baltymai" },
//     { id: 4, label: "Grūdai" },
//     { id: 5, label: "Pieno produktai" },
//     { id: 6, label: "Prieskoniai" },
//   ];

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (!ingredientName.trim() || !quantity.trim() || !unitId.trim() || !ingredientCategoryId.trim()) {
//       alert("Visi laukai privalomi!");
//       return;
//     }

//     const newIngredient = {
//       ingredientName: ingredientName.trim(),
//       quantity: parseFloat(quantity),
//       unitId: unitId.toString(), // Siunčiame kaip string
//       ingredientCategoryId: ingredientCategoryId.toString(), // Siunčiame kaip string
//     };

//     try {
//       const response = await addIngredient(newIngredient);
//       alert("✅ Ingredientas sėkmingai pridėtas!");
//       navigate("/");
//     } catch (error) {
//       alert(`❌ Klaida pridedant ingredientą: ${error.message}`);
//     }
//   };

//   return (
//     <div style={{ textAlign: "center", padding: "20px" }}>
//       <h2>Pridėti naują ingredientą</h2>
//       <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "15px", maxWidth: "350px", margin: "auto" }}>
//         <input type="text" placeholder="Ingrediento pavadinimas" value={ingredientName} onChange={(e) => setIngredientName(e.target.value)} required />
//         <input type="number" placeholder="Kiekis" value={quantity} onChange={(e) => setQuantity(e.target.value)} required />

//         {/* Dropdown meniu vienetams */}
//         <select value={unitId} onChange={(e) => setUnitId(e.target.value)} required>
//           <option value="">Pasirinkti vienetus</option>
//           {unitOptions.map((unit) => (
//             <option key={unit.id} value={unit.id}>{unit.label}</option>
//           ))}
//         </select>

//         {/* Dropdown meniu kategorijoms */}
//         <select value={ingredientCategoryId} onChange={(e) => setIngredientCategoryId(e.target.value)} required>
//           <option value="">Pasirinkti kategoriją</option>
//           {categoryOptions.map((category) => (
//             <option key={category.id} value={category.id}>{category.label}</option>
//           ))}
//         </select>

//         <button type="submit">Išsaugoti ingredientą</button>
//       </form>
//     </div>
//   );
// }


import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { addIngredient } from "../services/ingredientService";

export default function AddIngredient() {
  const [ingredientName, setIngredientName] = useState("");
  const [quantity, setQuantity] = useState("");
  const [unitId, setUnitId] = useState("");
  const [ingredientCategoryId, setIngredientCategoryId] = useState("");
  const navigate = useNavigate();

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
      const response = await addIngredient(newIngredient);
      alert("✅ Ingredientas sėkmingai pridėtas!");
      navigate("/");
    } catch (error) {
      alert(`❌ Klaida pridedant ingredientą: ${error.message}`);
    }
  };

  return (
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
  );
}
