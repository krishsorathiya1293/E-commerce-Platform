import java.sql.*;
import java.util.*;

//import com.mysql.cj.jdbc.CallableStatement;
class Product {
    private static int PreparedStatement;
    private int id;
    private String name;
    private double price;
    private int Quantity;
    private int tq;

    public Product(int id, String name, double price, int Quantity, int tq) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.Quantity = Quantity;
        this.tq = tq;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int gettq() {
        return tq;
    }

    public int getQuantity() {
        return Quantity;
    }

    public static void Updatedatabase(String sql, int Id, double tQ1, int PQ) throws SQLException {
        String sql2 = sql;
        int UQ = (int) (tQ1 - PQ);
        PreparedStatement pst1 = App.con.prepareStatement(sql2);
        pst1.setInt(1, UQ);
        pst1.setInt(2, Id);
        pst1.executeUpdate();
    }

    public static int additemincart(int ID, String g1, double tQ1, double Price, String Categories) throws Exception {
        Scanner sc = new Scanner(System.in);
        String c = Categories;
        String ItemName = g1;
        int ItemId = ID;
        double ItemPrice = Price;
        int PQ = 0;
        System.out.println("For Add Cart Press 1  \nOtherwise Press 2.");
        System.out.println("You want to Exit Grossary CATEGORIE then press 3.");
        int i1 = sc.nextInt();
        if (i1 == 1) {
            System.out.println("Enter Quantity/pice You want to buy.");
            PQ = sc.nextInt();
            Product p = new Product(ItemId, ItemName, ItemPrice, PQ, (int) tQ1);
            App.cart.addItem(p);
            System.out.println("Enterd successfully:");
            if (c.equals("Grossary")) {
                String sql1 = "Update grossary set Quantity=? where Id=?";
                Updatedatabase(sql1, ID, tQ1, PQ);
            } else if (c.equals("Electronics")) {
                String sql1 = "Update  electronics_items set Stock=? where Id=?";
                Updatedatabase(sql1, ID, tQ1, PQ);
            } else if (c.equals("personalCare")) {
                String sql1 = "Update  personal_care set Stock=? where Id=?";
                Updatedatabase(sql1, ID, tQ1, PQ);
            }
        }
        String s = "insert into sell(Id,Name,Price,Quantity,Categories) values(?,?,?,?,?)";
        PreparedStatement pst = App.con.prepareStatement(s);
        pst.setInt(1, ID);
        pst.setString(2, ItemName);
        pst.setDouble(3, ItemPrice);
        pst.setInt(4, PQ);
        pst.setString(5, c);
        pst.executeUpdate();
        if (i1 == 2 && Categories == "Grossary") {
            serchGrossary();
        } else if (i1 == 2 && Categories == "Electronics") {
            serchElectronicsItems();
        } else if (i1 == 2 && Categories == "personalCare") {
            SerchPersonalCare();
        }
        return i1;
    }

