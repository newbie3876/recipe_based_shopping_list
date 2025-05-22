import { useEffect, useState } from "react";
import {
    fetchShoppingLists,
    createShoppingList,
    deleteShoppingListById
} from "../api/shoppinglists";

export default function ShoppingListManager() {
    const [shoppingLists, setShoppingLists] = useState([]);
    const [newShoppingList, setNewShoppingList] = useState({ name: "", createdAt: "" });

    useEffect(() => {
        loadShoppingLists();
    }, []);

    async function loadShoppingLists() {
        try {
            const data = await fetchShoppingLists();
            setShoppingLists(data);
        } catch (error) {
            console.error("Klaida gaunant prekių krepšelius:", error);
        }
    }

    async function handleCreate() {
        try {
            const created = await createShoppingList(newShoppingList);
            setShoppingLists([...shoppingLists, created]);
            setNewShoppingList({ name: "", createdAt: "" });
        } catch (error) {
            console.error("Klaida kuriant prekių krepšelį:", error);
        }
    }

    async function handleDelete(id) {
        try {
            await deleteShoppingListById(id);
            setShoppingLists(shoppingLists.filter(l => l.id !== id));
        } catch (error) {
            console.error("Klaida trinant prekių krepšelį:", error);
        }
    }

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold mb-4">Prekių krepšeliai</h1>

            <div className="mb-6">
                <input
                    type="text"
                    placeholder="Pavadinimas"
                    value={newShoppingList.name}
                    onChange={e => setNewShoppingList({ ...newShoppingList, name: e.target.value })}
                    className="border p-2 mr-2"
                />

                <button onClick={handleCreate} className="bg-green-500 text-white px-4 py-2 rounded">
                    Pridėti
                </button>
            </div>

            {shoppingLists.map(shoppingList => (
                <div key={shoppingList.id} className="mb-2 flex justify-between items-center border-b pb-2">
                    <div>
                        <strong>{shoppingList.name}</strong> - {shoppingList.createdAt}
                    </div>
                    <div className="space-x-2">
                        <button onClick={() => handleDelete(shoppingList.id)} className="text-red-500">🗑️</button>
                    </div>
                </div>
            ))}
        </div>
    );
}