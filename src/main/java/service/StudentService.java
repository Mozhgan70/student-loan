package service;

import dto.LoginDto;
import dto.RegisterStudentDto;
import entity.Student;

public interface StudentService {
    Student registerStudent(RegisterStudentDto param);
    //String generatePassword();
    Student findByUsernameAndPassword(String username, String password);
    boolean login (LoginDto loginDto);
    Student findStudentById(long id);

}
