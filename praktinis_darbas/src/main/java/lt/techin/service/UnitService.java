package lt.techin.service;

import lt.techin.model.Unit;
import lt.techin.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
  private final UnitRepository unitRepository;

  @Autowired
  public UnitService(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  public Unit getUnitById(Long id) {
    return unitRepository.findById(id).orElse(null);
  }

  public boolean existsByName(String name) {
    return unitRepository.findByName(name).isPresent();
  }
}
