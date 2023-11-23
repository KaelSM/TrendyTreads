package comp31.a2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.a2.model.entities.Order;
import comp31.a2.model.repositories.OrderRepo;

@Component
public class InitData implements CommandLineRunner {

    OrderRepo orderRepo;

    public InitData(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        Integer nItems = Integer.parseInt(args[0]);
        for (int i = 0; i < nItems; i++) {
            // orderRepo.save(new Order("Entity1 " + i, i, i, null, null, null, null, null,
            // null));

            orderRepo.save(new Order(1, 1, "2021-10-10", 3, "1234 Main St", "Toronto", "Canada", "5698358", "John Doe", "10/29"));
            orderRepo.save(new Order(2, 1, "2021-11-11", 1, "5678 Main St", "Toronto", "Canada", "5698358", "John Doe", "10/29"));
            orderRepo.save(new Order(3, 2, "2021-12-06", 1, "9734 Nunez Prairie", "Rio Branco", "Brazil", "3652599", "Joy Morrow", "06/26"));
            orderRepo.save(new Order(4, 3, "2020-11-21", 2, "10249 Jennifer Squares", "Suzuka City", "Japan", "3652125", "Ana Day", "01/25"));
            orderRepo.save(new Order(5, 4, "2022-06-01", 7, "4547 Maldonado Ports", "Des Moines", "United States", "8625431", "Anthony Martin", "12/29"));

            System.out.println("---- Created Entity");

        }
    }

}
