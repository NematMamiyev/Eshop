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
@Table(name = "product_image")
@Data
@DynamicInsert
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_image_seq")
    @SequenceGenerator(name = "product_image_seq",sequenceName = "PRODUCT_IMAGE_SEQ",allocationSize = 1)
    private Long id;
    @Lob
    private byte[] data;
    @Column(nullable = false, length = 200)
    private String fileName;
    private String fileType;
    @ManyToOne
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetails;
    @CreationTimestamp
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;

    public ProductImage(String fileName,String fileType,byte[] data,ProductDetails productDetails){
        this.fileName=fileName;
        this.fileType=fileType;
        this.data=data;
        this.productDetails=productDetails;
    }
}
