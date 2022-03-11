import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {
    private Long id;

    private Date orderDate;
    private Date deliveryDate;
    private String status;

    private Customer customer;

    Set<Product> products = new HashSet<Product>();

    public Order(Long id, Date orderDate, Date deliveryDate, String status, Customer customer) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", products=" + products +
                '}';
    }
}
