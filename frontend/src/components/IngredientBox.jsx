import { useState } from "react";

function IngredientBox({ title, buttonText, showList, handleClick }) {
  const [ingredients, setIngredients] = useState([]);
  const [isVisible, setIsVisible] = useState(false);

  const handleAddIngredient = () => {
    const newIngredient = prompt("Įveskite naują ingredientą:");
    if (newIngredient) {
      setIngredients([...ingredients, newIngredient]);
    }
  };

  return (
    <div style={{
      border: "1px solid gray",
      padding: "15px",
      margin: "10px",
      borderRadius: "8px",
      boxShadow: "3px 3px 12px rgba(0, 0, 0, 0.3)",
      backgroundColor: "#ffffff",
      maxWidth: "260px",
      textAlign: "center"
    }}>
      <h3>{title}</h3>
      <button 
        onClick={() => {
          if (showList) {
            setIsVisible(!isVisible);
          } else {
            handleAddIngredient();
          }
        }}
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
        {buttonText}
      </button>

      {isVisible && showList && (
        <ul style={{ textAlign: "left", marginTop: "10px" }}>
          {ingredients.map((ingredient, index) => (
            <li key={index}>{ingredient}</li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default IngredientBox;