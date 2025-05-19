import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

// Gauna pirkinių sąrašus pagal userId
export const fetch = async (userId) => {
  localStorage.setItem("jwtToken", token);  
  const token = localStorage.getItem("jwtToken"); //  Gauna tokeną iš localStorage
  

  if (!token) {
  console.error("❌ Vartotojas neprisijungęs. Tokenas neegzistuoja.");
  return [];
  }

  try {
    const response = await axios.get(`${API_BASE_URL}/shoppinglists/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      withCredentials: true, // Jei backend naudoja `HttpOnly` cookies
    });
    return response.data;
  } catch (error) {
    console.error("❌ Klaida gaunant pirkinių sąrašus:", error.response?.data || error.message);
    throw error;
  }
};


// try {
//     const response = await fetch(`${API_BASE_URL}/shoppinglists/${userId}`, {
//       headers: {
//         Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
//       },
//       credentials: "include",
//     });

//     if (!response.ok) throw new Error(`HTTP klaida! Statusas: ${response.status}`);

//     return await response.json();
//   } catch (err) {
//     throw new Error(`❌ Klaida gaunant pirkinių sąrašus: ${err.message}`);
//   }
