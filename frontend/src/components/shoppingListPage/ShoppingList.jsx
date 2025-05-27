export default function ShoppingList({ shoppingLists, onDelete }) {
  if (!shoppingLists || shoppingLists.length === 0) {
    return (
      <div className="text-center text-gray-500 mt-8">
        Nėra pridėtų pirkinių krepšelių.
      </div>
    );
  }

  return (
    <div className="flex flex-wrap gap-[1rem] justify-center">
      {shoppingLists.map((shoppingList) => (
        <div
          key={shoppingList.id}
          className="bg-white p-4 rounded shadow flex flex-col items-center justify-between"
        >
          <h2 className="text-lg font-semibold">{shoppingList.name}</h2>
          <p>Sukūrimo data: {new Date(shoppingList.createdAt).toLocaleString()}</p>

          <div className="flex justify-end mt-4 space-x-2">
            <button
              onClick={() => {
                if (!shoppingList.id) {
                  console.error("Neteisingas arba neegzistuojantis krepšelio ID:", shoppingList);
                  return;
                }
                onDelete(shoppingList);
              }}
              className="px-3 py-1 text-sm bg-red-500 text-white rounded hover:bg-red-600"
            >
              Ištrinti
            </button>
          </div>
        </div>
      ))}
    </div>
  );
}
