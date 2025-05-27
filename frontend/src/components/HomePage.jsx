import React from "react";
import soup4 from "../images/soup4.jpg";

function HomePage() {
  return (
    <div className="relative" style={{ height: 'calc(100vh - 92px)' }}>
      <div className="absolute inset-0 z-0">
        <img src={soup4} alt="background with a plate full of soup" className="w-full h-full object-cover" />
      </div>

      <div className="relative z-10 w-full h-full p-12">
        <div className="max-w-4xl mx-auto bg-orange-100 rounded-xl p-8 text-center mb-20 opacity-95">
          <h2 className="text-4xl font-bold text-black mb-4">Receptais pagrįstas pirkinių krepšelis</h2>
          <p className="text-lg text-black max-w-2xl mx-auto">
            Paverskite savo mėgstamiausius receptus tvarkingais pirkinių sąrašais. Pasirinkite receptą, sužymėkite ingredientus ir gaminkite patogiau.
          </p>
        </div>
        
        <div className="max-w-4xl mx-auto bg-orange-100 rounded-xl p-8 text-center opacity-95">
          <h2 className="text-4xl font-bold text-black mb-4">Kodėl receptais pagrįstas pirkinių krepšelis?</h2>
          <p className="text-lg text-black max-w-2xl mx-auto mb-10">
            Šis tinklalapis padės Jums supaprastinti maisto gaminimo rutiną, naudojant išmanius, receptais pagrįstus pirkinių sąrašus. Nuo šiol niekada nepamiršite nė vieno ingrediento!
          </p>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-white p-5 rounded-xl border-l-4 border-orange-300">
              <div className="bg-orange-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-6 h-6 text-emerald-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7"></path>
                </svg>
              </div>
              <h3 className="font-bold text-xl text-black mb-3">Lengva naudoti</h3>
              <p className="text-gray-800">
                Pridėkite receptus ir sukurkite sąrašus vos keliais paspaudimais.
              </p>
            </div>
            
            <div className="bg-white p-5 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 border-l-4 border-orange-300">
              <div className="bg-orange-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                </svg>
              </div>
              <h3 className="font-bold text-xl text-black mb-3">Būkite organizuoti</h3>
              <p className="text-gray-800">
                Apsipirkdami pažymėkite prekes ir viską laikykite vienoje vietoje.
              </p>
            </div>
            
            <div className="bg-white p-5 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 border-l-4 border-orange-300">
              <div className="bg-orange-100 w-12 h-12 rounded-full flex items-center justify-center mx-auto mb-4">
                <svg className="w-6 h-6 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
              <h3 className="font-bold text-xl text-black mb-3">Sutaupykite laiko</h3>
              <p className="text-gray-800">
                Sutelkite dėmesį į maisto gaminimą, o ne į planavimą.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomePage;
