import java.util.Scanner;

void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Scanner keyIn = new Scanner(System.in); //naming scanner
    //welcome banner
    String greeting = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n" + "| \t\t\tQuickBite Restaurant Platform Pickup\t\t\t| \n" + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
    System.out.println(greeting);

    //First navigation screen
    System.out.println("1. I am a customer");
    System.out.println("2. I am a restaurant");
    System.out.println("What type of user are you? ");
    int userType = keyIn.nextInt();
    int commandNum = -1;//initializing to some random value

    switch (userType) {
        case 1:
            Cart cart = new Cart("user001");
            while (commandNum != 0) {
                //navigation for customers
                System.out.println("1. Browse Restaurants");
                System.out.println("2. View Cart");
                System.out.println("0. Exit Program");
                System.out.println("Enter command number: ");
                commandNum = keyIn.nextInt();


                if (commandNum == 1) {

                    // Temporary restaurant selection
                    System.out.println("Enter Restaurant Number: ");
                    int restaurantNum = keyIn.nextInt();

                    if (restaurantNum == 8) continue;

                    if (restaurantNum == 0) {
                        commandNum = 0;
                        continue;
                    }

                    // Stay inside this restaurant's menu
                    int itemChoice = -1;

                    while (itemChoice != 8 && itemChoice != 0) {

                        System.out.println("=== Restaurant Menu ===");
                        System.out.println("1. Burger - $12.99");
                        System.out.println("2. Pizza - $15.99");
                        System.out.println("3. Sushi - $10.99");
                        System.out.println("8. Return to Main Menu");
                        System.out.println("0. Exit Program");

                        System.out.print("Choose an item: ");
                        itemChoice = keyIn.nextInt();

                        if (itemChoice == 8) {
                            break;
                        }

                        if (itemChoice == 0) {
                            commandNum = 0;
                            break;
                        }

                        String itemName = "";
                        float price = 0;

                        switch (itemChoice) {
                            case 1:
                                itemName = "Burger";
                                price = 12.99f;
                                break;
                            case 2:
                                itemName = "Pizza";
                                price = 15.99f;
                                break;
                            case 3:
                                itemName = "Sushi";
                                price = 10.99f;
                                break;
                            default:
                                System.out.println("Invalid item.");
                                continue;
                        }

                        System.out.print("Enter quantity: ");
                        int quantity = keyIn.nextInt();

                        while (quantity <= 0) {
                            System.out.print("Invalid quantity. Try again: ");
                            quantity = keyIn.nextInt();
                        }

                        Item selectedItem = new Item(
                                itemName, price, "Test Restaurant", quantity
                        );

                        cart.addCartList(selectedItem);

                        System.out.println("Item successfully added to cart!");
                    }
                }

                if (commandNum == 2) {

                    System.out.println("=== Your Cart ===");
                    System.out.println(cart);

                    System.out.println("7. Remove items");
                    System.out.println("8. Return to Main Menu");
                    System.out.println("0. Exit Program");

                    System.out.print("Enter command number: ");
                    int itemNum = keyIn.nextInt();

                    if (itemNum == 7) {
                        System.out.print("Enter the number of the item you want to modify: ");
                        int removeNum = keyIn.nextInt();

                        System.out.print("Enter the quantity you want to remove: ");
                        int quantityRemoved = keyIn.nextInt();

                        cart.removeCartList(removeNum, quantityRemoved);
                        continue;
                    }

                    if (itemNum == 8) {
                        continue;
                    }

                    if (itemNum == 0) {
                        commandNum = 0;
                    }
                }
            }
            System.exit(0);
        case 2:
            System.out.println("To be developed soon...");

    }
}
