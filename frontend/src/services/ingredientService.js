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


export const fetchIngredients = async () => {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      throw new Error("❌ Nepavyko gauti autentifikacijos tokeno!");
    }

    const response = await fetch(API_URL, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP klaida! Statusas: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (err) {
    throw new Error(`❌ Klaida gaunant ingredientus: ${err.message}`);
  }
};