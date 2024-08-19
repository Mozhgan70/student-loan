package service.impl;

import dto.RegisterStudentParam;
import dto.mapStruct.StudentMapper;
import entity.Student;
import repository.StudentRepository;
import service.StudentService;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    @Override
    public Student registerStudent(RegisterStudentParam param) {
        Student student = studentMapper.toEntity(param);
        if (!studentRepository.existsByNationalCode(param.nationalCode())) {
            return studentRepository.registerStudent(student);

        }
       return null;
    }

    @Override
    public Student findByUsernameAndPassword(String username, String password) {
        return studentRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public boolean login(String username, String password) {
        Student byUsernameAndPassword = findByUsernameAndPassword(username, password);
        if (byUsernameAndPassword != null) {
            return true;
        }
        return false;
    }
}
//    @Override
//    public String generatePassword() {
//        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
//        String digitChars = "0123456789";
//        String specialChars = "@#$%&";
//        String allTypeOfChars = upperCaseChars + lowerCaseChars + digitChars + specialChars;
//        StringBuilder complexPass = new StringBuilder();
//        Random random = new Random();
//        complexPass.append(upperCaseChars.charAt(random.nextInt(upperCaseChars.length())));
//        complexPass.append(lowerCaseChars.charAt(random.nextInt(lowerCaseChars.length())));
//        complexPass.append(digitChars.charAt(random.nextInt(digitChars.length())));
//        complexPass.append(specialChars.charAt(random.nextInt(specialChars.length())));
//        for (int i = 0; i < 4; i++) {
//            complexPass.append(allTypeOfChars.charAt(random.nextInt(allTypeOfChars.length())));
//        }
//
//        return complexPass.toString();
//    }

