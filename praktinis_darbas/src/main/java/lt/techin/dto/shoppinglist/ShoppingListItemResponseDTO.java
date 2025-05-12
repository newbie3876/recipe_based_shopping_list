package lt.techin.dto.shoppinglist;

import jakarta.validation.constraints.NotNull;
import lt.techin.model.Ingredient;
import lt.techin.model.ShoppingList;
import lt.techin.model.Unit;

import java.util.List;

public record ShoppingListItemResponseDTO(
        Long id,
        List<Long> listsIds,
        List<Long> ingredients,
        int quantity,
        List<Long> units
) {
}
