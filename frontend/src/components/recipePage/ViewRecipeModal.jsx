export default function ViewRecipeModal({ recipe, ingredients, onClose }) {
    const ingredientCategoryMap = {
    1: "Pienas ir jo gaminiai",
    2: "Mėsa, žuvis ir kiaušiniai",
    3: "Bulvės, ankštiniai augalai ir riešutai",
    4: "Daržovės",
    5: "Vaisiai",
    6: "Duona, makaronai, grūdai, cukrus ir saldainiai",
    7: "Riebalai, aliejus ir sviestas"
    };

    const unitMap = {
        1: "g",
        2: "kg",
        3: "l",
        4: "ml",
        5: "pc."
    };


    if (!recipe) return null;
    return (
        <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center z-50">
            <div className="bg-white rounded p-6 w-[90%] max-w-lg relative">
                <h2 className="text-xl font-bold mb-2 text-center">{recipe.name || "nepriskirta"}</h2>
                <p><strong>Aprašymas:</strong> {recipe.description || "nepriskirta"}</p>
                <p><strong>Porcijos:</strong> {recipe.portions || "nepriskirta"}</p>
                <p><strong>Nuoroda:</strong> {recipe.link || "nepriskirta"}</p>
                <p><strong>Kategorija:</strong> {recipe.categoryName || "nepriskirta"}</p>

                <h3 className="mt-4 text-lg font-semibold">Ingredientai:</h3>
                {ingredients && ingredients.length > 0 ? (
                    <ul className="list-disc list-inside">
                        {ingredients.map((ing) => (
                            <li key={ing.id}>
                                {ing.name}, kiekis: {ing.quantity ?? "nepriskirta"}, matavimo vienetai: {unitMap[parseInt(ing.unitId, 10)] || "nepriskirta"}, kategorija: {ingredientCategoryMap[parseInt(ing.ingredientCategoryId, 10)] || "nepriskirta"}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Nėra ingredientų.</p>
                )}

                <button
                    onClick={onClose}
                    className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600"
                >X</button>
            </div>
        </div>
    );
}
