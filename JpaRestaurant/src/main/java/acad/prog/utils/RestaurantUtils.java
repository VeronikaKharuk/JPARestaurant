package acad.prog.utils;

import acad.prog.entities.Dish;

import javax.persistence.*;
import java.util.*;

public class RestaurantUtils {
    static EntityManagerFactory emf;
    static EntityManager em;


    public static void openEM() {
        emf = Persistence.createEntityManagerFactory("JPATest");
        em = emf.createEntityManager();
    }

    public static void closeEM() {
        em.close();
        emf.close();
    }

    public static void addDish() {
        Scanner sc = new Scanner(System.in);
        openEM();
        System.out.println("Enter title: ");
        String title = sc.nextLine();
        System.out.println("Enter price: ");
        String priceS = sc.nextLine();
        double price = Double.parseDouble(priceS);
        System.out.println("Enter weight in grams: ");
        String weightS = sc.nextLine();
        int weight = Integer.parseInt(weightS);
        System.out.println("Enter discount \"yes\" or \"no\": ");
        String discountS = sc.nextLine();
        String discountStr;
        if (discountS.equals("yes".toLowerCase())) {
            discountStr = "true";
        } else {
            discountStr = "false";
        }
        boolean discount = Boolean.parseBoolean(discountStr);

        em.getTransaction().begin();
        try {
            Dish a = new Dish(title, price, weight, discount);
            em.persist(a);
            em.getTransaction().commit();
            System.out.println("The dish was entered under id " + a.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    public static void byPrice() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Min price in UAH: ");
        String minPriceS = sc.nextLine();
        double minPrice = Double.parseDouble(minPriceS);
        System.out.print("Max price in UAH: ");
        String maxPriceS = sc.nextLine();
        double maxPrice = Double.parseDouble(maxPriceS);

        openEM();
        TypedQuery query = em.createQuery("SELECT a FROM Dish a WHERE a.price > :minPrice AND a.price < :maxPrice", Dish.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);

        List<Dish> aList = query.getResultList();

        for (Dish a : aList) {
            System.out.println(a);
        }
    }

    public static void withDiscount() {
        openEM();
        TypedQuery query = em.createQuery("SELECT a FROM Dish a WHERE a.discount = true", Dish.class);
        List<Dish> aList = query.getResultList();

        for (Dish a : aList)
            System.out.println(a);
    }

    public static void menuUnderKilo() {
        final int maxWeight = 1000;

        openEM();
        TypedQuery query = em.createQuery("SELECT a FROM Dish a", Dish.class);
        List<Dish> list = query.getResultList();
        Set<Dish> menuSet = new HashSet<>();
        Random rnd = new Random();

        int weight;
        int totalWeight = 0;

        System.out.println("The menu of dishes under 1 kg:");
        while (totalWeight <= maxWeight) {
            Dish randomDish = list.get(rnd.nextInt(list.size()));
            weight = randomDish.getWeight();
            if ((totalWeight + weight) < maxWeight) {
                menuSet.add(randomDish);
            }
            totalWeight += weight;
        }

        totalWeight = 0;
        for (Dish a : menuSet) {
            totalWeight += a.getWeight();
        }

        for (Dish d : list) {
            if (!menuSet.contains(d) && d.getWeight() < (1000 - totalWeight)) {
                menuSet.add(d);
                totalWeight += d.getWeight();
            }
        }

        for (Dish a : menuSet)
            System.out.println(a);
        System.out.println("The total weight: " + totalWeight + " g.");
    }

}

