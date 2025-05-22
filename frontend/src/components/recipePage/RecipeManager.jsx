import { useEffect, useState } from "react";
import {
    fetchRecipes,
    createRecipe,
    updateRecipe,
    deleteRecipeById
} from "../api/recipes";

export default function RecipeManager() {
    const [recipes, setRecipes] = useState([]);
    const [newRecipe, setNewRecipe] = useState({ name: "", description: "" });

    useEffect(() => {
        loadRecipes();
    }, []);

    async function loadRecipes() {
        try {
            const data = await fetchRecipes();
            setRecipes(data);
        } catch (error) {
            console.error("Klaida gaunant receptus:", error);
        }
    }

    async function handleCreate() {
        try {
            const created = await createRecipe(newRecipe);
            setRecipes([...recipes, created]);
            setNewRecipe({ name: "", description: "" });
        } catch (error) {
            console.error("Klaida kuriant receptą:", error);
        }
    }

    async function handleUpdate(id) {
        const updatedData = { name: "Naujas pavadinimas", description: "Naujas aprašymas" };
        try {
            const updated = await updateRecipe(id, updatedData);
            setRecipes(recipes.map(r => r.id === id ? updated : r));
        } catch (error) {
            console.error("Klaida atnaujinant receptą:", error);
        }
    }

    async function handleDelete(id) {
        try {
            await deleteRecipeById(id);
            setRecipes(recipes.filter(r => r.id !== id));
        } catch (error) {
            console.error("Klaida trinant receptą:", error);
        }
    }

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold mb-4">Receptai</h1>

            <div className="mb-6">
                <input
                    type="text"
                    placeholder="Pavadinimas"
                    value={newRecipe.name}
                    onChange={e => setNewRecipe({ ...newRecipe, name: e.target.value })}
                    className="border p-2 mr-2"
                />
                <input
                    type="text"
                    placeholder="Aprašymas"
                    value={newRecipe.description}
                    onChange={e => setNewRecipe({ ...newRecipe, description: e.target.value })}
                    className="border p-2 mr-2"
                />
                <button onClick={handleCreate} className="bg-green-500 text-white px-4 py-2 rounded">
                    Pridėti
                </button>
            </div>

            {recipes.map(recipe => (
                <div key={recipe.id} className="mb-2 flex justify-between items-center border-b pb-2">
                    <div>
                        <strong>{recipe.name}</strong> - {recipe.description}
                    </div>
                    <div className="space-x-2">
                        <button onClick={() => handleUpdate(recipe.id)} className="text-blue-500">✏️</button>
                        <button onClick={() => handleDelete(recipe.id)} className="text-red-500">🗑️</button>
                    </div>
                </div>
            ))}
        </div>
    );
}
