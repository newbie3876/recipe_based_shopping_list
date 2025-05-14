import { useState } from "react";

export default function AddRecipeForm({ onRecipeAdded }) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [portions, setPortions] = useState("");
    const [link, setLink] = useState("");
    const [category, setCategory] = useState("");


    const handleSubmit = (e) => {
        e.preventDefault();

        const newRecipe = {
            name,
            description,
            portions: parseInt(portions, 10), // būtinai skaičius!
            link,
            category,
        };

        fetch("http://localhost:8080/api/recipes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newRecipe),
        })
        .then((res) => {
            if (!res.ok) throw new Error("Nepavyko pridėti recepto");
            alert("Receptas pridėtas sėkmingai!");
            return res.json();
        })
        .then((data) => {
            onRecipeAdded(data); // callback, kad atnaujintų sąrašą
            setName("");
            setDescription("");
            setPortions("");
            setLink("");
            setCategory("");
        })
        .catch((err) => console.error("Klaida pridedant receptą:", err));
    };

    const handleChange = () => {
        setName("");
        setDescription("");
        setPortions("");
        setLink("");
        setCategory("");
    };

    return (
        <main className="p-[8rem] flex flex-col items-center bg-orange-200">
            <form onSubmit={handleSubmit} className="rounded shadow-md w-[30rem] bg-white flex flex-col items-center justify-evenly">
                <h2 className="text-xl font-bold mb-4">Pridėti naują receptą</h2>
                <input
                    type="text"
                    placeholder="Pavadinimas"
                    value={ name }
                    onChange={(e) => setName(e.target.value)}
                    className="block w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="text"
                    placeholder="Aprašymas"
                    value={ description }
                    onChange={(e) => setDescription(e.target.value)}
                    className="block w-full mb-2 p-2 border rounded"
                    required
                />
                <input
                    type="number"
                    placeholder="Porcijų skaičius"
                    value={ portions }
                    onChange={(e) => setPortions(e.target.value)}
                    className="block w-full mb-4 p-2 border rounded"
                    required
                    min="0"
                />
                <input
                    type="url"
                    placeholder="URL adresas"
                    value={ link }
                    onChange={(e) => setLink(e.target.value)}
                    className="block w-full mb-4 p-2 border rounded"
                />
                <select
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                    className="block w-full mb-4 p-2 border rounded"
                >
                    <option value="">Pasirinkite kategoriją</option>
                    <option value="Pusryčiai">Pusryčiai</option>
                    <option value="Pietūs">Pietūs</option>
                    <option value="Vakarienė">Vakarienė</option>
                    <option value="Desertas">Desertas</option>
                </select>
                <div className="flex gap-[1rem]">
                    <button type="submit" className="bg-green-500 text-white px-4 py-2 rounded">Pridėti</button>
                    <button type="button" className="bg-red-500 text-white px-4 py-2 rounded" onClick={handleChange}>Išvalyti</button>
                </div>
            </form>
        </main>
    );
}
