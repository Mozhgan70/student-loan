package service;

import dto.RegisterStudentDto;
import entity.Student;

public interface StudentService {
    Student registerStudent(RegisterStudentDto param);
    //String generatePassword();
    Student findByUsernameAndPassword(String username, String password);
    boolean login (String username, String password);
    Student findStudentById(long id);

}
