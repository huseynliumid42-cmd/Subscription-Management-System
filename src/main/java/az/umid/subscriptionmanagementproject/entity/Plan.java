package az.umid.subscriptionmanagementproject.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer MonthlyPrice;

    public Plan() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMonthlyPrice() {
        return MonthlyPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyPrice(Integer monthlyPrice) {
        MonthlyPrice = monthlyPrice;
    }
}
