import { useState } from "react";
import { addIngredientToRecipe } from "../../api/recipePageMethods";

export default function AddIngredientModal({ recipeId, onIngredientAdded, onClose }){
    const [name, setName] = useState("");
    const [quantity, setQuantity] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        const quantityNumber = parseInt(quantity, 10);
        
        if(isNaN(quantityNumber) || quantityNumber <= 0){
            alert("Kiekis turi būti daugiau arba lygus 1.");
            setLoading(false);
            return;
        }

        try {
            const newIngredient = await addIngredientToRecipe({
                recipeId,
                name,
                quantity: quantityNumber,
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
        setName("");
        setQuantity("");
    };


    return (
        <section className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50">
            <form onSubmit={handleSubmit} className="bg-gray-500 p-6 rounded shadow-lg w-full max-w-md flex flex-col justify-between items-center">
                {error && <p className="text-red-600">{error}</p>}

                <input 
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
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