    public static void serchGrossary() throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("            ... Refrances...");
            System.out.println();
            System.out.println("-->> wheat--Rice--salt--sugar--shampoo <<-- ");
            System.out.println("-->> soap--Toothpaste--Toothbrushes--hand sanatizer <<--");
            System.out.println("-->> For Exit Write quit <<--");
            System.out.println();
            System.out.println("Enter Grossary name:");
            String g1 = sc.nextLine();
            if (g1.equalsIgnoreCase("quit")) {
                break;
            }
            String sql = "{call Getgrossary(?)}";
            java.sql.CallableStatement cst = App.con.prepareCall(sql);
            cst.setString(1, g1);
            ResultSet rs = cst.executeQuery();
            int ID = 0;
            int TQ1 = 0;
            double price = 0;
            String Categories = null;
            while (rs.next()) {
                ID = rs.getInt(1);
                TQ1 = rs.getInt(4);
                price = rs.getDouble(3);
                Categories = rs.getString(5);
                System.out.println();
                System.out.println(" ID               :-  " + ID);
                System.out.println(" NAME             :-  " + rs.getString(2));
                System.out.println(" Total Quantity   :-  " + TQ1);
                System.out.println(" Price/kg/pice    :-  " + price);
                System.out.println();
            }
            if (TQ1 > 0) {
                System.out.println("Item Is Avaleble:");
            } else {
                System.out.println("Sorry Item is not Avaleble:");
                serchGrossary();
            }
            int r1 = additemincart(ID, g1, TQ1, price, Categories);
            if (r1 == 3) {
                break;
            }
        }
    }

    public static void serchElectronicsItems() throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("            ... Refrances...");
            System.out.println();
            System.out.println("-->> Laptop--Smartphone--Smart TV--Wireless Earbuds <<--");
            System.out.println("-->> Fitness Tracker--Gaming Console--Digital Camera--Tablet <<--");
            System.out.println("-->> For Exit Write quit <<--");
            System.out.println();
            System.out.println("Enter Electronics items name.");
            String Ename = sc.nextLine();
            if (Ename.equalsIgnoreCase("quit")) {
                break;
            }
            System.out.println("Eter Brand name.");
            String Ebrand = sc.nextLine();
            String sql = "{call GetElectronics(?)}";
            java.sql.CallableStatement cst = App.con.prepareCall(sql);
            cst.setString(1, Ename);
            ResultSet rs = cst.executeQuery();
            int ID = 0;
            int TS = 0;
            String B = null, EN = null;
            Double Unitprice = 0.0;
            String Categories = null;
            while (rs.next()) {
                ID = rs.getInt(1);
                EN = rs.getString(2);
                TS = rs.getInt(5);
                B = rs.getString(3);
                Unitprice = rs.getDouble(6);
                Categories = rs.getString(7);
                System.out.println();
                System.out.println(" ID             :-  " + ID);
                System.out.println(" NAME           :-  " + EN);
                System.out.println(" Total STOCK    :-  " + TS);
                System.out.println(" BRAND          :-  " + B);
                System.out.println(" Description    :-  " + rs.getString(4));
                System.out.println(" Unit Price     :-  " + Unitprice);
                System.out.println(" CATEGORIES     :-  " + Categories);
                System.out.println();
            }
            if (TS > 0) {
                System.out.println("Item is Avaleble:");
            } else {
                System.out.println("Sorry Item is not Avaleble:");
                serchElectronicsItems();
            }
            int r1 = additemincart(ID, Ename, (double) TS, Unitprice, Categories);
            if (r1 == 3) {
                break;
            }

        }
    }

    public static void SerchPersonalCare() throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("            ... Refrances...");
            System.out.println();
            System.out.println("-->> Toothpaste--Shampoo--Moisturizer--personalCare--Hair Oil <<--");
            System.out.println("-->> Conditioner--Hair Color--Face Wash--Lipstics--Beard oil <<--");
            System.out.println("-->> For Exit Write quit <<--");
            System.out.println();
            System.out.println("Enter PersonalCare item name:");
            String pcname = sc.nextLine();
            if (pcname.equalsIgnoreCase("quit")) {
                break;
            }
            System.out.println("Enter Brand name:");
            String pcbrand = sc.nextLine();
            String sql = "{call GetPersonalCare(?)}";
            java.sql.CallableStatement cst = App.con.prepareCall(sql);
            cst.setString(1, pcname);
            ResultSet rs = cst.executeQuery();
            int ID = 0;
            String pcn = null, pcb = null, Categories = null;
            int S = 0;
            Double pcunitprice = 0.0;
            while (rs.next()) {
                ID = rs.getInt(1);
                pcn = rs.getString(2);
                pcb = rs.getString(3);
                S = rs.getInt(6);
                pcunitprice = rs.getDouble(5);
                Categories = rs.getString(7);
                System.out.println();
                System.out.println(" ID             :- " + ID);
                System.out.println(" NAME           :- " + pcn);
                System.out.println(" BRAND          :- " + pcb);
                System.out.println(" TOTAL STOCK    :- " + S);
                System.out.println(" UNIT PRICE     :- " + pcunitprice);
                System.out.println(" QUNTITY        :- " + rs.getString(4));
                System.out.println(" CATEGORIES     :- " + Categories);
                System.out.println();
            }
            if (S > 0) {
                System.out.println("Item is Avaleble:");
            } else {
                System.out.println("Sorry Item is not Avaleble:");
                SerchPersonalCare();
            }
            int r1 = additemincart(ID, pcn, S, pcunitprice, Categories);
            if (r1 == 3) {
                break;
            }
        }
    }
}

class ShoppingCart {
    ArrayList<Product> items = new ArrayList<>();

