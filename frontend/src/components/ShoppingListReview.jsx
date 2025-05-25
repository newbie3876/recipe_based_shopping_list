// import { useLocation } from "react-router-dom";

// export default function ShoppingListReview() {
//   const location = useLocation();
//   const selectedIngredients = location.state?.selectedIngredients || [];

//   return (
//     <div>
//       <h2>Pasirinkti ingredientai:</h2>
//       {selectedIngredients.length === 0 && <p>Ingredientų nėra.</p>}
//       <ul>
//         {selectedIngredients.map((ing, index) => (
//           <li key={ing.ingredientId || index}>
//             {ing.ingredientName} — {ing.quantity ?? 1} {ing.unit || ""}
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// }
