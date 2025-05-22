const API_URL = "http://localhost:8080/api/shoppinglists";

function getToken() {
    const token = localStorage.getItem("token");
    if (!token) throw new Error("Autentifikacijos žetonas nerastas.");
    return token;
}

export async function fetchShoppingLists() {
    const res = await fetch(API_URL, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko gauti receptų krepšelių.");
    return await res.json();
}

export async function createShoppingList(shoppingListData) {
    const res = await fetch(API_URL, {
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
    const res = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });
    if (!res.ok) throw new Error("Nepavyko ištrinti receptų krepšelio.");
    return true;
}