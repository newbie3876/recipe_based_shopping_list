import React, { useState, useEffect, useRef } from "react";
import PhotoModal from "./PhotoModal";

function PhotoAlbum() {
  const [file, setFile] = useState(null);
  const [images, setImages] = useState([]);
  const [imageName, setImageName] = useState("");
  const [isPhotoModalOpen, setIsFhotoModalOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState(null);
  const [fileName, setFileName] = useState("");

  const fileInputRef = useRef(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
    const file = event.target.files[0];
    if (file) {
      setFileName(file.name);
    }
  };

  const handleNameChange = (event) => {
    setImageName(event.target.value);
  };

  const uploadImage = () => {
    if (!imageName.trim()) {
      alert("Pavadinimas yra būtinas!");
      return; // sustabdom įkėlimą
    }

    if (!file) {
      alert("Pasirinkite failą!");

      return;
    }

    const reader = new FileReader();

    reader.onloadend = async () => {
      const base64Data = reader.result.split(",")[1];

      const payload = {
        imageName: imageName,
        contentType: file.type,
        imageData: base64Data,
      };

      try {
        const response = await fetch("/api/images", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          body: JSON.stringify(payload),
        });

        if (response.ok) {
          const result = await response.json();
          alert("Foto: " + result.imageName + " sėkmingai išsaugotas.");
          fetchImages();
          setImageName("");
          setFile(null);
          setFileName("");
          fileInputRef.current.value = "";
        } else {
          const errorData = await response.json();
          console.error("Klaida:", errorData);
          alert("Įkėlimas nepavyko.");
        }
      } catch (err) {
        console.error("Tinklo klaida:", err);
        alert("Serverio klaida.");
      }
    };

    reader.readAsDataURL(file);
  };

  const fetchImages = async () => {
    try {
      const response = await fetch("/api/images", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setImages(data);
      } else {
        console.error("Nepavyko gauti paveikslėlių");
      }
    } catch (error) {
      console.error("Klaida gaunant paveikslėlius:", error);
    }
  };

  const deleteImage = async (id) => {
    const isConfirmed = window.confirm(
      "Ar tikrai norite pašalinti šį paveikslėlį?"
    );

    if (isConfirmed) {
      try {
        const response = await fetch(`/api/images/${id}`, {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });

        if (response.ok) {
          fetchImages(); // Po pašalinimo – atnaujiname sąrašą
        } else {
          console.error("Nepavyko pašalinti paveikslėlio");
          alert("Nepavyko pašalinti paveikslėlio.");
        }
      } catch (error) {
        console.error("Klaida pašalinant paveikslėlį:", error);
        alert("Serverio klaida.");
      }
    }
  };

  // Naudojame useEffect norėdami užkrauti paveikslėlius iš karto, kai komponentas užkraunamas
  useEffect(() => {
    fetchImages();
  }, []);

  const openModal = (image) => {
    setSelectedImage(image);
    setIsFhotoModalOpen(true);
  };

  return (
    <div className=" bg-orange-200 min-h-screen">
      <div className="flex flex-col gap-2 items-center">
        <h2 className="text-xl font-bold text-center mt-4">
          Paveikslėlio įkėlimas
        </h2>
        <input
          type="text"
          placeholder="Paveikslėlio pavadinimas"
          value={imageName}
          className=" border rounded p-2 mt-1"
          onChange={handleNameChange}
        />
        <div className="flex flex-col items-center gap-4">
          <label className="bg-orange-100 p-2 rounded-xl shadow-md max-w-xs cursor-pointer text-center block">
            Pasirinkti nuotrauką
            <input
              type="file"
              accept="image/*"
              onChange={handleFileChange}
              className="hidden"
              ref={fileInputRef}
            />
          </label>

          {fileName && (
            <p className="text-sm text-gray-700">
              Pasirinktas failas: <strong>{fileName}</strong>
            </p>
          )}
        </div>

        <button
          onClick={uploadImage}
          className="bg-orange-100 p-2 rounded-xl shadow-md max-w-xs"
        >
          Įkelti
        </button>
      </div>
      <h3 className="text-xl font-bold text-center mt-20 ">
        Paveikslėlių galerija
      </h3>
      <div className="flex flex-wrap gap-5 m-5 ">
        {images.map((image) => (
          <div key={image.id}>
            <button
              className="cursor-pointer border border-gray-300 rounded-xl p-2 shadow-md focus:outline-none focus:ring-2 focus:ring-orange-300 transition duration-200"
              onClick={() => openModal(image)}
            >
              <img
                src={`data:${image.contentType};base64,${image.imageData}`}
                alt={image.imageName}
                className="w-32 h-32 object-cover"
              />
            </button>
            <p>{image.imageName}</p>
            <button
              onClick={() => deleteImage(image.id)}
              className="bg-orange-500 text-white p-0.5 mt-0.5 rounded-md hover:cursor-pointer hover:bg-orange-600"
            >
              Pašalinti
            </button>
          </div>
        ))}
      </div>
      <div className=" flex justify-center items-center">
        <PhotoModal
          isOpen={isPhotoModalOpen}
          onClose={() => setIsFhotoModalOpen(false)}
          image={selectedImage}
        />
      </div>
    </div>
  );
}

export default PhotoAlbum;
