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
            while (commandNum != 0) {
                //navigation for customers
                System.out.println("1. Browse Restaurants");
                System.out.println("2. View Cart");
                System.out.println("0. Exit Program");
                System.out.println("Enter command number: ");
                commandNum = keyIn.nextInt();
                //create cart for user using cart constructor
                if (commandNum == 1) {
                    //have a list of restaurants to view from
                    // includes 0. Exit program at the end
                    // includes (for example) 8. return to Main Menu
                    System.out.println("Enter Restaurant Number: ");
                    int restaurantNum = keyIn.nextInt();
                    //display the menu of the selected restaurant
                    //add selected items to cart using add to cart function

                    //if enter number to return to main menu
                    if (restaurantNum == 8) continue;
                    //if enter 0 to exit program
                    if(restaurantNum == 0) {
                        commandNum = 0;
                    }
                }
                if(commandNum == 2) {
                    // includes 0. Exit program at the end
                    System.out.println("List of items ");
                    //will display cart, numbering each item
                    System.out.println("7. Remove items ");
                    //return to main menu
                    System.out.println("8. Return to Main Menu ");
                    //exit program
                    System.out.println("0. Exit Program");

                    System.out.println("Enter command number: ");
                    int itemNum = keyIn.nextInt();
                    //function to remove item
                    if (itemNum == 7) {
                        System.out.println("Enter the number of the item you want to modify: ");
                        int removeNum = keyIn.nextInt(); //temp until i have the item class
                        System.out.println("Enter the quantity you want to remove: ");
                        int quantityRemoved = keyIn.nextInt();
                        //use remove item function
                        continue;
                    }
                    //return to main menu
                    if (itemNum == 8) continue;
                    //exit program
                    if(itemNum == 0) {
                        commandNum = 0;
                    }

                }
            }
            System.exit(0);
        case 2:
            System.out.println("To be developed soon...");

    }
}
