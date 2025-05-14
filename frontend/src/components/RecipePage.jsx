import { useState, useEffect } from "react";
import AddRecipeForm from "./RecipeRegistration";

export default function RecipePage() {
    const [recipes, setRecipes] = useState([]);
    const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] = useState(false);
    const [recipeToDelete, setRecipeToDelete] = useState(null);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [recipeToEdit, setRecipeToEdit] = useState(null);
    const [isAddFormOpen, setIsAddFormOpen] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8080/api/recipes")
            .then((res) => res.json())
            .then((data) => setRecipes(data))
            .catch((err) => console.error("Klaida gaunant receptus:", err));
    }, []);

    const updateRecipe = (id, updatedRecipe) => {
        fetch(`http://localhost:8080/api/recipes/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedRecipe),
        })
        .then((res) => res.json())
        .then((data) => {
            setRecipes(recipes.map(recipe => recipe.id === id ? data : recipe));
            setIsEditModalOpen(false);
        })
        .catch((err) => console.error("Klaida atnaujinant receptą:", err));
    };

    const handleEditClick = (recipe) => {
        setRecipeToEdit(recipe);
        setIsEditModalOpen(true);
    };

    const handleEditSubmit = (e) => {
        e.preventDefault();

        const updatedRecipe = {
            name: e.target.name.value,
            description: e.target.description.value,
            portions: parseInt(e.target.portions.value, 10),
            link: e.target.link.value,
            category: e.target.category.value,
        };
        updateRecipe(recipeToEdit.id, updatedRecipe);
    };

    const deleteRecipe = (id) => {
        fetch(`http://localhost:8080/api/recipes/${id}`, {
            method: "DELETE",
        })
        .then((res) => {
            if (res.ok) {
                setRecipes(recipes.filter(recipe => recipe.id !== id));
            } else {
                console.error("Nepavyko ištrinti recepto:", res.statusText);
            }
        })
        .catch((err) => console.error("Klaida trinant receptą:", err));
    };

    const handleDeleteClick = (recipe) => {
        setRecipeToDelete(recipe);
        setIsDeleteConfirmationOpen(true);
    };

    const handleCancelDelete = () => {
        setIsDeleteConfirmationOpen(false);
        setRecipeToDelete(null);
    };

    const handleConfirmDelete = () => {
        if (recipeToDelete) {
            deleteRecipe(recipeToDelete.id);
            handleCancelDelete();
        }
    };

    return (
        <main className="h-screen bg-orange-200">
            <article className="flex flex-col justify-center items-center">
                <h1 className="text-2xl font-bold mb-4">Mano receptai</h1>
                {recipes.length === 0 ? (
                    <div className="flex flex-col">
                        <p>Nėra pridėtų receptų.</p>
                        <button className="font-semibold" onClick={() => setIsAddFormOpen(true)}>Pridėti receptą?</button>
                    </div>
                ) : (
                    <ul className="space-y-4 flex flex-wrap justify-center gap-[1rem]">
                        {recipes.map((recipe) => (
                            <li key={recipe.id} className="bg-white p-4 rounded shadow flex flex-col justify-between items-center">
                                <h2 className="text-xl font-semibold">{recipe.name}</h2>
                                <p>{recipe.description}</p>
                                <p>Porcijos: {recipe.portions}</p>
                                <p>Kategorija: {recipe.category}</p>
                                <div className="flex flex-wrap gap-[1rem]">
                                    <button className="font-semibold" onClick={() => handleEditClick(recipe)}>redaguoti</button>
                                    <button className="font-semibold" onClick={() => handleDeleteClick(recipe)}>ištrinti</button>
                                </div>
                            </li>
                        ))}
                    </ul>
                )}
            </article>

            {isDeleteConfirmationOpen && (
                <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg w-1/3 text-center">
                        <h3 className="text-xl font-bold mb-4">Ar tikrai norite ištrinti šį receptą?</h3>
                        <div className="flex justify-between">
                            <button onClick={handleCancelDelete} className="px-4 py-2 bg-gray-400 text-white rounded">Atšaukti</button>
                            <button onClick={handleConfirmDelete} className="px-4 py-2 bg-red-500 text-white rounded">Ištrinti</button>
                        </div>
                    </div>
                </div>
            )}

            {isEditModalOpen && (
                <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg w-1/3 text-center">
                        <h3 className="text-xl font-bold mb-4">Redaguoti receptą</h3>
                        <form onSubmit={handleEditSubmit}>
                            <div className="flex flex-col justify-between">
                                <input
                                type="text"
                                name="name"
                                defaultValue={recipeToEdit.name}
                                placeholder="Recepto pavadinimas"
                                className="mb-4 p-2 border rounded"
                                />
                                <input
                                    type="text"
                                    name="description"
                                    defaultValue={recipeToEdit.description}
                                    placeholder="Recepto aprašymas"
                                    className="mb-4 p-2 border rounded"
                                />
                                <input
                                    type="number"
                                    name="portions"
                                    defaultValue={recipeToEdit.portions}
                                    placeholder="Porcijų skaičius"
                                    className="mb-4 p-2 border rounded"
                                />
                                <input
                                    type="url"
                                    name="link"
                                    defaultValue={recipeToEdit.link}
                                    placeholder="Nuoroda į receptą"
                                    className="mb-4 p-2 border rounded"
                                />
                                <select
                                    name="category"
                                    value={recipeToEdit.category}
                                    onChange={(e) => setRecipeToEdit({...recipeToEdit, category: e.target.value })}
                                    className="block w-full mb-4 p-2 border rounded"
                                >
                                    <option value="">Pasirinkite kategoriją</option>
                                    <option value="Pusryčiai">Pusryčiai</option>
                                    <option value="Pietūs">Pietūs</option>
                                    <option value="Vakarienė">Vakarienė</option>
                                    <option value="Desertas">Desertas</option>
                                </select>
                            </div>
                            <div className="flex justify-between">
                                <button type="button" onClick={() => setIsEditModalOpen(false)} className="px-4 py-2 bg-gray-400 text-white rounded">Atšaukti</button>
                                <button type="submit" className="px-4 py-2 bg-green-500 text-white rounded">Išsaugoti</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {isAddFormOpen && (
                <div className="flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg text-center overflow-y-auto max-w-[50rem] flex flex-wrap justify-center">
                        <AddRecipeForm
                            onRecipeAdded={(newRecipe) => {
                                setRecipes([...recipes, newRecipe]);
                                setIsAddFormOpen(false);
                            }}
                        />
                        <button
                            onClick={() => setIsAddFormOpen(false)}
                            className="mt-4 bg-gray-400 text-white px-4 py-2 rounded"
                        >
                            Atšaukti
                        </button>
                    </div>
                </div>
            )}
        </main>
    );
}
