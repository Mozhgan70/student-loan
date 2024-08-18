package repository.impl;

import dto.RegisterStudentParam;
import entity.Student;
import jakarta.persistence.EntityManager;
import repository.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {

private final EntityManager entityManager;

    public StudentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Student registerStudent(Student student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        return student;
    }
}
