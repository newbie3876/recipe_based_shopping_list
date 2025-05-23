const API_URL = "http://localhost:8080/api/ingredients";

export const addIngredient = async (ingredientData) => {
  try {
    const token = localStorage.getItem("token"); // 📌 Pasiimame JWT tokeną
    if (!token) throw new Error("❌ Nepavyko gauti autentifikacijos tokeno!");

    const response = await fetch(API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(ingredientData),
    });

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || `Serverio klaida! Statusas: ${response.status}`);
    }

    return await response.json(); // ✅ Grąžiname serverio atsakymą (ResponseDTO)
  } catch (err) {
    throw new Error(`❌ Klaida pridedant ingredientą: ${err.message}`);
  }
};


export const fetchIngredients = async (userId) => {
  try {
    const token = localStorage.getItem("token"); // 🔹 Pasiimame JWT tokeną iš localStorage
    if (!token) throw new Error("❌ Nepavyko gauti autentifikacijos tokeno!");

    const response = await fetch(`${API_URL}/${userId}`, {
      method: "GET", // 🔹 Nurodome metodą
      headers: {
        Authorization: `Bearer ${token}`, // 🔹 Pridedame autentifikacijos header
        "Content-Type": "application/json", // 🔹 Nurodome turinio tipą
      },
      credentials: "include", // 🔹 Jei backend'as naudoja session cookies
    });

    if (!response.ok) throw new Error(`HTTP klaida! Statusas: ${response.status}`);

    return await response.json(); // 🔹 Parsisiunčiame JSON duomenis
  } catch (err) {
    throw new Error(`❌ Klaida gaunant pirkinių sąrašus: ${err.message}`);
  }
};

// const API_API_URL = "http://localhost:8080/api";

// export const fetchUnits = async () => {
//   try {
//     const token = localStorage.getItem("token");
//     if (!token) throw new Error("❌ Nepavyko gauti autentifikacijos tokeno!");

//     const response = await fetch(`${API_API_URL}/units`, {
//       method: "GET",
//       headers: {
//         Authorization: `Bearer ${token}`,
//         "Content-Type": "application/json",
//       },
//     });

//     if (!response.ok) throw new Error(`Serverio klaida: ${response.status}`);

//     return await response.json();
//   } catch (err) {
//     throw new Error(`❌ Klaida gaunant vienetus: ${err.message}`);
//   }
// };

// export const fetchCategories = async () => {
//   try {
//     const token = localStorage.getItem("token");
//     if (!token) throw new Error("❌ Nepavyko gauti autentifikacijos tokeno!");

//     const response = await fetch(`${API_API_URL}/categories`, {
//       method: "GET",
//       headers: {
//         Authorization: `Bearer ${token}`,
//         "Content-Type": "application/json",
//       },
//     });

//     if (!response.ok) throw new Error(`Serverio klaida: ${response.status}`);

//     return await response.json();
//   } catch (err) {
//     throw new Error(`❌ Klaida gaunant kategorijas: ${err.message}`);
//   }
// };

