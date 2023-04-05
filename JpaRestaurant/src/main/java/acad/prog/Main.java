package acad.prog;

import java.util.Scanner;

import static acad.prog.utils.RestaurantUtils.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("1: add dish");
                System.out.println("2: filter dishes by price");
                System.out.println("3: see all dishes with discount");
                System.out.println("4: select a random menu under 1 kg");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addDish();
                        break;
                    case "2":
                        byPrice();
                        break;
                    case "3":
                        withDiscount();
                        break;
                    case "4":
                        menuUnderKilo();
                        break;
                    default:
                        return;
                }
            }
        } finally {
            sc.close();
            closeEM();
        }
    }
}