package az.orient.eshop.entity;

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
@Table(name = "orderStatus")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_seq")
    @SequenceGenerator(name = "order_status_seq", sequenceName = "ORDER_STATUS_SEQ", allocationSize = 1)
    private Long id;
    @Column(nullable = false, length = 50,unique = true)
    private String name;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
