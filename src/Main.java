import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Läs in kundinformation
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your address:");
        String address = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        // Skapa kundobjekt
        Customer customer = new Customer(name, address, email);
        Order order = new Order(customer);

        boolean addMoreClothing = true;
        while (addMoreClothing) {
            System.out.println("Choose a clothing item to add:");
            System.out.println("1. Pants");
            System.out.println("2. T-Shirt");
            System.out.println("3. Skirt");
            System.out.println("4. Finish and place order");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.println("Choose Pants attributes:");
                    Pants pants = createPants(scanner);
                    if (pants != null) {
                        order.addClothing(pants);
                    }
                    break;
                case 2:
                    System.out.println("Choose T-Shirt attributes:");
                    TShirt tShirt = createTShirt(scanner);
                    if (tShirt != null) {
                        order.addClothing(tShirt);
                    }
                    break;
                case 3:
                    System.out.println("Choose Skirt attributes:");
                    Skirt skirt = createSkirt(scanner);
                    if (skirt != null) {
                        order.addClothing(skirt);
                    }
                    break;
                case 4:
                    // Fråga om bekräftelse innan kvittot skrivs ut
                    System.out.println("Are you sure you want to place the order? (yes/no)");
                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        addMoreClothing = false;
                    } else {
                        System.out.println("Order cancelled.");
                        return; // Avbryt programmet om ordern avbryts
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }

        // Skriv ut kvitto med beställningens detaljer
        System.out.println("Order Summary:");
        System.out.println(order.getOrderDetails());

        // Skicka beställning till butiken och notifiera
        ClothingShop shop = new ClothingShop();
        CEOObserver ceoObserver = new CEOObserver();
        VDObserver vdObserver = new VDObserver();
        shop.addObserver(ceoObserver);
        shop.addObserver(vdObserver);

        shop.placeOrder(order);
        shop.notifyReadyForDelivery(); // Notify when the order is ready for delivery
    }

    private static Pants createPants(Scanner scanner) {
        String size, material, color, fit, length;

        boolean validSize = false;
        while (!validSize) {
            System.out.println("Size (S/M/L):");
            size = scanner.nextLine().toUpperCase();

            // Validera storleken
            if (size.equals("S") || size.equals("M") || size.equals("L")) {
                validSize = true;

                System.out.println("Material:");
                material = scanner.nextLine();

                System.out.println("Color:");
                color = scanner.nextLine();

                System.out.println("Fit:");
                fit = scanner.nextLine();

                System.out.println("Length:");
                length = scanner.nextLine();

                // Validera att alla attribut är angivna
                if (material.isEmpty() || color.isEmpty() || fit.isEmpty() || length.isEmpty()) {
                    System.out.println("Please enter all attributes.");
                    validSize = false; // loops om något attribut saknas
                } else {
                    return new Pants(size, material, color, fit, length);
                }
            } else {
                System.out.println("Invalid size. Please enter S, M, or L.");
            }
        }
        return null;
    }

    private static TShirt createTShirt(Scanner scanner) {
        String size, material, color, sleeves, neck;

        boolean validSize = false;
        while (!validSize) {
            System.out.println("Size (S/M/L):");
            size = scanner.nextLine().toUpperCase();

            // Validera storleken
            if (size.equals("S") || size.equals("M") || size.equals("L")) {
                validSize = true;

                System.out.println("Material:");
                material = scanner.nextLine();

                System.out.println("Color:");
                color = scanner.nextLine();

                System.out.println("Sleeves:");
                sleeves = scanner.nextLine();

                System.out.println("Neck:");
                neck = scanner.nextLine();

                // Validera att alla attribut är angivna
                if (material.isEmpty() || color.isEmpty() || sleeves.isEmpty() || neck.isEmpty()) {
                    System.out.println("Please enter all attributes.");
                    validSize = false; // loops om något attribut saknas
                } else {
                    return new TShirt(size, material, color, sleeves, neck);
                }
            } else {
                System.out.println("Invalid size. Please enter S, M, or L.");
            }
        }
        return null;
    }

    private static Skirt createSkirt(Scanner scanner) {
        String size, material, color, waistline, pattern;

        boolean validSize = false;
        while (!validSize) {
            System.out.println("Size (S/M/L):");
            size = scanner.nextLine().toUpperCase();

            // Validera storleken
            if (size.equals("S") || size.equals("M") || size.equals("L")) {
                validSize = true;

                System.out.println("Material:");
                material = scanner.nextLine();

                System.out.println("Color:");
                color = scanner.nextLine();

                System.out.println("Waistline:");
                waistline = scanner.nextLine();

                System.out.println("Pattern:");
                pattern = scanner.nextLine();

                // Validera att alla attribut är angivna
                if (material.isEmpty() || color.isEmpty() || waistline.isEmpty() || pattern.isEmpty()) {
                    System.out.println("Please enter all attributes.");
                    validSize = false; // loops om något attribut saknas
                } else {
                    return new Skirt(size, material, color, waistline, pattern);
                }
            } else {
                System.out.println("Invalid size. Please enter S, M, or L.");
            }
        }
        return null;
    }

}

