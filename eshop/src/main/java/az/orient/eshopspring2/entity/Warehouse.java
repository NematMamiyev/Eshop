package az.orient.eshopspring2.entity;

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
@Table(name = "warehouse")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_seq")
    @SequenceGenerator(name = "warehouse_seq", sequenceName = "WAREHOUSE_SEQ", allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 50,unique = true)
    private String name;
    @Column(nullable = false,length = 150)
    private String address;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
