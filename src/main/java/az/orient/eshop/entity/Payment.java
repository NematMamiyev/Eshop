package az.orient.eshop.entity;

import az.orient.eshop.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "payment")
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq",sequenceName = "PAYMENT_SEQ",allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(nullable = false)
    private BigDecimal amount;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