// Klassdefinitioner för Customer, Order, ClothingShop, CEOObserver, VDObserver, Pants, TShirt, Skirt

class ClothingShop {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void placeOrder(Order order) {
        for (Observer observer : observers) {
            observer.update(order);
        }
    }

    public void notifyReadyForDelivery() {
        for (Observer observer : observers) {
            observer.readyForDelivery();
        }
    }
}

class CEOObserver implements Observer {
    @Override
    public void update(Order order) {
        System.out.println("CEO: Order placed. Notifying VD.");
    }

    @Override
    public void readyForDelivery() {
        System.out.println("CEO: Clothing ready for delivery. Notifying VD.");
    }
}

class VDObserver implements Observer {
    @Override
    public void update(Order order) {
        System.out.println("VD: Order placed. Notifying CEO.");
    }

    @Override
    public void readyForDelivery() {
        System.out.println("VD: Clothing ready for delivery. Notifying CEO.");
    }
}

class Order {
    private Customer customer;
    private List<Clothing> clothingList = new ArrayList<>();

    public Order(Customer customer) {
        this.customer = customer;
    }

    public void addClothing(Clothing clothing) {
        clothingList.add(clothing);
    }

    public List<Clothing> getClothingList() {
        return clothingList;
    }

    public String getOrderDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer: ").append(customer.getName()).append("\n");
        builder.append("Address: ").append(customer.getAddress()).append("\n");
        builder.append("Email: ").append(customer.getEmail()).append("\n");
        builder.append("Items ordered:\n");
        for (Clothing clothing : clothingList) {
            builder.append("- ").append(clothing.getDescription()).append("\n");
        }
        return builder.toString();
    }
}

class Customer {
    private String name;
    private String address;
    private String email;

    public Customer() {
    }

    public Customer(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

abstract class Clothing {
    private String size;
    private String material;
    private String color;

    public Clothing() {
    }

    public Clothing(String size, String material, String color) {
        this.size = size;
        this.material = material;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Beskrivning av klädesplagget
    public abstract String getDescription();
}

class Pants extends Clothing {
    private String fit;
    private String length;

    public Pants() {
    }

    public Pants(String size, String material, String color, String fit, String length) {
        super(size, material, color);
        this.fit = fit;
        this.length = length;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String getDescription() {
        return getSize() + " " + getColor() + " pants made of " + getMaterial() + ", " +
                "fit: " + getFit() + ", length: " + getLength();
    }
}

class TShirt extends Clothing {
    private String sleeves;
    private String neck;

    public TShirt() {
    }

    public TShirt(String size, String material, String color, String sleeves, String neck) {
        super(size, material, color);
        this.sleeves = sleeves;
        this.neck = neck;
    }

    public String getSleeves() {
        return sleeves;
    }

    public void setSleeves(String sleeves) {
        this.sleeves = sleeves;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    @Override
    public String getDescription() {
        return getSize() + " " + getColor() + " t-shirt made of " + getMaterial() + ", " +
                "sleeves: " + getSleeves() + ", neck: " + getNeck();
    }
}

class Skirt extends Clothing {
    private String waistline;
    private String pattern;

    public Skirt() {
    }

    public Skirt(String size, String material, String color, String waistline, String pattern) {
        super(size, material, color);
        this.waistline = waistline;
        this.pattern = pattern;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getDescription() {
        return getSize() + " " + getColor() + " skirt made of " + getMaterial() + ", " +
                "waistline: " + getWaistline() + ", pattern: " + getPattern();
    }
}

interface Observer {
    void update(Order order);

    void readyForDelivery();
}
