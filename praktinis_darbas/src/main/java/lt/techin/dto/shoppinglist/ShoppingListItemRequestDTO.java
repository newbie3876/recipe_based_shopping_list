package lt.techin.dto.shoppinglist;

import jakarta.validation.constraints.NotNull;
import lt.techin.model.Ingredient;
import lt.techin.model.ShoppingList;
import lt.techin.model.Unit;

import java.util.List;

public record ShoppingListItemRequestDTO(
        @NotNull
        List<ShoppingList> shoppingLists,

        @NotNull
        List<Ingredient> ingredients,

        @NotNull
        int quantity,

        @NotNull
        List<Unit> units
) {
}
