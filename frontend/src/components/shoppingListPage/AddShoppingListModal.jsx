import { useState } from "react";
import { createShoppingList } from "../../services/shoppingListMethods";

export default function AddShoppingListModal({onClose, onShoppingListAdded}) {
    const [name, setName] = useState("");
    const [createdAt, setCreatedAt] = useState("");
    const [recipeIngredients, setRecipeIngredients] = useState("");
    const [independentIngredients, setIndependentIngredients] = useState("");

    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        const token = localStorage.getItem("token");

        if (!token) {
            setError("Prašome prisijungti - trūksta autentifikacijos žetono.");
            setLoading(false);
            return;
        }

        try {
            const newShoppingList = await createShoppingList({
                name,
                createdAt,
                recipeIngredients,
                independentIngredients
            }, token); // <- PERDUODAME TOKENĄ

            onShoppingListAdded(newShoppingList);
            onClose();
        } catch (err) {
            console.error("Klaida kuriant prekių krepšelį:", err);
            setError("Nepavyko sukurti prekių krepšelio.");
        } finally {
            setLoading(false);
        }
    };

        
    const clearForm = () => {
        setName("");
        setCreatedAt("");
        setRecipeIngredients("");
        setIndependentIngredients("");
    }

    return (
        <section className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50">
            <div className="bg-white p-6 rounded shadow-lg w-full max-w-md">
                <h2 className="text-xl font-bold mb-4 text-center">Pridėti naują pirkinių krepšelį</h2>

                {error && <p className="text-red-600 mb-2">{error}</p>}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="w-full px-3 py-2 border rounded"
                        placeholder="Prekių krepšelio pavadinimas"
                        required
                    />

                    <select
                        value={recipeIngredients}
                        onChange={(e) => setRecipeIngredients(e.target.value)}
                        className="block w-full mb-4 p-2 border rounded"
                    >
                        <option value="">Pasirinkite receptus</option>
                        <option value="1">Receptas 1</option>
                        <option value="2">Receptas 2</option>
                        <option value="3">Receptas 3</option>
                    </select>
                    
                    <select
                        value={independentIngredients}
                        onChange={(e) => setIndependentIngredients(e.target.value)}
                        className="block w-full mb-4 p-2 border rounded"
                    >
                        <option value="">Pasirinkite ingredientus</option>
                        <option value="1">Ingredientas 1</option>
                        <option value="2">Ingredientas 2</option>
                        <option value="3">Ingredientas 3</option>
                    </select>

                    <div className="flex gap-[1rem] justify-center">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white rounded"
                        > Atšaukti </button>

                        <button
                            type="button"
                            onClick={clearForm}
                            className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded"
                        > Išvalyti </button>

                        <button
                            type="submit"
                            disabled={loading}
                            className="px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded"
                        > {loading ? "Kuriama..." : "Pridėti"} </button>
                    </div>
                </form>
            </div>
        </section>
    );

}