import { useState, useEffect } from "react";
import AddIngredientModal from "./AddIngredientModal";
import ViewRecipeModal from "./ViewRecipeModal";

export default function RecipeList({ recipes, onEdit, onDelete, onIngredientAdded }) {
    const [selectedRecipeId, setSelectedRecipeId] = useState(null);
    const[selectedRecipeForView, setSelectedRecipeForView] = useState(null);
    const [ingredients, setIngredients] = useState([]);

    const fetchIngredients = async () => {
        try {
            const res = await fetch(`/api/ingredients`, {
                headers: { 'Authorization': `Bearer ${localStorage.getItem("token")}` }
            });
            if (!res.ok) throw new Error(`Fetch klaida: ${res.status}`);
            const data = await res.json();
            setIngredients(data);
        } catch (err) {
            console.error("Klaida kraunant ingredientus:", err);
            setIngredients([]);
        }
    };

    useEffect(() => {
        if (selectedRecipeForView?.id) {
            fetchIngredients(selectedRecipeForView.id);
        } else {
            setIngredients([]);
        }
    }, [selectedRecipeForView]);


    if (!recipes || recipes.length === 0) {
        return (
            <div className="text-center text-gray-500 mt-8">
                Nėra pridėtų receptų.
            </div>
        );
    }

    const handleOpenModal = (recipeId) => {
        setSelectedRecipeId(recipeId);
    };

    const handleCloseModal = () => {
        setSelectedRecipeId(null);
    };

    return (
        <div className="flex flex-wrap gap-[1rem] justify-center">
            {recipes.map((recipe) => (
                <div
                    key={recipe.id}
                    className="bg-white p-4 rounded shadow flex flex-col items-center justify-between gap-[5px]"
                >
                    <h2 className="text-lg font-semibold">{recipe.name || "nepriskirta"}</h2>
                    <p>Aprašymas: {recipe.description || "nepriskirta"}</p>
                    <p>Porcijų skaičius: {recipe.portions || "nepriskirta"}</p>
                    <p>Nuoroda: {recipe.link || "nepriskirta"}</p>

                    <div className="flex gap-[8px]">
                        <p>Kategorija: {recipe.categoryName || "nepriskirta"}</p>
                    </div>

                    <div className="flex flex-col justify-between items-center gap-[5px]">
                         <button
                            onClick={() => onDelete(recipe)}
                            className="px-3 py-1 text-sm bg-red-500 text-white rounded hover:bg-red-600 w-[10rem]"
                        > Ištrinti</button>

                        <button
                            onClick={() => onEdit(recipe)}
                            className="px-3 py-1 text-sm bg-yellow-400 text-white rounded hover:bg-yellow-500 w-[10rem]"
                        > Redaguoti</button>

                        <button
                            onClick={() => handleOpenModal(recipe.id)}
                            className="px-3 py-1 text-sm text-white rounded bg-green-500 hover:bg-green-600 w-[10rem]"
                        > Pridėti ingredientą</button>

                        <button
                            onClick={() => setSelectedRecipeForView(recipe)}
                            className="px-3 py-1 text-sm text-white rounded bg-blue-500 hover:bg-blue-600 w-[10rem]"
                            > Peržiūrėti receptą</button>
                    </div>
                </div>
            ))}

            {selectedRecipeId && (
                <AddIngredientModal
                    recipeId={selectedRecipeId}
                    onIngredientAdded={(ingredient) => onIngredientAdded(selectedRecipeId, ingredient)}
                    onClose={handleCloseModal}
                />
            )}

            {selectedRecipeForView && (
                <ViewRecipeModal
                    recipe={selectedRecipeForView}
                    ingredients={ingredients}
                    onClose={() => setSelectedRecipeForView(null)}
                />
            )}
        </div>
    );
}
