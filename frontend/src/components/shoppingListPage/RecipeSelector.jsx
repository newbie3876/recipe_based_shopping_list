import { useState, useEffect } from "react";

export default function RecipeSelector({ onRecipeSelected }) {
    const [recipes, setRecipes] = useState([]);
    const [selectedIds, setSelectedIds] = useState([]);
    const [loading, setLoading] = useState(false);

   useEffect(() => {
        const token = localStorage.getItem("token");
        if (!token) {
            console.error("Tokenas nerastas.");
            return;
        }

        setLoading(true);
        fetch("http://localhost:8080/api/recipes", {
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Serverio klaida: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                setRecipes(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Nepavyko gauti receptų", err);
                setLoading(false);
            });
    }, []);

    const handleSelectionChange = (e) => {
        const selectedOptions = Array.from(e.target.selectedOptions);
        const ids = selectedOptions.map(opt => parseInt(opt.value));
        setSelectedIds(ids);
    };

    const handleConfirm = () => {
        if (selectedIds.length === 0) {
            alert("Pasirinkite bent vieną receptą.");
            return;
        }
        onRecipeSelected(selectedIds);
        setSelectedIds([]); // pasirenkamų reikšmių išvalymas
    };

    return (
        <div className="my-4 flex flex-col items-center">
            <label className="block font-bold mb-2">Pasirinkite receptus:</label>
            <select
                multiple
                className="p-2 rounded w-64 h-32"
                value={selectedIds.map(String)}
                onChange={handleSelectionChange}
            >
                {recipes.map(recipe => (
                    <option key={recipe.id} value={recipe.id}>
                        {recipe.name}
                    </option>
                ))}
            </select>
            <p className="text-sm text-gray-600 mt-2">Laikyk Ctrl (arba Cmd), kad pasirinktum kelis.</p>

            <button
                onClick={handleConfirm}
                className="mt-4 px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded"
            >
                Sukurti pirkinių krepšelį
            </button>
        </div>
    );
}
