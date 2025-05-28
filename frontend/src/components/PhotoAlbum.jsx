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

  useEffect(() => {
    fetchImages();
  }, []);

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
    <div className="min-h-screen min-w-screen bg-gradient-to-t from-orange-100 via-transparent to-orange-300 pt-12">
      <div className="max-w-3xl mx-auto bg-orange-100 rounded-xl p-8 text-center">
        <div className="flex flex-col gap-2 items-center">
          <h2 className="text-3xl font-bold text-black mb-4">
            Pasidalinkite savo patiekalo nuotraukomis!
          </h2>
          <p className="text-lg text-black max-w-2xl mx-auto mb-5">
            Suteikite savo patiekalui pavadinimą ir įkelkite jo nuotrauką iš savo įrenginio:
          </p>

          <div className="text-sm text-gray-600 mr-48">
            Įveskite patiekalo pavadinimą:
          </div>
          <input
            type="text"
            placeholder="Patiekalo pavadinimas"
            value={imageName}
            onChange={handleNameChange}
            className=" border-2 border-orange-200 w-100 rounded p-2 mb-5 bg-white hover:border-orange-400"
          />
          <div className="flex flex-col items-center gap-4">
            <label className="bg-orange-300 w-50 p-2 rounded-md shadow-md max-w-xs cursor-pointer text-center block">
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
            className="bg-orange-300 p-2 rounded-md w-20 shadow-md max-w-xs"
          >
            Įkelti
          </button>
        </div>
      </div>

      <h3 className="text-2xl font-bold text-black text-center mt-10 ">
        Nuotraukų galerija
        <div className="flex justify-center mt-2">
        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none" stroke="#f97316" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" >
          <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
          <circle cx="12" cy="13" r="4" />
        </svg>
      </div>
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
