package repository.impl;

import entity.Spouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.SpouseRepository;

public class SpouseRepositoryImpl implements SpouseRepository {
    private final EntityManager entityManager;




    public SpouseRepositoryImpl(EntityManager entityManager) {


        this.entityManager = entityManager;
    }



    @Override
    public Spouse save(Spouse spouse) {
        entityManager.getTransaction().begin();
        entityManager.persist(spouse);
        entityManager.getTransaction().commit();
        return spouse;
    }
}
