import { useEffect, useState } from "react";
import RecipeList from "./RecipeList";
import EditRecipeModal from "./EditRecipeModal";
import DeleteConfirmationModal from "./DeleteConfirmationModal";
import AddRecipeModal from "./AddRecipeModal";
import { fetchRecipes, deleteRecipeById } from "../../services/recipePageMethods";

export default function RecipePage() {
    const [recipes, setRecipes] = useState([]);
    const [isAddFormOpen, setIsAddFormOpen] = useState(false);
    const [recipeToEdit, setRecipeToEdit] = useState(null);
    const [recipeToDelete, setRecipeToDelete] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        fetchRecipes()
            .then(data => {
                setRecipes(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Nepavyko gauti receptų: ", err);
                setLoading(false);
            });
    }, []);

    const handleIngredientAdded = (recipeId, ingredient) => {
        fetchRecipes().then(data => setRecipes(data));
    };

    return (
        <main className="h-screen bg-orange-200 flex flex-col items-center">
            <h1 className="text-2xl text-center font-bold py-4">Mano receptai</h1>

            <RecipeList 
                recipes={recipes}
                onEdit={(recipe) => setRecipeToEdit(recipe)}
                onDelete={(recipe) => setRecipeToDelete(recipe)}
                onIngredientAdded={handleIngredientAdded}
            />

            <button onClick={() => setIsAddFormOpen(true)} className="m-4 px-4 py-2 bg-green-500 hover:bg-green-600 text-white rounded">
                Pridėti receptą
            </button>

            {isAddFormOpen && (
                <AddRecipeModal
                    onClose={() => setIsAddFormOpen(false)}
                    onRecipeAdded={(newRecipe) =>
                        setRecipes(prev => [...prev, newRecipe])
                    }
                />
            )}

            {recipeToEdit && (
                <EditRecipeModal 
                    recipe={recipeToEdit}
                    ingredients={recipeToEdit.ingredients}
                    onClose={() => setRecipeToEdit(null)}
                    onRecipeUpdated={(updatedRecipe) =>
                        setRecipes(prev => prev.map(r => r.id === updatedRecipe.id ? updatedRecipe : r))
                    }
                />
            )}

            {recipeToDelete && (
                <DeleteConfirmationModal
                    recipe={recipeToDelete}
                    onCancel={() => setRecipeToDelete(null)}
                    onConfirm={() => {
                        deleteRecipeById(recipeToDelete.id)
                            .then(() => {
                                setRecipes(prev => prev.filter(r => r.id !== recipeToDelete.id));
                                setRecipeToDelete(null);
                            })
                        .catch(err => console.error("Klaida trinant receptą:", err));
                    }}

                />
            )}
        </main>
    );
}
