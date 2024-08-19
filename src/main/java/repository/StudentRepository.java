package repository;

import entity.Student;

public interface StudentRepository {
    Student registerStudent(Student student);
    boolean existsByNationalCode(String userName);
    Student login(String userName, String password);
    Student findByUsernameAndPassword(String username, String password);
}
