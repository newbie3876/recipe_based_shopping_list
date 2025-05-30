import React from "react";
import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";

function UsersInfoForAdmin() {
  const [users, setUsers] = useState([]);

  const { user } = useAuth();

  // Čia deklaruojam isAdmin
  const isAdmin =
    user?.roles?.some((role) => role?.name === "ROLE_ADMIN") || false;

  const fetchUsers = async () => {
    try {
      const response = await fetch("/api/users", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setUsers(data);
      } else {
        console.error("Nepavyko gauti vartotojų");
      }
    } catch (error) {
      console.error("Klaida gaunant vartotojus:", error);
    }
  };

  const deleteUser = async (id) => {
    const isConfirmed = window.confirm(
      "Ar tikrai norite pašalinti šį vartotoją?"
    );

    if (isConfirmed) {
      try {
        const response = await fetch(`/api/users/${id}`, {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });

        if (response.ok) {
          fetchUsers(); // Po pašalinimo – atnaujiname sąrašą
        } else {
          console.error("Nepavyko pašalinti vartotojo");
          alert("Nepavyko pašalinti vartotojo.");
        }
      } catch (error) {
        console.error("Klaida pašalinant vartotojo:", error);
        alert("Serverio klaida.");
      }
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Vartotojų sąrašas</h2>
      {users.length === 0 ? (
        <p>Nėra vartotojų duomenų.</p>
      ) : (
        <ul className="space-y-2">
          {users.map((userItem) => {
            const isUserAdmin = userItem.roles?.some(
              (role) => role?.name === "ROLE_ADMIN"
            );

            return (
              <li
                key={userItem.id}
                className="border p-2 rounded bg-orange-300 shadow-sm flex justify-between items-center"
              >
                <div>
                  <p>
                    <strong>ID:</strong> {userItem.id}
                  </p>
                  <p>
                    <strong>Vardas:</strong> {userItem.username}
                  </p>
                  <p>
                    <strong>Rolė:</strong>{" "}
                    {isUserAdmin ? "ADMIN" : "Vartotojas"}
                  </p>
                </div>

                {isAdmin && (
                  <button
                    onClick={() => deleteUser(userItem.id)}
                    className="bg-red-600 hover:bg-red-700 text-white py-1 px-3 rounded"
                  >
                    Ištrinti
                  </button>
                )}
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}

export default UsersInfoForAdmin;
