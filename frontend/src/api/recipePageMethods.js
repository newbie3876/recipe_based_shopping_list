const API_URL = "http://localhost:8080/api/recipes";

function getToken() {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Autentifikacijos žetonas nerastas.");
    return token;
}

export async function fetchRecipes() {
    const res = await fetch(API_URL, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko gauti receptų.");
    return await res.json();
}

export async function createRecipe(recipeData) {
    const res = await fetch(API_URL, {
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
    const res = await fetch(`${API_URL}/${id}`, {
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
    const res = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko ištrinti recepto.");
    return true;
}

export async function addIngredientToRecipe({ recipeId, name, quantity }) {
    const response = await fetch(`/api/recipes/${recipeId}/ingredients`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ name, quantity }),
    });

    if (!response.ok) {
        throw new Error("Nepavyko pridėti ingrediento");
    }
    return await response.json();
}

