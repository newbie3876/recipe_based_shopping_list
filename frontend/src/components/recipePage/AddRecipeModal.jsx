import { useState } from "react";
import { createRecipe } from "../../api/recipePageMethods";

export default function AddRecipeModal({ onClose, onRecipeAdded }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [portions, setPortions] = useState("");
    const [link, setLink] = useState("");
    const [categoryId, setCategoryId] = useState("");

    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const newRecipe = await createRecipe({
                name,
                description,
                categoryId: parseInt(categoryId, 10),
                portions,
                link,
            });

            onRecipeAdded(newRecipe); // iš RecipePage – įtraukia į sąrašą
            onClose(); // uždaro modalą
        } catch (err) {
            console.error("Klaida kuriant receptą:", err);
            setError("Nepavyko sukurti recepto.");
        } finally {
            setLoading(false);
        }
    };

    const handleChange = () => {
        setName("");
        setDescription("");
        setPortions("");
        setLink("");
        setCategoryId("");
    };

    return (
        <section className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50">
            <div className="bg-white p-6 rounded shadow-lg w-full max-w-md">
                <h2 className="text-xl font-bold mb-4 text-center">Pridėti naują receptą</h2>

                {error && <p className="text-red-600 mb-2">{error}</p>}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="w-full px-3 py-2 border rounded"
                        placeholder="Recepto pavadinimas"
                        required
                    />

                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        className="w-full px-3 py-2 border rounded"
                        placeholder="Recepto aprašymas"
                    />

                    <input type="number"
                        value={portions}
                        onChange={(e) => setPortions(e.target.value)}
                        className="w-full px-3 py-2 border rounded"
                        placeholder="Porcijų skaičius"
                        required
                    />

                    <input type="url" 
                        value={link}
                        onChange={(e) => setLink(e.target.value)}
                        className="w-full px-3 py-2 border rounded"
                        placeholder="URL nuoroda į receptą"
                    />
                    
                    <select
                        value={categoryId}
                        onChange={(e) => setCategoryId(e.target.value)}
                        className="block w-full mb-4 p-2 border rounded"
                    >
                        <option value="">Pasirinkite kategoriją</option>
                        <option value="1">Desertas</option>
                        <option value="2">Gėrimas</option>
                        <option value="3">Salotos</option>
                        <option value="4">Užkandis</option>
                        <option value="5">Sriuba</option>
                        <option value="6">Pagrindinis patiekalas</option>
                    </select>

                    <div className="flex gap-[1rem] justify-center">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white rounded"
                        > Atšaukti </button>

                        <button
                            type="button"
                            onClick={handleChange}
                            className="px-4 py-2 bg-red-500 hover:bg-red-700 text-white rounded"
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
