package service;

import dto.RegisterStudentParam;
import entity.Student;

public interface StudentService {
    Student registerStudent(RegisterStudentParam param);
    //String generatePassword();
}
