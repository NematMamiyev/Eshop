package az.orient.eshop.securitytoken;

import az.orient.eshop.entity.Customer;
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
@Table(name = "user_token")
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_token_seq")
    @SequenceGenerator(name = "user_token_seq", sequenceName = "USER_TOKEN_SEQ", allocationSize = 1)
    private Long id;
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Builder.Default
    @CreationTimestamp
    private Date dataDate = new Date();
    @ColumnDefault(value = "1")
    private Integer active;
}
