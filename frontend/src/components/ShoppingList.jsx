export default function ShoppingList(){
     return (
    <div className="min-h-screen w-full bg-orange-50 flex flex-col">
      <div className="bg-orange-200 p-4">
        <h1 className="text-2xl font-bold text-center">Pirkinių krepšelis</h1>
      </div>
      
      <div className="flex-grow p-4 max-w-6xl mx-auto w-full">
        <div className="bg-orange-100 p-4 rounded-lg border border-orange-200 shadow-md h-full">
          <div className="mb-6">
            <div className="text-xl font-semibold mb-4 text-center">Ingredientai</div>
            
            <div className="overflow-x-auto">
              <div className="grid grid-cols-6 w-full border border-orange-300">
                <div className="bg-orange-200 p-2 font-medium">#</div>
                <div className="bg-orange-200 p-2 font-medium">Ingredientai</div>
                <div className="bg-orange-200 p-2 font-medium">Kiekis</div>
                <div className="bg-orange-200 p-2 font-medium">Vienetas</div>
                <div className="bg-orange-200 p-2 font-medium">Kategorija</div>
                <div className="bg-orange-200 p-2 font-medium">Įsigytas</div>
              </div>
              
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-200 p-2 font-medium border border-orange-300 text-center">
                  Receptų ingredientai
                </div>
              </div>
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-100 p-3 text-center border border-orange-300 text-orange-800">
                  Dar nepridėjote jokių receptų ingredientų
                </div>
              </div>
              
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-200 p-2 font-medium border border-orange-300 text-center">
                  Kiti ingredientai
                </div>
              </div>
              <div className="grid grid-cols-1 w-full">
                <div className="bg-orange-100 p-3 text-center border border-orange-300 text-orange-800">
                  Dar nepridėjote jokių ingredientų
                </div>
              </div>
            </div>
          </div>
          
          <div className="flex justify-center gap-4 mt-8">
            <button className="bg-orange-200 hover:bg-orange-300 px-4 py-2 rounded text-orange-900 border border-orange-300">
              Ištrinti
            </button>
            <button className="bg-green-500 hover:bg-green-700 px-4 py-2 rounded text-white border border-green-600">
              Grįžti atgal
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}