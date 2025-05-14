package lt.techin.service;

import lt.techin.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }
}
