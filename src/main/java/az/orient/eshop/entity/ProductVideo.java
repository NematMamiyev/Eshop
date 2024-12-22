package az.orient.eshop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "product_video")
@Setter
@Getter
@DynamicInsert
@NoArgsConstructor
public class ProductVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_video_seq")
    @SequenceGenerator(name = "product_video_seq",sequenceName = "PRODUCT_VIDEO_SEQ",allocationSize = 1)
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
    public ProductVideo(String fileName,String fileType,byte[] data,ProductDetails productDetails){
        this.fileName=fileName;
        this.fileType=fileType;
        this.data=data;
        this.productDetails=productDetails;
    }
}
