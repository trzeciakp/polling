package pl.edu.agh.trzeciak.polling.web.rest.dto;

public class ScoreDTO {

    private int productA;
    private int productB;
    private int value;
    private String user;

    public int getProductA() {
        return productA;
    }

    public void setProductA(int productA) {
        this.productA = productA;
    }

    public int getProductB() {
        return productB;
    }

    public void setProductB(int productB) {
        this.productB = productB;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "productA=" + productA +
                ", productB=" + productB +
                ", value=" + value +
                ", user='" + user + '\'' +
                '}';
    }
}
