public class Item{

    // vars
    private String itemName;
    private float price;
    private String restaurant;
    private int amount;

    // item (itemName, price, restaurant, amount)
    public Item(String itemName, float price, String restaurant, int amount) {
        this.itemName = itemName;
        this.price = price;
        this.restaurant = restaurant;
        this.amount = amount;
    }

    // get/set
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override 
    public String toString(){
        String out = this.amount + " x\t" + this.itemName + "\t" + this.price + "\t" + this.restaurant;
        return out;
    }

}