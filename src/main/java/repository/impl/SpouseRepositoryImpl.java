package repository.impl;

import entity.Spouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.SpouseRepository;

public class SpouseRepositoryImpl implements SpouseRepository {

    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public SpouseRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    @Override
    public Spouse save(Spouse spouse) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(spouse);
        getEntityManager().getTransaction().commit();
        return spouse;
    }
}
