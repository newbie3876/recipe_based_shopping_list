// import React, { useState, useEffect } from "react";
// import ShoppingList from "./ShoppingList";
// import AddShoppingListModal from "./AddShoppingListModal";
// import { fetchShoppingLists } from "../services/shoppingListService";

// export default function ShoppingListPage({ userId }) {
//   const [shoppingLists, setShoppingLists] = useState([]);
//   const [showModal, setShowModal] = useState(false);
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState(null);

//   useEffect(() => {
//     if (!userId) return;

//     const fetchData = async () => {
//       try {
//         const data = await fetchShoppingLists(userId);
//         setShoppingLists(data);
//       } catch (err) {
//         setError(err.message);
//       } finally {
//         setLoading(false);
//       }
//     };

//     fetchData();
//   }, [userId]);

//   // 👇 ŠTAI ČIA įrašyk šitą funkciją
//   const handleShoppingListAdded = (newList) => {
//     setShoppingLists((prevLists) => [...prevLists, newList]);
//   };

//   return (
//     <div>
//       <button onClick={() => setShowModal(true)}>➕ Pridėti sąrašą</button>

//       <ShoppingList userId={userId} shoppingLists={shoppingLists} loading={loading} error={error} />

//       {showModal && (
//         <AddShoppingListModal
//           userId={userId}
//           onClose={() => setShowModal(false)}
//           onShoppingListAdded={handleShoppingListAdded}
//         />
//       )}
//     </div>
//   );
// }