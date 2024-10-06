package az.orient.eshop.entity;

import az.orient.eshop.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@Builder
@DynamicInsert
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "EMPLOYEE_SEQ", allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String surname;
    @Column(nullable = false, length = 70, unique = true)
    private String email;
    @Column(nullable = false, length = 30, unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(nullable = false, length = 20)
    private String password;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
