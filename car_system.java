import java.math.BigInteger;
import java.sql.*;
import java.util.Scanner;

public class car_system {
    public static void main(String args[]) {
        String url = "jdbc:mysql://localhost:3306/car_rental_system";
        String password = "@#aditya2006";
        String username = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("error at drivers.");
        }
        Scanner sc = new Scanner(System.in);
        try {

            Connection cn = DriverManager.getConnection(url, username, password);
            System.out.println();
            while (true) {
                System.out.println();
                System.out.println("|-------------------------------------------------|");
                System.out.println("|      *-*-*-*- CAR RENTAL SYSTEM *-*-*-*-        |");
                System.out.println("|-------------------------------------------------|");
                System.out.println();
                System.out.println("1.rent a car.\n2.add a car for renting.\n3.return a rented car.\n4.show rented car users\n5remove a car from rent.\n6.exit : ");
                int ch = sc.nextInt();
                switch (ch) {
                    case 1:
                        rentACar(cn, sc);
                        break;
                    case 2:
                        addCar(cn,sc);
                        break;
                    case 3:
                        returnAcar(cn,sc);
                        break;
                    case 4:
                        showRentedUsers(cn);
                        break;
                    case 5 :
                        removeCar(cn,sc);
                        break;
                    case 6:
                        return;
                }
            }

        } catch (SQLException e) {
            System.out.println("error at connection.");
        }
    }
    public static void returnAcar(Connection cn,Scanner sc) {
        System.out.println();
        System.out.println("*-*-*-* welcome to return car section *-*-*- ");
        System.out.println();
        System.out.println("enter returning car id : ");
        String returnCarId = sc.next();
        if(carExistsInUserTable(cn,returnCarId)) {
            System.out.println();
            System.out.println("Thank you!!!!!!!!!!");
            String q = "delete from user_info where id_car = ?;";
            String q1 = "update car_info set available = false where car_id = ?;";

            try {
                PreparedStatement ps = cn.prepareStatement(q);
                PreparedStatement ps1 = cn.prepareStatement(q1);
                ps.setString(1,returnCarId);
                ps1.setString(1,returnCarId);
                int afr = ps.executeUpdate();
                int afr1 = ps1.executeUpdate();
                if(afr > 0 && afr1 > 0) {
                    System.out.println("You car returning succesfully.!!!!!.");
                }else {
                    System.out.println("errrr at ps.");
                }
            }catch (SQLException e) {
                System.out.println("error at delete record.");
            }
        }else{
            System.out.println("this car not found!!!!!!!!!!!");
        }
    }
    public static boolean carExistsInUserTable(Connection cn,String car_id) {
        String query = "select * from user_info where id_car = ?;";
        try {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,car_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (SQLException e) {
            System.out.println("error at car exists!!!!!!!!!");
        }
        return false;
    }
    public static void rentACar(Connection cn,Scanner sc) {
        System.out.println();
        System.out.println("*-*-*- Welcome to car rent section *-*-*-*-");
        System.out.println();
        System.out.println("availables cars are !!!!!!!!!");
        System.out.println();
        int car_count = showCars(cn);
        if(car_count > 0) {
            System.out.print("enter car id you rent a car : ");
            String ci = sc.next();
            if(carExists(cn,ci)) {
                System.out.print("enter days you rent a car : ");
                int rd = sc.nextInt();
                int pdp = getPerDayPrice(cn, ci);
                System.out.println("your rent cost is = " + (pdp * rd));
                System.out.print("renting conform or not....\nenter (y/n) : ");
                char ch = sc.next().charAt(0);
                if (ch == 'y') {
                    System.out.println();
                    System.out.println("thank you renting a car!!!!!!!!!!!");
                    System.out.println();
                    System.out.print("enter your name : ");
                    String name = sc.next();
                    System.out.println("enter car id . your rented car : ");
                    String usercar_id = new String();
                    usercar_id = sc.next();
                    if (carExists(cn, usercar_id)) {
                        String q = "update car_info set available = true where car_id = ?";
                        String q1 = "insert into user_info(name,id_car) values(?,?)";
                        try {
                            PreparedStatement ps = cn.prepareStatement(q);
                            PreparedStatement ps1 = cn.prepareStatement(q1);
                            ps.setString(1, usercar_id);
                            ps1.setString(1, name);
                            ps1.setString(2, usercar_id);
                            int afr = ps.executeUpdate();
                            int afr1 = ps1.executeUpdate();
                            if (afr > 0 && afr1 > 0) {
                                System.out.println("car rented succesfully.\ntake keys from counter 1....");
                                System.out.println("thank You !!");
                            } else {
                                System.out.println("some problem ouccors.");
                            }

                        } catch (SQLException e) {
                            System.out.println("error at ps.");
                        }
                    } else {
                        System.out.println("some error occurs.");
                    }

                } else {
                    System.out.println("Thank You !!!!!!!");
                }
            }else {
                System.out.println("Sorry!!\nbut this car not available in workshop.");
            }
        }else {
            System.out.println("Sorry !!!\ncars are not available!!!!!!");
        }
    }
    public static void addCar(Connection cn,Scanner sc) {
        System.out.println();
        System.out.println("*-*-*- section of adding Car*-*-*- ");
        System.out.println();
        String q = "insert into car_info(car_id,car_name,perdayprice) values(?,?,?);";
        System.out.print("enter your car id = ");
        String car_id = sc.next();
        System.out.println("enter car name : ");
        String name = sc.next();
        System.out.println("enter per day price for your renting car : ");
        int pdp = sc.nextInt();
        try {
            PreparedStatement ps = cn.prepareStatement(q);
            ps.setString(1,car_id);
            ps.setString(2,name);
            ps.setInt(3,pdp);
            int afr = ps.executeUpdate();
            if(afr > 0){
                System.out.println("Car succesfully added to renting......");
            }else {
                System.out.println("some error occurs to car adding.........");
            }
        }catch (SQLException e) {
            System.out.println("error at adding car...");
        }
    }
    public static boolean carExists(Connection cn,String car_id) {
        String query = "select * from car_info where car_id = ?;";
        try {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,car_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (SQLException e) {
            System.out.println("error at car exists!!!!!!!!!");
        }
        return false;
    }
    public static int showCars(Connection cn){

        String query = "select car_id,car_name from car_info where available = false;";
        int car_count = 0;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("+--------+----------+");
            System.out.println(" car_id  | car_name |");
            System.out.println("+--------+----------+");
            while(rs.next()) {
                System.out.printf("|%-8s|"+"%-10s|"+"\n",rs.getString("car_id"),rs.getString("car_name"));
                System.out.println("+--------+----------+");
                car_count++;
            }
        }catch (SQLException e){
            System.out.println("error at create st.");
        }
        if(car_count == 0) {
            return 0;
        }
        return 1;
    }
    public static void showRentedUsers(Connection cn){

        String query = "select * from user_info;";
        int user_count = 0;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("+-----------+---------+-----------+");
            System.out.println("|   name    | user_id | car_id    |");
            System.out.println("+-----------+---------+-----------+");
            while(rs.next()) {

                System.out.printf("| %-10s|"+"%-9s|"+"%-11s|"+"\n",rs.getString("name"),rs.getInt("user_id"),rs.getString("id_car"));
                System.out.println("|-----------|----- ---|-----------|");
                user_count++;
            }
        }catch (SQLException e){
            System.out.println("error at create st.");
        }
        if(user_count == 0) {
            System.out.println("no user's available!!!!!!!!!!!!!!!!");
        }
    }
    public static void removeCar(Connection cn,Scanner sc) {
        System.out.println();
        System.out.println("*-*-*-*- section of removing car from rent *-*-*-*-");
        System.out.println();
        System.out.print("enter your car id = ");
        String car_id = sc.next();
        String query = "delete from car_info where car_id = ?;";
        if(carExists(cn,car_id)) {
            if(carOnUserHand(cn,car_id)) {
                try {
                    PreparedStatement ps = cn.prepareStatement(query);
                    ps.setString(1, car_id);
                    int afr = ps.executeUpdate();
                    if (afr > 0) {
                        System.out.println("car removing succesfully.");
                    } else {
                        System.out.println("some promblem occurs..!!");
                    }
                } catch (SQLException e) {
                    System.out.println("error at delete!!!!!!!!!!!");
                }
            }else {
                System.out.println("Sorry !! but a car is go to renting !!\nafter coming to workshop then your get a car..");
            }
        }else {
            System.out.println("car not found!!!!!!!!!");
        }
    }
    public static boolean carOnUserHand(Connection cn,String car_id) {
        String q = "select * from user_info where id_car = ?;";
        try {
            PreparedStatement ps = cn.prepareStatement(q);
            ps.setString(1,car_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println("error at id car !! ");
        }
        return false;
    }
    public static int getPerDayPrice(Connection cn,String car_id){

        String query = "select perdayprice from car_info where car_id = ?;";
        try {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1,car_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt("perdayprice");
            }
        }catch (SQLException e){
            System.out.println("error at create st.");
        }
        return -0;
    }
}