    void duplicates(List uList) {
        items.addAll(uList);
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public ArrayList<Product> getItems() {
        return items;
    }
}

class seller {
    public static void productsell() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many product you want to sell.");
        int NoOfProduct = sc.nextInt();
        int count = 0;
        int r = 0;
        do {
            sc.nextLine();
            System.out.println("Enter categories name you want to sell:");
            String Cname = sc.nextLine();
            if (Cname.equals("Grossary")) {
                System.out.println("Enter Product name.");
                String Pname = sc.nextLine();
                System.out.println("Enter Product Quantity. ");
                int Pquantity = sc.nextInt();
                System.out.println("Enetr Product sell price.");
                double PsellPrice = sc.nextDouble();
                String sql = "insert into grossary(Name,Price,Quantity,Categorie) values(?,?,?,?)";
                PreparedStatement pst = App.con.prepareStatement(sql);
                pst.setString(1, Pname);
                pst.setDouble(2, PsellPrice);
                pst.setInt(3, Pquantity);
                pst.setString(4, Cname);
                r = pst.executeUpdate();
                String sql1 = "{call GetGrossaryId(?)}";
                CallableStatement cst = App.con.prepareCall(sql1);
                cst.setString(1, Pname);
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    System.out.println();
                    System.out.println("Your product Id is " + rs.getInt(1));
                }
            } else if (Cname.equals("Electronics")) {
                System.out.println("Enter Product name.");
                String Pname = sc.nextLine();
                System.out.println("Enter Product Stock. ");
                int Pquantity = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter your brand name.");
                String Bname = sc.nextLine();
                System.out.println("Enter Product description.");
                String Pdescription = sc.nextLine();
                System.out.println("Enetr Product sell price.");
                double PsellPrice = sc.nextDouble();
                String sql = "insert into electronics_items(Name,Brand,Description,Stock,UnitPrice,Type) values(?,?,?,?,?,?)";
                PreparedStatement pst = App.con.prepareStatement(sql);
                pst.setString(1, Pname);
                pst.setString(2, Bname);
                pst.setString(3, Pdescription);
                pst.setInt(4, Pquantity);
                pst.setDouble(5, PsellPrice);
                pst.setString(6, Cname);
                r = pst.executeUpdate();
                String sql1 = "{call GetElectronicsId(?,?)}";
                CallableStatement cst = App.con.prepareCall(sql1);
                cst.setString(1, Pname);
                cst.setString(2, Bname);
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    System.out.println("Your product Id is " + rs.getInt(1));
                }
            } else if (Cname.equalsIgnoreCase("PersonalCare")) {
                System.out.println("Enter Product name.");
                String Pname = sc.nextLine();
                System.out.println("Enter your brand name.");
                String Bname = sc.nextLine();
                System.out.println("Enter Product Stock. ");
                int Pstock = sc.nextInt();
                System.out.println("Enetr Product sell price.");
                double PsellPrice = sc.nextDouble();
                System.out.println("Enter Product quantity. ");
                String Pquantity = sc.nextLine();
                String sql = "insert into personal_care(Name,Brand,Quantity,UnitPrice,Stock,Categories) values(?,?,?,?,?,?)";
                PreparedStatement pst = App.con.prepareStatement(sql);
                pst.setString(1, Pname);
                pst.setString(2, Bname);
                pst.setString(3, Pquantity);
                pst.setDouble(4, PsellPrice);
                pst.setInt(5, Pstock);
                pst.setString(6, Cname);
                r = pst.executeUpdate();
                String sql1 = "{call GetPersonalCareId(?,?)}";
                CallableStatement cst = App.con.prepareCall(sql1);
                cst.setString(1, Pname);
                cst.setString(2, Bname);
                ResultSet rs = cst.executeQuery();
                while (rs.next()) {
                    System.out.println("Your product Id is " + rs.getInt(1));
                }
            }
            count++;
        } while (NoOfProduct != count);
        if (r > 0) {
            System.out.println("Please remember this id...");
            System.out.println("Your Account is created sucessfully.");
            System.out.println();
        } else {
            System.out.println("Your Account is not created please try again.");
        }
    }

    public static void QuantityDetails() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter product categories.");
        String cname = sc.nextLine();
        System.out.println("Enter yout product Id.");
        int id = sc.nextInt();
        boolean check = true;
        if (cname.equalsIgnoreCase("Grossary")) {
            if (check) {
                String sql1 = "select * from grossary where Id=?";
                PreparedStatement pst = App.con.prepareStatement(sql1);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println();
                    System.out.println("Your product Quantity is " + rs.getInt(4));
                    System.out.println();
                }
                check = false;
            }
        }
        if (cname.equalsIgnoreCase("Electronics")) {
            if (check) {
                String sql1 = "select * from electronics_items where Id=?";
                PreparedStatement pst = App.con.prepareStatement(sql1);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("Your product Quantity is " + rs.getInt(5));
                }
                check = false;
            }
        }
        if (cname.equalsIgnoreCase("personalCare")) {
            if (check) {
                String sql1 = "select * from personal_care where Id=?";
                PreparedStatement pst = App.con.prepareStatement(sql1);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println("Your product Quantity is " + rs.getInt(6));
                }
                check = false;
            }
        }
        if (check) {
            System.out.println("Enter valid Id..");
        }
    }

    public static void UpdateSellPrice(String sql1) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter yout product Id.");
        int id = sc.nextInt();
        System.out.println("Enter Updated Price:");
        double Uprice = sc.nextInt();
        boolean check = true;
        int r = 0;
        String sql = sql1;
        PreparedStatement pst = App.con.prepareStatement(sql);
        pst.setDouble(1, Uprice);
        pst.setInt(2, id);
        r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("Your sellprice is Updated..");
            System.out.println();
        } else {
            System.out.println("Your sellprice is not Updated please try Again..");
        }
    }
}

