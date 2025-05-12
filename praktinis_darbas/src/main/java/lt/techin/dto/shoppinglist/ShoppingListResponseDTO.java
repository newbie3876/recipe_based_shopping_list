package lt.techin.dto.shoppinglist;

import java.time.LocalDate;
import java.util.List;

public record ShoppingListResponseDTO(
        Long id,
        List<Long> userIds,
        String username,
        LocalDate createdAt
) {
}
