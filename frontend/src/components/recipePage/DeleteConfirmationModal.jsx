export default function DeleteConfirmationModal({ recipe, onCancel, onConfirm }) {
    if (!recipe) return null;

    return (
        <div className="fixed inset-0 bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-lg w-1/3 text-center">
                <h3 className="text-xl font-bold mb-4">Ar tikrai norite ištrinti receptą „{recipe.name}“?</h3>
                <div className="flex justify-between">
                    <button onClick={onCancel} className="px-4 py-2 bg-gray-400 hover:bg-gray-500 text-white rounded">Atšaukti</button>
                    <button onClick={onConfirm} className="px-4 py-2 bg-red-500 hover:bg-red-600 text-white rounded">Ištrinti</button>
                </div>
            </div>
        </div>
    );
}
