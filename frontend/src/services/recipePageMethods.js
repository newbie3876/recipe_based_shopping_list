const API_BASE = "http://localhost:8080";

function getToken() {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Autentifikacijos žetonas nerastas.");
    return token;
}

export async function fetchRecipes() {
    const res = await fetch(`${API_BASE}/api/recipes`, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko gauti receptų.");
    return await res.json();
}

export async function createRecipe(recipeData) {
    const res = await fetch(`${API_BASE}/api/recipes`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify(recipeData)
    });
    if (!res.ok) throw new Error("Nepavyko sukurti recepto.");
    return await res.json();
}

export async function updateRecipe(id, updatedData) {
    const res = await fetch(`${API_BASE}/api/recipes/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify(updatedData)
    });
    if (!res.ok) throw new Error("Nepavyko atnaujinti recepto.");
    return await res.json();
}

export async function deleteRecipeById(id) {
    const res = await fetch(`${API_BASE}/api/recipes/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko ištrinti recepto.");
    return true;
}

export async function addIngredientToRecipe({ recipeId, name, quantity, unitId, ingredientCategoryId }) {
    const response = await fetch(`${API_BASE}/api/ingredients`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`,
        },
        body: JSON.stringify({ recipeId, name, quantity, unitId, ingredientCategoryId }),
    });

    if (!response.ok) {
        throw new Error("Nepavyko pridėti ingrediento");
    }
    return await response.json();
}

