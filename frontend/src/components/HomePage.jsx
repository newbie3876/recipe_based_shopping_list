import { useNavigate } from "react-router-dom";

export default function Homepage() {
  const navigate = useNavigate();

  return (
    <div className="bg-orange-200 h-screen">
      <div style={{ display: "flex", gap: "20px", flexWrap: "wrap", justifyContent: "center" }}>
        <div style={{ border: "1px solid gray", padding: "15px", boxShadow: "3px 3px 12px rgba(0, 0, 0, 0.3)", borderRadius: "8px", textAlign: "center" }}>
          <button 
            onClick={() => navigate("/all-ingredients")}
            style={{
              padding: "10px",
              borderRadius: "5px",
              backgroundColor: "#4CAF50",
              color: "white",
              border: "none",
              cursor: "pointer",
              boxShadow: "2px 2px 8px rgba(0, 0, 0, 0.2)"
            }}
          >
            Rodyti visus ingredientus
          </button>
        </div>
      </div>
    </div>
  );
}
