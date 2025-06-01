import React from "react";
import AdminRegistrationModal from "./AdminRegistrationModal";
import { useState } from "react";
import UsersInfoForAdmin from "./UsersInfoForAdmin";

function Admin() {
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  return (
    <div className="bg-orange-200 h-full">
      <div className="flex items-center justify-center pt-10 ">
        <button
          onClick={() => setIsRegisterOpen(true)}
          className=" bg-blue-400 hover:bg-blue-600 cursor-pointer text-white font-bold py-2 px-4 rounded"
        >
          Naujo administratoriaus registracija
        </button>
      </div>

      <AdminRegistrationModal
        isOpen={isRegisterOpen}
        onClose={() => setIsRegisterOpen(false)}
      />
      <UsersInfoForAdmin />
    </div>
  );
}

export default Admin;
