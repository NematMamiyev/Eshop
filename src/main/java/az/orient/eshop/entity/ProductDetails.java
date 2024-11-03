package az.orient.eshop.entity;

import az.orient.eshop.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "product_details")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_details_seq")
    @SequenceGenerator(name = "product_details_seq",sequenceName = "PRODUCT_DETAILS_SEQ",allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(nullable = false)
    private Integer stock;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_details_id")
    private Set<ProductImage> images = new HashSet<>();
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_details_id")
    private Set<ProductVideo> videos = new HashSet<>();
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}
