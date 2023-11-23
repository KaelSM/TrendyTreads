package comp31.a2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.a2.model.entities.Entity1;
import comp31.a2.model.repositories.Entity1Repository;

@Component
public class InitData implements CommandLineRunner {

    Entity1Repository entity1Repository;

    public InitData(Entity1Repository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    @Override
    public void run(String... args) throws Exception {

        Integer nItems = Integer.parseInt(args[0]);
        for (int i = 0; i < nItems; i++) {
            entity1Repository.save(new Entity1("Entity1 " + i));
            System.out.println("---- Created Entity");

        }
    }

}
