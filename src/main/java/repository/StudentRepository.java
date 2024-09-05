package repository;

import entity.Student;

public interface StudentRepository {
    Student registerStudent(Student student);
    Student findStudentById(long id);
    boolean existsByNationalCode(String userName);
    Student login(String userName, String password);
    Student findByUsernameAndPassword(String username, String password);
    Student findStudentByNatCode(String natCode);

}
