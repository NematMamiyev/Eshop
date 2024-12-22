package az.orient.eshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "warehouse_work")
@Setter
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseWork {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_work_seq")
    @SequenceGenerator(name = "warehouse_work_seq", sequenceName = "WAREHOUSE_WORK_SEQ", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
