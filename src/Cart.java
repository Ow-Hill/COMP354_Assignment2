import java.util.ArrayList;

public class Cart {
    // vars
    private String userID;
    private ArrayList<Item> cartList = new ArrayList<>();

    // consructors
    public Cart(String uid, ArrayList<Item> list){
        this.userID = uid;
        this.cartList = list;
    }

    public Cart(String uid){
        this.userID = uid;
    }


    // get/set
    //  -   userID
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    //  -   cartList
    public ArrayList<Item> getCartList() {
        return this.cartList;
    }

    public void setCartList(ArrayList<Item> cartList) {
        this.cartList = cartList;
    }

    // -    add/remove list
    public void addCartList(Item item){
        this.cartList.add(item);
    }

  
    // if number great enough to remove all, delete from list
    // else remove item amounts from cart 
    public void removeCartList(int index, int quantity){
        // check array bounds
        if (index >= 0 && index < this.cartList.size()) {
            Item item = this.cartList.get(index);
            int newAmount = item.getAmount() - quantity;

            // remove or decrement
            if (newAmount <= 0) {
                this.cartList.remove(index);
            } else {
                item.setAmount(newAmount);
            }

        }else{
            System.out.println("Try input again, not valid index");
        }
    }
    // to string
    public String toString(){
        
        String out = "";
        // loop through and add
        for (int i = 0; i < this.cartList.size(); i++){
            out += "number " + i + ": " + this.cartList.get(i).toString() + "\n";
        }
        
        return out;
    }
    
}
