package az.orient.eshop.entity;

import az.orient.eshop.enums.Status;
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
@Table(name = "order_status")
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_status_seq")
    @SequenceGenerator(name = "order_status_seq",sequenceName = "ORDER_STATUS_SEQ",allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
