import { useEffect, useState } from "react";
import { updateRecipe } from "../../services/recipePageMethods";

export default function EditRecipeModal({ recipe, onClose, onRecipeUpdated }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [portions, setPortions] = useState("");
    const [link, setLink] = useState("");
    const [categoryId, setCategoryId] = useState("");
    const [ingredients, setIngredients] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (recipe) {
            setName(recipe.name || "");
            setDescription(recipe.description || "");
            setPortions(recipe.portions || "");
            setLink(recipe.link || "");
            setCategoryId(recipe.categoryId || "");
            setIngredients(recipe.ingredients || []);
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
            ingredients: ingredients.map(ing => ({
                ...ing,
                quantity: parseFloat(ing.quantity),
                categoryId: parseInt(ing.categoryId, 10)
            }))
        };

        try {
            const saved = await updateRecipe(recipe.id, updatedRecipe);
            onRecipeUpdated(saved);
            onClose();
        } catch (err) {
            console.error("Klaida atnaujinant receptą:", err);
        } finally {
            setLoading(false);
        }
    };

    const handleIngredientChange = (index, field, value) => {
        const updated = [...ingredients];
        updated[index] = { ...updated[index], [field]: value };
        setIngredients(updated);
    };

    const addIngredient = () => {
        setIngredients([...ingredients, { name: "", quantity: "", unitId: "", categoryId: "" }]);
    };

    const removeIngredient = (index) => {
        setIngredients(ingredients.filter((_, i) => i !== index));
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
                <h3 className="font-semibold text-left">Ingredientai:</h3>
                    {ingredients.map((ing, index) => (
                        <div key={index} className="grid grid-cols-2 gap-2 mb-2">
                            <input
                                type="text"
                                value={ing.name}
                                onChange={(e) => handleIngredientChange(index, 'name', e.target.value)}
                                placeholder="Ingrediento pavadinimas"
                                className="px-2 py-1 border border-gray-300 rounded"
                            />
                            <input
                                type="number"
                                value={ing.quantity}
                                onChange={(e) => handleIngredientChange(index, 'quantity', e.target.value)}
                                placeholder="Kiekis"
                                className="px-2 py-1 border border-gray-300 rounded"
                            />
                            <select
                                value={ing.unitId}
                                onChange={(e) => handleIngredientChange(index, 'unitId', e.target.value)}
                                placeholder="Matavimo vienetai"
                            >
                                <option value="">Pasirinkite matavimo vienetus: </option>
                                <option value="1">g</option>
                                <option value="2">kg</option>
                                <option value="3">l</option>
                                <option value="4">ml</option>
                                <option value="5">pc.</option>
                            </select>
                            <select
                                value={ing.categoryId}
                                onChange={(e) => handleIngredientChange(index, 'categoryId', e.target.value)}
                                className="px-2 py-1 border border-gray-300 rounded"
                            >
                                <option value="">Kategorija</option>
                                <option value="1">Pienas ir jo gaminiai</option>
                                <option value="2">Mėsa, žuvis ir kiaušiniai</option>
                                <option value="3">Bulvės, ankštiniai augalai ir riešutai</option>
                                <option value="4">Daržovės</option>
                                <option value="5">Vaisiai</option>
                                <option value="6">Duona, makaronai, grūdai, cukrus ir saldainiai</option>
                                <option value="7">Riebalai, aliejus ir sviestas</option>
                            </select>
                        <button
                            type="button"
                            onClick={() => removeIngredient(index)}
                            className="text-red-500 col-span-2 text-right text-sm"
                        > Pašalinti </button>
                    </div>  
                ))}
                    <button
                        type="button"
                        onClick={addIngredient}
                        className="bg-blue-500 text-white px-2 py-1 rounded hover:bg-blue-600 mb-4"
                    > Pridėti ingredientą </button>
                    <div className="flex justify-center space-x-2">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-400 text-white rounded hover:bg-gray-500"
                        > Atšaukti </button>
                        <button
                            type="submit"
                            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                        > Išsaugoti </button>
                    </div>
                    </form>
            </div>
        </section>
    );
}
