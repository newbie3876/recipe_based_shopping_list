package lt.techin.model;

import jakarta.persistence.*;

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
}