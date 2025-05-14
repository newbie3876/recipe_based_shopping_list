import React from "react";
import modal_close_icon from "../assets/modalCloseIcon.webp";

function PhotoModal({ isOpen, onClose, image }) {
  if (!isOpen || !image) return null;

  // Funkcija, kuri uždaro modalą uz jo ribu
  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div
      className=" fixed inset-0  bg-opacity-50 flex justify-center items-center"
      onClick={handleOverlayClick}
    >
      <div className="relative bg-orange-100 p-5 rounded-md">
        <button
          onClick={() => onClose()}
          className="absolute top-2 right-2 cursor-pointer hover:opacity-50"
        >
          <img src={modal_close_icon} className="w-6" alt="close icon" />
        </button>
        <div>
          <img
            src={`data:${image.contentType};base64,${image.imageData}`}
            alt={image.imageName}
            className="h-96 w-mx-full"
          />
          <p className="flex justify-center mt-2">{image.imageName}</p>
        </div>
      </div>
    </div>
  );
}

export default PhotoModal;
