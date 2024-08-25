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
@Table(name = "subcategory")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_seq")
    @SequenceGenerator(name = "subcategory_seq", sequenceName = "SUBCATEGORY_SEQ", allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 50,unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
