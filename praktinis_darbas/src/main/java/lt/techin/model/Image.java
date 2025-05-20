package lt.techin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageName;

  private String contentType; // pvz., "image/jpeg", "image/png"

  @Lob
  @Column(name = "image_data", columnDefinition = "LONGBLOB")
  private byte[] imageData;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Image(String imageName, String contentType, byte[] imageData, User user) {
    this.imageName = imageName;
    this.contentType = contentType;
    this.imageData = imageData;
    this.user = user;
  }

  public Image() {
  }

  public Long getId() {
    return id;
  }


  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public byte[] getImageData() {
    return imageData;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
