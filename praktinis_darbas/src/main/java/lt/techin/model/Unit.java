package lt.techin.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "units")
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 80, nullable = false, unique = true)
  private String name;

  public Unit(String name) {
    this.name = name;
  }

  public Unit() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Unit)) return false;
    Unit unit = (Unit) o;
    // Jei id dar nėra priskirtas (null), naudoti name lyginimui (jei norisi)
    if (id != null && unit.id != null) {
      return id.equals(unit.id);
    }
    return Objects.equals(name, unit.name);
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    }
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Unit{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
