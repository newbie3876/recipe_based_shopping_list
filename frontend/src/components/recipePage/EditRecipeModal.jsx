import { useEffect, useState } from "react";
import { updateRecipe } from "../../api/recipePageMethods";

export default function EditRecipeModal({ recipe, onClose, onRecipeUpdated }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [portions, setPortions] = useState("");
    const [link, setLink] = useState("");
    const [categoryId, setCategoryId] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (recipe) {
            setName(recipe.name || "");
            setDescription(recipe.description || "");
            setPortions(recipe.portions || "");
            setLink(recipe.link || "");
            setCategoryId(recipe.categoryId || "");
        }
    }, [recipe]);

    const handleSubmit = async(e) => {
        e.preventDefault();
        setLoading(true);

        const updatedRecipe = {
            ...recipe,
            name,
            description,
            portions,
            link,
            categoryId: parseInt(categoryId, 10),
        };

        try {
            const saved = await updateRecipe(recipe.id, updatedRecipe); // ← čia svarbiausia
            onRecipeUpdated(saved);
            onClose();
        } catch (err) {
            console.error("Klaida atnaujinant receptą:", err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <section className="fixed inset-0 bg-opacity-50 flex items-center justify-center z-50 text-center">
            <div className="bg-white p-6 rounded shadow-lg w-96">
                <h2 className="text-xl font-bold mb-4">Redaguoti receptą</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded"
                        placeholder="Recepto pavadinimas"
                        required
                    />
                    <textarea
                        value={description}
                        placeholder="Recepto aprašymas"
                        onChange={(e) => setDescription(e.target.value)}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded"
                        rows="2"
                    />

                    <input type="number"
                        value={portions}
                        onChange={(e) => setPortions(e.target.value)}
                        placeholder="Porcijų skaičius"
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded"
                        min="1"
                        required
                    />

                    <input type="url" 
                        value={link}
                        onChange={(e) => setLink(e.target.value)}
                        placeholder="URL nuorodą į receptą"
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded"
                    />

                    <select 
                        value={categoryId}
                        onChange={(e) => setCategoryId(e.target.value)}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded"
                    >
                        <option value="">Pasirinkite kategoriją</option>
                        <option value="1">Desertas</option>
                        <option value="2">Gėrimas</option>
                        <option value="3">Salotos</option>
                        <option value="4">Užkandis</option>
                        <option value="5">Sriuba</option>
                        <option value="6">Pagrindinis patiekalas</option>
                    </select>
                    <div className="flex justify-center space-x-2">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-400 text-white rounded hover:bg-gray-500"
                        >
                            Atšaukti
                        </button>
                        <button
                            type="submit"
                            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                        >
                            Išsaugoti
                        </button>
                    </div>
                </form>
            </div>
        </section>
    );
}
