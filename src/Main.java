import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {

    static class MenuItemData {
        String id;
        String restaurantId;
        String name;
        String description;
        float price;
        boolean available;

        MenuItemData(String[] row) {
            id = row[0];
            restaurantId = row[1];
            name = row[2];
            description = row[3];
            price = Float.parseFloat(row[4]);
            available = Boolean.parseBoolean(row[5]);
        }
    }

    static class RestaurantData {
        String id;
        String name;
        String address;
        String opens;
        String closes;
        List<MenuItemData> menu = new ArrayList<>();

        RestaurantData(String[] row) {
            id = row[0];
            name = row[1];
            address = row[2];
            opens = row[3];
            closes = row[4];
        }
    }

    public static void main(String[] args) {
        Scanner keyIn = new Scanner(System.in);
        Cart cart = new Cart("customer1");

        Path dataDirectory = findDataDirectory();
        List<RestaurantData> restaurants;

        try {
            restaurants = loadRestaurants(dataDirectory);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Unable to load restaurant/menu data: " + e.getMessage());
            return;
        }

        String greeting =
                "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n" +
                "|              QuickBite Restaurant Platform Pickup         |\n" +
                "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        System.out.println(greeting);

        System.out.println("\nINITIAL MENU SCREEN");
        System.out.println("1. I am a customer");
        System.out.println("2. I am a restaurant");
        System.out.print("\nWhat type of user are you?     ");
        int userType = readInt(keyIn);

        if (userType == 1) {
            customerMenu(keyIn, cart, restaurants);
        } else if (userType == 2) {
            System.out.println("To be developed soon...");
        } else {
            System.out.println("Invalid user type.");
        }

        keyIn.close();
    }

    // Li integration: use the prototype's data folder whether Main is run
    // from the project root or directly from src.
    static Path findDataDirectory() {
        Path fromProjectRoot = Paths.get("src", "data");
        if (Files.isDirectory(fromProjectRoot)) return fromProjectRoot;
        return Paths.get("data");
    }

    static List<String[]> readTable(Path file, String expectedHeader) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        if (lines.isEmpty() || !lines.get(0).replace("\uFEFF", "").equals(expectedHeader)) {
            throw new IllegalArgumentException("Incorrect header in " + file);
        }

        int expectedFields = expectedHeader.split("\\|", -1).length;
        List<String[]> rows = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).trim().isEmpty()) continue;

            String[] row = lines.get(i).split("\\|", -1);
            if (row.length != expectedFields) {
                throw new IllegalArgumentException("Incorrect field count in " + file);
            }

            for (int j = 0; j < row.length; j++) {
                row[j] = row[j].trim();
            }
            rows.add(row);
        }
        return rows;
    }

    static List<RestaurantData> loadRestaurants(Path directory) throws IOException {
        List<RestaurantData> restaurants = new ArrayList<>();

        for (String[] row : readTable(
                directory.resolve("restaurants.txt"),
                "restaurant_id|name|address|opening_time|closing_time|menu_file")) {

            RestaurantData restaurant = new RestaurantData(row);

            for (String[] itemRow : readTable(
                    directory.resolve(row[5]),
                    "item_id|restaurant_id|name|description|price_cad|available")) {

                MenuItemData item = new MenuItemData(itemRow);
                if (!item.restaurantId.equals(restaurant.id)) {
                    throw new IllegalArgumentException("Menu restaurant ID mismatch");
                }
                restaurant.menu.add(item);
            }

            restaurants.add(restaurant);
        }

        if (restaurants.isEmpty()) {
            throw new IllegalArgumentException("No restaurants found");
        }
        return restaurants;
    }

    static void customerMenu(Scanner keyIn, Cart cart, List<RestaurantData> restaurants) {
        int commandNum = -1;

        while (commandNum != 0) {
            System.out.println("\nCUSTOMER MENU SCREEN");
            System.out.println("1. Browse Restaurants");
            System.out.println("2. View Cart");
            System.out.println("0. Exit Program");
            System.out.print("Enter menu number: ");
            commandNum = readInt(keyIn);

            if (commandNum == 1) {
                browseRestaurants(keyIn, cart, restaurants);
            } else if (commandNum == 2) {
                viewCart(keyIn, cart);
            } else if (commandNum != 0) {
                System.out.println("Invalid command.");
            }
        }

        System.out.println("\n\nThank you for using QuickBite.\n\n1");
    }

    // Li: restaurant browsing and menu integration.
    static void browseRestaurants(Scanner keyIn, Cart cart, List<RestaurantData> restaurants) {
        while (true) {
            System.out.println("\n--- Available Restaurants ---");

            for (int i = 0; i < restaurants.size(); i++) {
                RestaurantData r = restaurants.get(i);
                System.out.printf("%d. %s | %s | %s-%s%n",
                        i + 1, r.name, r.address, r.opens, r.closes);
            }

            System.out.println("0. Return to Main Menu");
            System.out.print("Enter Restaurant Number: ");
            int restaurantNum = readInt(keyIn);

            if (restaurantNum == 0) return;

            if (restaurantNum < 1 || restaurantNum > restaurants.size()) {
                System.out.println("Invalid restaurant number.");
                continue;
            }

            showMenu(keyIn, cart, restaurants.get(restaurantNum - 1));
        }
    }

    static void showMenu(Scanner keyIn, Cart cart, RestaurantData restaurant) {
        while (true) {
            System.out.println("\n--- " + restaurant.name + " Menu ---");

            for (int i = 0; i < restaurant.menu.size(); i++) {
                MenuItemData item = restaurant.menu.get(i);
                System.out.printf("%d. %s | $%.2f%s%n   %s%n",
                        i + 1,
                        item.name,
                        item.price,
                        item.available ? "" : " | UNAVAILABLE",
                        item.description);
            }

            System.out.println("0. Back");
            System.out.print("\nItem to add: ");
            int itemNum = readInt(keyIn);

            if (itemNum == 0) return;

            if (itemNum < 1 || itemNum > restaurant.menu.size()) {
                System.out.println("Invalid item number.");
                continue;
            }

            MenuItemData selected = restaurant.menu.get(itemNum - 1);

            if (!selected.available) {
                System.out.println("Item is unavailable.");
                continue;
            }

            System.out.print("Quantity (1-99; 0 cancels): ");
            int quantity = readInt(keyIn);

            if (quantity == 0) continue;
            if (quantity < 1 || quantity > 99) {
                System.out.println("Quantity must be from 1 to 99.");
                continue;
            }

            // Uses Lily's existing Item class and Owen's existing Cart class.
            Item cartItem = findCartItem(cart, selected.name, restaurant.name);

            if (cartItem == null) {
                cart.addCartList(new Item(
                        selected.name,
                        selected.price,
                        restaurant.name,
                        quantity));
            } else {
                cartItem.setAmount(cartItem.getAmount() + quantity);
            }

            System.out.println("Added " + quantity + " x " + selected.name + " to cart.");
        }
    }

    static Item findCartItem(Cart cart, String itemName, String restaurantName) {
        for (Item item : cart.getCartList()) {
            if (item.getItemName().equals(itemName)
                    && item.getRestaurant().equals(restaurantName)) {
                return item;
            }
        }
        return null;
    }

    static void viewCart(Scanner keyIn, Cart cart) {
        System.out.println("\n--- Cart ---");

        if (cart.getCartList().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println(cart);
        System.out.println("1. Remove items");
        System.out.println("2. Return to Main Menu");
        System.out.println("0. Exit Program");
        System.out.print("Enter menu number: ");
        int itemNum = readInt(keyIn);

        if (itemNum == 7) {
            System.out.print("Enter the number of the item you want to modify: ");
            int removeNum = readInt(keyIn);
            System.out.print("Enter the quantity you want to remove: ");
            int quantityRemoved = readInt(keyIn);
            cart.removeCartList(removeNum, quantityRemoved);
        } else if (itemNum == 0) {
            System.out.println("Thank you for using QuickBite.");
            System.exit(0);
        }
    }

    static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a whole number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
