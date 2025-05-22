import { useEffect, useState } from "react";
import ShoppingList from "./ShoppingList";
import DeleteConfirmationModal from "./DeleteConfirmationModal";
import AddShoppingListModal from "./AddShoppingListModal";
import { fetchShoppingLists, deleteShoppingListById} from "../../services/shoppingListMethods";

export default function ShoppingListPage() {
    const [shoppingLists, setShoppingLists] = useState([]);
    const [isAddFormOpen, setIsAddFormOpen] = useState(false);
    const [shoppingListToDelete, setShoppingListToDelete] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        fetchShoppingLists()
            .then(data => {
                setShoppingLists(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Nepavyko gauti prekių krepšelių: ", err);
                setLoading(false);
            });
    }, []);

    return (
        <main className="h-screen bg-orange-200 flex flex-col items-center">
            <h1 className="text-2xl text-center font-bold py-4">Mano pirkinių krepšeliai</h1>

            <ShoppingList 
                shoppingLists={shoppingLists}
                onDelete={(shoppingList) => setShoppingListToDelete(shoppingList)}
            />

            <button onClick={() => setIsAddFormOpen(true)} className="m-4 px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded">
                Pridėti prekių krepšelį
            </button>

            {isAddFormOpen && (
                <AddShoppingListModal
                    onClose={() => setIsAddFormOpen(false)}
                    onShoppingListAdded={(newShoppingList) =>
                        setShoppingLists(prev => [...prev, newShoppingList])
                    }
                />
            )}

            {shoppingListToDelete && (
                <DeleteConfirmationModal
                    shoppingList={shoppingListToDelete}
                    onCancel={() => setShoppingListToDelete(null)}
                    onConfirm={() => {
                        deleteShoppingListById(shoppingListToDelete.id)
                            .then(() => {
                                setShoppingLists(prev => prev.filter(l => l.id !== shoppingListToDelete.id));
                                setShoppingListToDelete(null);
                            })
                        .catch(err => console.error("Klaida trinant prekių krepšelį:", err));
                    }}

                />
            )}
        </main>
    );
}