class App {
    static String dburl;
    static String dbuser;
    static String dbpass;
    static String drivername;
    static Connection con;

    public static void menu() {
        System.out.println("===== :: CATEGORIES :: =====");
        System.out.println();
        System.out.println("1  : Grossry.");
        System.out.println("2  : Electronics items.");
        System.out.println("3  : Personal Care.");
        System.out.println("4  : Display Cart.");
        System.out.println("5  : Remove item from cart.");
        System.out.println("6  : For Invoice.");
        System.out.println("7  : For Product sell.");
        System.out.println("8  : For Exit.");
        System.out.println();
    }

    static String space(String s) {
        String s1 = " ";
        for (int i = s.length(); i < 10; i++) {
            s1 += " ";
        }
        return s1;
    }

    static ShoppingCart cart = new ShoppingCart();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        dburl = "jdbc:mysql://localhost:3306/ecommerce";
        dbuser = "root";
        dbpass = "";
        drivername = "com.mysql.cj.jdbc.Driver";
        Class.forName(drivername);
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }

        int choice;
        do {
            menu();
            System.out.println("Enter Your Choice:");
            choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    Product.serchGrossary();
                    break;
                }
                case 2: {
                    Product.serchElectronicsItems();
                    break;
                }
                case 3: {
                    Product.SerchPersonalCare();
                    break;
                }
                case 4: {
                    System.out.println(
                            "-------------------------------------------------------------------------------------");
                    System.out.println("          ----:: Cart Items ::----");
                    System.out.println(
                            "=====================================================================================");
                    System.out.print("ID");
                    System.out.print("         ");
                    System.out.print("Items");
                    System.out.print("         ");
                    System.out.print("Price");
                    System.out.print("       ");
                    System.out.println("Quantity");
                    System.out.println(
                            "=====================================================================================");
                    for (Product item : cart.getItems()) {
                        System.out.println(item.getId() + space(Integer.toString(item.getId())) + item.getName()
                                + space(item.getName()) + item.getPrice() + space(Double.toString(item.getPrice()))
                                + item.getQuantity());
                        System.out.println();
                        System.out.println(
                                "-------------------------------------------------------------------------------------");
                    }
                    break;
                }
                case 5: {
                    System.out.println("Enter id removed product. ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter catagory:");
                    String categories = sc.nextLine();
                    int pq = 0;
                    int tq = 0;
                    try {
                        for (Product item : App.cart.getItems()) {
                            if (item.getId() == id) {
                                tq = item.gettq();
                                pq = item.getQuantity();
                                cart.removeItem(item);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println();
                    }
                    int Q = tq;
                    int r = 0;
                    if (categories.equalsIgnoreCase("Grossary")) {
                        String sql = "Update grossary set Quantity=? where Id=?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setInt(1, Q);
                        pst.setInt(2, id);
                        r = pst.executeUpdate();
                    }
                    if (categories.equalsIgnoreCase("Electronics")) {
                        String sql = "Update electronics_items set Quantity=? where Id=?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setInt(1, Q);
                        pst.setInt(2, id);
                        r = pst.executeUpdate();
                    }
                    if (categories.equalsIgnoreCase("personalCare")) {
                        String sql = "Update personal_care set Quantity=? where Id=?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setInt(1, Q);
                        pst.setInt(2, id);
                        r = pst.executeUpdate();
                    }
                    if (r > 0) {
                        System.out.println("Removed item from cart.");
                        System.out.println();
                    } else {
                        System.out.println("Not removed please try again.");
                    }
                    break;
                }
                case 6: {
                    Double Total_Amount = 0.0;
                    System.out.println(
                            "--------------------------------------------------------------------------------------------");
                    System.out.println();
                    System.out.println("                                 =====   :: INVOICE ::   =====");
                    System.out.println();
                    System.out.println(
                            "============================================================================================");
                    System.out.print("Item");
                    System.out.print(space(" "));
                    System.out.print("Quantity");
                    System.out.print(space(" "));
                    System.out.println("Price");
                    System.out.println(
                            "============================================================================================");
                    for (Product item : cart.getItems()) {
                        System.out.println(
                                "--------------------------------------------------------------------------------------------");
                        System.out.println(item.getName() + space(item.getName()) + item.getQuantity()
                                + space(Integer.toString(item.getId()))
                                + space(Double.toString(item.getPrice())) + item.getPrice());
                        Total_Amount += item.getPrice();
                    }
                    System.out.println(
                            "============================================================================================");
                    System.out.println("TOTAL AMOUNT                     " + Total_Amount + " /-");
                    System.out.println("                                  Inculiding GST");
                    System.out.println(
                            "-------------------------------------------------------------------------------------------");
                    break;
                }
                case 7: {
                    int choice1;
                    do {
                        System.out.println("-->>Enter your choice.");
                        System.out.println("1 : Create Account.");
                        System.out.println("2 : For Quntity details.");
                        System.out.println("3 : Update your product sell price.");
                        System.out.println("4 : Check my all product sell.");
                        System.out.println("5 : For Exit.");
                        choice1 = sc.nextInt();
                        switch (choice1) {
                            case 1: {
                                seller.productsell();
                                break;
                            }
                            case 2: {
                                seller.QuantityDetails();
                                break;
                            }
                            case 3: {
                                sc.nextLine();
                                System.out.println("Enter product categories.");
                                String cname = sc.nextLine();
                                if (cname.equalsIgnoreCase("Grossary")) {
                                    String sql = "Update grossary set Price=? where Id=?";
                                    seller.UpdateSellPrice(sql);
                                }
                                if (cname.equalsIgnoreCase("Electronics")) {
                                    String sql = "Update electronics_items set UnitPrice=? where Id=?";
                                    seller.UpdateSellPrice(sql);
                                }
                                if (cname.equalsIgnoreCase("personalCare")) {
                                    String sql = "Update personal_care set UnitPrice=? where Id=?";
                                    seller.UpdateSellPrice(sql);
                                }
                                break;
                            }
                            case 4: {
                                try {
                                    System.out.println("Enetr your Product id:");
                                    int Pid = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Enter Categories:");
                                    String c = sc.nextLine();
                                    double Price = 0, Total_sell_price = 0;
                                    int Quantity = 0;
                                    int ID = 0;
                                    String sql = "SELECT Categories,Id ,SUM(Price),COUNT(*) FROM sell GROUP BY Id,Categories HAVING Id=? AND Categories=?;";
                                    PreparedStatement pst = con.prepareStatement(sql);

                                    pst.setInt(1, Pid);
                                    pst.setString(2, c);
                                    ResultSet rs = pst.executeQuery();
                                    while (rs.next()) {
                                        Price = rs.getDouble(3);
                                        Quantity = rs.getInt(4);
                                        ID = rs.getInt(2);
                                    }

                                    System.out.println();
                                    System.out.println(
                                            "--------------------------------------------------------------------------------------");
                                    System.out.println();
                                    System.out.println("---- :: PRODUCT SELL DETAILS :: ----");
                                    System.out.println();
                                    System.out.println("YOUR PRODUCT ID     :-   " + ID);
                                    System.out.println("TOTAL SELL QUANTITY :-   " + Quantity);
                                    System.out.println("TOTAL SELL PRICE    :-   " + Price);
                                    System.out.println(
                                            "--------------------------------------------------------------------------------------");
                                    System.out.println();
                                    break;
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                            default:
                                System.out.println("Enter valiid choice.");
                                break;
                        }
                    } while (choice1 != 5);

                    break;
                }
                default:
                    System.out.println("Invalid choice please Enter valide choice  ");
                    break;
            }
        } while (choice != 8);

    }
}
