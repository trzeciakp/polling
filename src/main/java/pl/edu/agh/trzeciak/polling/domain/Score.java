package pl.edu.agh.trzeciak.polling.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Score.
 */
@Entity
@Table(name = "T_SCORE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Score implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "product_a")
    private Product productA;

    @ManyToOne
    @JoinColumn(name = "product_b")
    private Product productB;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Product getProductA() {
        return productA;
    }

    public void setProductA(Product productA) {
        this.productA = productA;
    }

    public Product getProductB() {
        return productB;
    }

    public void setProductB(Product productB) {
        this.productB = productB;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Score score = (Score) o;

        if (id != null ? !id.equals(score.id) : score.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", value='" + value + "'" +
                '}';
    }
}
