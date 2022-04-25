package i7.Models;

public class CartItem {
    private int quantity;
    private MenuItem item;

    public CartItem(MenuItem item) {
        this.quantity = 0;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
