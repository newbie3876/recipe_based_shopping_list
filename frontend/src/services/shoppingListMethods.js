const API_BASE = "http://localhost:8080";

function getToken() {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Autentifikacijos žetonas nerastas.");
    return token;
}

export async function fetchShoppingLists() {
    const res = await fetch(`${API_BASE}/api/shoppinglists`, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko gauti receptų krepšelių.");
    return await res.json();
}

export async function createShoppingList(shoppingListData) {
    const res = await fetch(`${API_BASE}/api/shoppinglists`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify(shoppingListData)
    });
    if (!res.ok) throw new Error("Nepavyko sukurti receptų krepšelio.");
    return await res.json();
}

export async function deleteShoppingListById(id) {
    const numericId = Number(id);
    if (isNaN(numericId)) throw new Error("Neteisingas krepšelio ID.");
    
    const res = await fetch(`${API_BASE}/api/shoppinglists/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko ištrinti receptų krepšelio.");
    return true;
}

export async function createShoppingListFromRecipe(recipeId) {
    const res = await fetch(`${API_BASE}/api/shoppinglists/from-recipe/${recipeId}`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko sukurti krepšelio iš recepto.");
    return await res.json();
}
