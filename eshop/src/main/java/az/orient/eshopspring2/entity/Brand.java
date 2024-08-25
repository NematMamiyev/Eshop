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
@Table(name = "brand")
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq")
    @SequenceGenerator(name = "brand_seq", sequenceName = "BRAND_SEQ", allocationSize = 1)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50,unique = true)
    private String name;
    @CreationTimestamp
    private Date dataDate = new Date();
    @ColumnDefault(value = "1")
    private Integer active;

}
