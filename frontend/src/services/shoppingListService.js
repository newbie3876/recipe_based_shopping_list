const API_URL = "http://localhost:8080/api/shoppinglists";

export const fetchShoppingLists = async (userId) => {
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