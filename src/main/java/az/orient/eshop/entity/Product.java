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

import java.util.Date;

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
    @Column(nullable = false)
    private Float price;
    @Column(name = "product_information",length = 500)
    private String productInformation;
    @Column(name = "expertion_date")
    private Date expertionDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false, length = 20,name = "product_number")
    private String productNumber;
    @Lob
    private byte[] image;
    @Lob
    private byte[] video;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
    @Column(nullable = false)
    private Integer stock;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}

