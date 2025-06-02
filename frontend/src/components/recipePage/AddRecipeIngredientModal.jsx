import { useState } from "react";
import { addIngredientToRecipe } from "../../services/recipePageMethods";

export default function AddRecipeIngredientModal({ recipeId, onIngredientAdded, onClose }){
    const [ingredientName, setIngredientName] = useState("");
    const [quantity, setQuantity] = useState("");
    const [unitId, setUnitId] = useState("");
    const [ingredientCategoryId, setIngredientCategoryId] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        const quantityNumber = parseFloat(quantity);
        
        if(isNaN(quantityNumber) || quantityNumber <= 0){
            alert("Kiekis turi būti daugiau arba lygus 1.");
            setLoading(false);
            return;
        }

        if (!ingredientName.trim()) {
            alert("Ingrediento pavadinimas negali būti tuščias.");
            setLoading(false);
            return;
        }

        try {
            const newIngredient = await addIngredientToRecipe({
                recipeId,
                ingredientId: null,
                ingredientName,
                quantity: quantityNumber,
                unitId: unitId ? parseInt(unitId, 10) : null,
                ingredientCategoryId: ingredientCategoryId ? parseInt(ingredientCategoryId, 10) : null,
            });

            onIngredientAdded(newIngredient);
            clearForm();
            onClose();
        } catch(err) {
            console.error("Klaida pridedant ingredientą: ", err);
            setError("Nepavyko pridėti ingrediento.");
        } finally {
            setLoading(false);
        }
    }

    const clearForm = () => {
        setIngredientName("");
        setQuantity("");
        setUnitId("");
        setIngredientCategoryId("");
    };

    return (
        <section className="fixed inset-0 flex justify-center items-center z-50">
            <form onSubmit={handleSubmit} className="p-6 rounded shadow-lg w-full max-w-md flex flex-col items-center justify-between gap-[1rem] bg-white">
                <h2>Pridėti ingredientą</h2>
                {error && <p className="text-red-600">{error}</p>}

                <input 
                    type="text"
                    value={ingredientName}
                    onChange={(e) => setIngredientName(e.target.value)}
                    className="w-full px-3 py-2 border rounded"
                    placeholder="Ingrediento pavadinimas"
                    required
                />
                <input 
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(e.target.value)}
                    className="w-full px-3 py-2 border rounded"
                    placeholder="Ingrediento kiekis"
                    min="1"
                    step="1"
                    required
                />
                <select
                    value={unitId}
                    onChange={(e) => setUnitId(e.target.value)}
                    className="block w-full p-2 border rounded"
                >
                    <option value="">Pasirinkite matavimo vienetus: </option>
                    <option value="1">g</option>
                    <option value="2">kg</option>
                    <option value="3">l</option>
                    <option value="4">ml</option>
                    <option value="5">pc.</option>
                </select>
                <select
                    value={ingredientCategoryId}
                    onChange={(e) => setIngredientCategoryId(e.target.value)}
                    className="block w-full p-2 border rounded"
                >
                    <option value="">Pasirinkite kategorija: </option>
                    <option value="1">Pienas ir jo gaminiai</option>
                    <option value="2">Mėsa, žuvis ir kiaušiniai</option>
                    <option value="3">Bulvės, ankštiniai augalai ir riešutai</option>
                    <option value="4">Daržovės</option>
                    <option value="5">Vaisiai</option>
                    <option value="6">Duona, makaronai, grūdai, cukrus ir saldainiai</option>
                    <option value="7">Riebalai, aliejus ir sviestas</option>
                </select>
                <div className="flex gap-[1rem]">
                    <button
                    type="button"
                    onClick={clearForm}
                    className="px-4 py-2 bg-gray-400 text-white rounded"
                    > Išvalyti
                    </button>

                    <button
                        type="submit"
                        disabled={loading}
                        className="px-4 py-2 bg-green-500 text-white rounded"
                    > {loading ? "Pridedama..." : "Pridėti"}
                    </button>

                    <button
                        type="button"
                        className="px-4 py-2 bg-red-500 text-white rounded"
                        onClick={onClose}
                    > Atšaukti </button>
                </div>
            </form>
        </section>
    )
}