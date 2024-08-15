import java.io.Serializable;

public class Income implements Serializable {
    private String category;
    private double amount;
    private String month;

    public Income(String category, double amount, String month) {
        this.category = category;
        this.amount = amount;
        this.month = month;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getMonth() {
        return month;
    }
}
