package service.impl;

import dto.RegisterStudentParam;
import dto.mapStruct.StudentMapper;
import entity.Student;
import repository.StudentRepository;
import service.StudentService;
import util.UserSession;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserSession userSession;
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, UserSession userSession) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userSession = userSession;
    }


    @Override
    public Student registerStudent(RegisterStudentParam param) {
     try{
        Student student = studentMapper.toEntity(param);
        System.out.println(student.getResidenceCity());
        if (!studentRepository.existsByNationalCode(param.nationalCode())) {
            return studentRepository.registerStudent(student);

        }

    }
    catch(Exception e){
        e.getMessage();
    }
    return null;}

    @Override
    public Student findByUsernameAndPassword(String username, String password) {

        Student byUsernameAndPassword = studentRepository.findByUsernameAndPassword(username, password);
        if (byUsernameAndPassword != null) {
            return byUsernameAndPassword;
        }
        return null;
    }



    @Override
    public boolean login(String username, String password) {
        Student student = studentRepository.findByUsernameAndPassword(username, password);
        if (student != null) {
            userSession.setTokenId(student.getId());
            userSession.setTokenName(student.getUserName());
            userSession.setEducationGrade(student.getEducationGrade());
            userSession.setMaritalStatus(student.getMaritalStatus());
            userSession.setUniversityType(student.getUniversityType());
            userSession.setCity(student.getResidenceCity());
            return true;
        }
        return false;

    }

    @Override
    public Student findStudentById(long id) {
        return studentRepository.findStudentById(id);
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

