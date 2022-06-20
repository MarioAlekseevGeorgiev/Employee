package tech.getarrays.api.company.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long   id;
    private String company;
    private String email;
    private String address;
    private String phone;
    private String vatNumber;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", bulstat='" + vatNumber + '\'' +
                '}';
    }
}
