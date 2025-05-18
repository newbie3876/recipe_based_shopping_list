package lt.techin.dto.recipe;

public record RecipeResponseDTO(Long id,
                                String name,
                                String description,
                                int portions,
                                String link,
                                String categoryName) {
}
