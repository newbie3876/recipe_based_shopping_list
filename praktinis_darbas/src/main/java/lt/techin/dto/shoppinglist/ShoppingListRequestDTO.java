package lt.techin.dto.shoppinglist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lt.techin.model.User;

import java.time.LocalDate;

public record ShoppingListRequestDTO(
        @NotNull
        @Size(min = 2, max = 100)
        User user,

        @NotNull
        @PastOrPresent(message = "Creation date cannot be in the future!")
        LocalDate createdAt
) {
}
