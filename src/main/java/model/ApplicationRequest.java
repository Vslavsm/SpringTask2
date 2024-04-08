package model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appReq")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "comment")
    private String commentary;

    @Column(name = "phoneNumber")
    private String phone;

    @Column(name = "handled")
    private boolean handled;


}
