import { useNavigate } from "react-router-dom";
import IngredientBox from "./IngredientBox";

export default function Homepage() {
  const navigate = useNavigate();

  return (
    <div style={{ display: "flex", gap: "20px", flexWrap: "wrap", justifyContent: "center" }}>
      <IngredientBox buttonText="Rodyti visus ingredientus" showList={true} />
      
      <div style={{ border: "1px solid gray", padding: "15px", boxShadow: "3px 3px 12px rgba(0, 0, 0, 0.3)", borderRadius: "8px", textAlign: "center" }}>
        <button 
          onClick={() => navigate("/add-ingredient")}
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
          Pridėti naują ingredientą
        </button>
      </div>
    </div>
  );
}
