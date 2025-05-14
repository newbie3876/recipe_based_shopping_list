package lt.techin.controller;

//
//@RestController
//@RequestMapping("/api/shopping-lists/")
//@CrossOrigin(origins = "http://localhost:5173")
//public class ShoppingListItemController {
//
//  private final ShoppingListItemService shoppingListItemService;
//  private final UserService userService;
//  private final RecipeIngredientRepository recipeIngredientRepository;
//  private final UnitRepository unitRepository;
//
//  public ShoppingListItemController(ShoppingListItemService shoppingListItemService,
//                                    UserService userService,
//                                    RecipeIngredientRepository recipeIngredientRepository,
//                                    UnitRepository unitRepository) {
//    this.shoppingListItemService = shoppingListItemService;
//    this.userService = userService;
//    this.recipeIngredientRepository = recipeIngredientRepository;
//    this.unitRepository = unitRepository;
//  }
//
//  @Autowired
//  @PostMapping("/items")
//  public ResponseEntity<Object> addItemToShoppingList(@Valid @RequestBody ShoppingListItem shoppingListItem) {
//    ShoppingListItem addedItem = this.shoppingListItemService.addItem(shoppingListItem);
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(addedItem.getId())
//                            .toUri())
//            .body(addedItem);
//  }
//
//  @PutMapping("/items/{id}")
//  public ResponseEntity<Object> updateItemQuantity(@PathVariable long id, @Valid @RequestBody ShoppingListItem shoppingListItem) {
//    Optional<ShoppingListItem> itemFromList = this.shoppingListItemService.findItemById(id);
//
/// /    if (itemFromList.isPresent()) {
/// /      ShoppingListItem updatedItem = itemFromList.get();
/// /
/// /      updatedItem.setQuantity(ShoppingListItemRequestDTO.quantity());
/// /
/// /      return ResponseEntity.ok(ShoppingListItemMapper.toDTO(i));
/// /    }
//
//    ShoppingListItem addedItem = this.shoppingListItemService.addItem(shoppingListItem);
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .replacePath("/api/movies/{id}")
//                            .buildAndExpand(addedItem.getId())
//                            .toUri())
//            .body(addedItem);
//  }
//
//  @DeleteMapping("/items/{id}")
//  public ResponseEntity<Void> removeItemFromShoppingList(@PathVariable long id) {
//    Optional<ShoppingListItem> item = this.shoppingListItemService.findItemById(id);
//
//    if (item.isEmpty()) {
//      return ResponseEntity.notFound().build();
//    }
//
//    this.shoppingListItemService.removeItemById(id);
//    return ResponseEntity.noContent().build();
//  }
//}
