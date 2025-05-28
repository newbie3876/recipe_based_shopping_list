const API_URL = "http://localhost:8080/api/shoppinglists";

export const fetchShoppingLists = async (userId) => {
  try {
    // Pasiimame token'ą iš localStorage
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("❌ Nepavyko gauti autentifikacijos tokeno! Prašome prisijungti.");
    }

    if (!userId) {
      throw new Error("❌ Nepateiktas vartotojo ID!");
    }

    // Debug: Logins tokeną (nebent nenori viešai rodyti)
    console.log("Sending token:", token);

    const response = await fetch(`${API_URL}/${userId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,  // Tokenas Authorization header'yje
        "Content-Type": "application/json",
      },
      // Jei backend'as naudoja cookies autentifikacijai, paliekam, kitu atveju galima pašalinti
      credentials: "include",
    });

    // Debug: Logins HTTP statusą
    console.log("Response status:", response.status);

    if (!response.ok) {
      if (response.status === 401) {
        // Specialus pranešimas 401 klaidai
        throw new Error("Unauthorized: Netinkamas arba pasibaigęs tokenas. Prašome prisijungti iš naujo.");
      }
      throw new Error(`HTTP klaida! Statusas: ${response.status}`);
    }

    const data = await response.json();
    return data;

  } catch (err) {
    // Loginam klaidą konsolėje detaliai
    console.error("fetchShoppingLists klaida:", err);
    throw new Error(`❌ Klaida gaunant pirkinių sąrašus: ${err.message}`);
  }
};


// export const createShoppingList = async (ingredientData) => {
//   try {
//     const token = localStorage.getItem("token"); // 📌 Pasiimame JWT tokeną
//     if (!token) throw new Error("❌ Nepavyko gauti autentifikacijos tokeno!");

//     const response = await fetch(API_URL, {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//         Authorization: `Bearer ${token}`,
//       },
//       body: JSON.stringify(ingredientData),
//     });

//     if (!response.ok) {
//       const errorData = await response.json();
//       throw new Error(errorData.message || `Serverio klaida! Statusas: ${response.status}`);
//     }

//     return await response.json(); // ✅ Grąžiname serverio atsakymą (ResponseDTO)
//   } catch (err) {
//     throw new Error(`❌ Klaida pridedant ingredientą: ${err.message}`);
//   }
// };

export async function createShoppingList(data) {
    try {
        const token = localStorage.getItem(token);
        
        const response = await fetch('/api/shoppinglists', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Serverio klaida: ${response.status} - ${errorText}`);
        }

        return await response.json();
    } catch (error) {
        console.error("createShoppingList klaida:", error);
        throw error;
    }
}