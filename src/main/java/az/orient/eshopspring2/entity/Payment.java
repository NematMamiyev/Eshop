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
    @ManyToOne
    @JoinColumn(name = "payment_method_id",nullable = false)
    private PaymentMethod paymentMethod;
    @Column(nullable = false)
    private Float amount;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
