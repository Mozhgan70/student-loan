package service;

import dto.RegisterStudentParam;
import entity.Student;

public interface StudentService {
    Student registerStudent(RegisterStudentParam param);
    //String generatePassword();
    Student findByUsernameAndPassword(String username, String password);
    boolean login (String username, String password);
    Student findStudentById(long id);

}
