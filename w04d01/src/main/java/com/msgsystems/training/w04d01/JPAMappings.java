package com.msgsystems.training.w04d01;

import com.msgsystems.training.w04d01.controller.ProductController;
import com.msgsystems.training.w04d01.data.model.Manager;
import com.msgsystems.training.w04d01.data.model.Product;
import com.msgsystems.training.w04d01.data.model.Store;
import com.msgsystems.training.w04d01.data.model.StoreSection;
import com.msgsystems.training.w04d01.data.repository.ProductRepository;
import com.msgsystems.training.w04d01.service.ProductService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings({"unused", "unchecked"})
public class JPAMappings {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static ProductRepository productRepository;
    private static ProductService productService;
    private static ProductController productController;

    static {
        // disable Hibernate's logging messages
        //java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        entityManagerFactory = Persistence.createEntityManagerFactory("msg_training");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static void main(final String[] args) {
        long now = System.currentTimeMillis();

        productRepository = new ProductRepository(entityManager);
        productService = new ProductService(productRepository);
        productController = new ProductController(productService);

        // readProduct(8);

        // saving a new Product, using the IDENTITY PK generation
        // saveNewProduct();

        //StoreSection storeSection = entityManager.find(StoreSection.class, 1);
        //System.out.println(storeSection.getId());
        //storeSection.getProducts().forEach(product -> System.out.println(product.getName()));

        //Product product = entityManager.find(Product.class, 1);
        //System.out.println(product.getName());

        //storeSection.getProducts().forEach(product -> System.out.println(product.getName()));

        //saveSectionAndProducts();

        //getProductsFromStore();

        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        StoreSection storeSection = entityManager.find(StoreSection.class, 1);
        final Set<Product> products = storeSection.getProducts();

        System.out.println("Before - " + products.size());
        products.removeIf(product -> product.getName().contains("Altceva"));
        System.out.println("After - " + products.size());

        entityManager.merge(storeSection);

        transaction.commit();

        /*
        try {
            displayStoresManagedByManager(2);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println();

        try {
            displayStoreManagers(3);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
        */

        closeEntityManagerObjects();

        System.out.println(System.currentTimeMillis() - now + " ms");
    }

    private static void getProductsFromStore() {
        final Query query = entityManager.createNamedQuery("Product.bySection");
        query.setParameter("storeId", 3);

        final List<Product> products = (List<Product>) query.getResultList();
        products.forEach(product -> System.out.println(product.getId() + ", " + product.getName()));
    }

    private static void saveSectionAndProducts() {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        StoreSection storeSection = new StoreSection();

        final Store store = entityManager.find(Store.class, 3);
        storeSection.setStore(store);
        storeSection.setSectionName("Baeutura :)");

        Product product = new Product();
        product.setStoreSection(storeSection);
        product.setName("Whiskey");

        Product carte = new Product();
        carte.setName("Beer");
        carte.setStoreSection(storeSection);

        Set<Product> products = new HashSet<>(2);
        products.add(product);
        products.add(carte);
        storeSection.setProducts(products);

        store.getStoreSections().add(storeSection);

        entityManager.persist(storeSection);

        transaction.commit();
    }

    private static void readProduct(final int id) {
        String product = productController.readProduct(id);
        System.out.println(product);
    }

    private static void saveNewProduct() {
        final Product product = new Product();
        product.setName("Razer Blade");
        product.setStoreSection(new StoreSection() {{
            setId(1);
        }});

        productController.saveProduct(product);
    }

    private static void displayStoresManagedByManager(final int id) {
        final Manager manager = Optional.ofNullable(entityManager.find(Manager.class, id))
                                        .orElseThrow(() -> new EntityNotFoundException("There is no manager with the id " + id));

        final Set<Store> stores = manager.getStores();

        System.out.println("The manager '" + manager.getName() + "' manages " + stores.size() + " stores:");
        stores.stream()
              .sorted((first, second) -> first.getName().compareTo(second.getName()))
              .forEach(store -> System.out.println("\t" + store.getName()));
    }

    private static void displayStoreManagers(final int id) {
        final Store store = Optional.ofNullable(entityManager.find(Store.class, id))
                                    .orElseThrow(() -> new EntityNotFoundException("There is no store with the id " + id));

        final Set<Manager> storeManagers = store.getStoreManagers();

        System.out.println("The store '" + store.getName() + "' is managed by " + storeManagers.size() + " managers:");
        storeManagers.stream()
                     .sorted((first, second) -> first.getName().compareTo(second.getName()))
                     .forEach(manager -> System.out.println("\t" + manager.getName()));
    }

    private static void closeEntityManagerObjects() {
        Optional.ofNullable(entityManager).ifPresent(EntityManager::close);
        Optional.ofNullable(entityManagerFactory).ifPresent(EntityManagerFactory::close);
    }
}
