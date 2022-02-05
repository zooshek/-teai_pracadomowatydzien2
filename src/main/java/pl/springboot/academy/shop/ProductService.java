package pl.springboot.academy.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Value("${tax}")
    private Double tax;

    @Value("${discount}")
    private Double discount;

    private List<Product> productList = new ArrayList<>();

    public ProductService() {

        Product product1 = new Product("Czapka", getRandomPrice());
        productList.add(product1);
        Product product2 = new Product("Szalik", getRandomPrice());
        productList.add(product2);
        Product product3 = new Product("Rękawiczki", getRandomPrice());
        productList.add(product3);
        Product product4 = new Product("Buty",getRandomPrice());
        productList.add(product4);
        Product product5 = new Product("Skarpetki",getRandomPrice());
        productList.add(product5);
    }

    private Double getRandomPrice(){
        Random rand = new Random();
        Integer min = 50;
        Integer max = 300;

        return min + (max - min) * rand.nextDouble();
    }

    public List<Product> showProductsList(){
        return productList;
    }


    public Double getAmount() {

        return  productList.stream()
                .map(Product::getPrice)
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public Double getTax(){
        return getAmount()* tax;
    }

    public Double getDiscount(){
        return getAmount()* discount;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void showTotalSum(){

        productList.forEach(System.out::println);
        System.out.println("START Sum: "+ getAmount().toString());
        System.out.println("PLUS Tax: "+ getTax().toString());
        System.out.println("PREMIUM Discount: "+ getDiscount().toString());

    }

}
