package lt.techin.controller;

import lt.techin.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }
}
