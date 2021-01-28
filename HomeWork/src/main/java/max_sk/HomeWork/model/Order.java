package max_sk.HomeWork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "count")
    private int count;

    @Column(name = "cost")
    private int cost;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date updateAt;

    public Order(Long idProduct, Long orderNumber, String title, int count, int cost, Date updateAt) {
        this.idProduct = idProduct;
        this.orderNumber = orderNumber;
        this.title = title;
        this.count = count;
        this.cost = cost;
        this.updateAt = updateAt;
    }
}
