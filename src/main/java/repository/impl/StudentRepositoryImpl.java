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

    @Override
    public boolean existsByNationalCode(String nationalCode) {
        String hql = "SELECT count(s.id) > 0 FROM Student s WHERE s.nationalCode = :nationalCode";
        return entityManager.createQuery(hql, Boolean.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
    }

    @Override
    public Student login(String userName, String password) {
        return null;
    }

    @Override
    public Student findByUsernameAndPassword(String username, String password) {
        String hql = "SELECT s FROM Student s WHERE s.userName = :username and s.password = :password";
        return entityManager.createQuery(hql, Student.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }

}
