export default function RecipeList({ recipes, onEdit, onDelete }) {
    if (!recipes || recipes.length === 0) {
        return (
            <div className="text-center text-gray-500 mt-8">
                Nėra pridėtų receptų.
            </div>
        );
    }

    return (
        <div className="flex flex-wrap gap-[1rem] justify-center">
            {recipes.map((recipe) => (
                <div
                    key={recipe.id}
                    className="bg-white p-4 rounded shadow flex flex-col items-center justify-between"
                >
                    <h2 className="text-lg font-semibold">{recipe.name}</h2>
                    <p>Aprašymas: {recipe.description}</p>
                    <p>Porcijų skaičius: {recipe.portions}</p>
                    <p>Nuoroda: {recipe.link || "nepriskirta"}</p>

                    <div className="flex gap-[8px]">
                        <p>Kategorija: {recipe.categoryName}</p>
                    </div>

                    <div className="flex justify-end mt-4 space-x-2">
                        <button
                            onClick={() => onEdit(recipe)}
                            className="px-3 py-1 text-sm bg-yellow-400 text-white rounded hover:bg-yellow-500"
                        >
                            Redaguoti
                        </button>
                        <button
                            onClick={() => onDelete(recipe)}
                            className="px-3 py-1 text-sm bg-red-500 text-white rounded hover:bg-red-600"
                        >
                            Ištrinti
                        </button>
                    </div>
                </div>
            ))}
        </div>
    );
}
