package az.orient.eshop.entity;

import az.orient.eshop.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_seq")
    @SequenceGenerator(name = "product_seq",sequenceName = "PRODUCT_SEQ",allocationSize = 1)
    private Long id;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(name = "product_information",length = 500)
    private String productInformation;
    @Column(name = "expertion_date")
    private Date expertionDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetails> productDetails = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}

