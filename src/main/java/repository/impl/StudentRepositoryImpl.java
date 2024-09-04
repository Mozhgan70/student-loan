package repository.impl;

import entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.StudentRepository;

import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private final EntityManager entityManager;




    public StudentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Student registerStudent(Student student) {
        entityManager.getTransaction().begin();
        Student mergedStudent = entityManager.merge(student);
        entityManager.getTransaction().commit();
        return mergedStudent;
    }

    @Override
    public Student findStudentById(long id) {
        entityManager.getTransaction().begin();
        Student student = entityManager.find(Student.class, id);
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
        List<Student> resultList = entityManager.createQuery(hql, Student.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        if(resultList!=null && resultList.size()>0) {
            return resultList.get(0);
        }
        return null;
    }

}
