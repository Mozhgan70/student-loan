package service.impl;

import dto.LoginDto;
import dto.RegisterStudentDto;
import dto.mapStruct.StudentMapper;
import entity.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import repository.StudentRepository;
import service.StudentService;
import util.UserSession;

import java.util.Set;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserSession userSession;
    private Validator validator;


    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, UserSession userSession) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userSession = userSession;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    @Override
    public Student registerStudent(RegisterStudentDto param) {
        Set<ConstraintViolation<RegisterStudentDto>> violations = validator.validate(param);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterStudentDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            return null;
        }


        try {
            Student student = studentMapper.toEntity(param);
           // if (!studentRepository.existsByNationalCode(param.nationalCode())) {
                return studentRepository.registerStudent(student);

//            } else {
//                System.out.println("Student with the same national code already exists.");
//                return null;
//            }

        } catch (Exception e) {
            System.out.println("An error occurred while registering the student: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Student findByUsernameAndPassword(String username, String password) {
        try {
            Student byUsernameAndPassword = studentRepository.findByUsernameAndPassword(username, password);
            if (byUsernameAndPassword != null) {
                return byUsernameAndPassword;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public boolean login(LoginDto loginDto) {
//        Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
//
//        if (!violations.isEmpty()) {
//            for (ConstraintViolation<LoginDto> violation : violations) {
//                System.out.println(violation.getMessage());
//            }
//            return false;
//        }


        try {
            Student student = studentRepository.findByUsernameAndPassword(loginDto.userName(), loginDto.password());

            if (student != null) {
                userSession.setTokenId(student.getId());
                userSession.setTokenName(student.getUserName());
                userSession.setEducationGrade(student.getEducationGrade());
                userSession.setEntryYear(student.getEntryYear());
                userSession.setIsDormitoryResident(student.isDormitoryResident());
                userSession.setMaritalStatus(student.getMaritalStatus());
                userSession.setUniversityType(student.getUniversityType());
                userSession.setCity(student.getResidenceCity());
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred while registering the student: " + e.getMessage());
            return false;
        }

    }

    @Override
    public Student findStudentById(long id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public Student findStudentByNatCode(String natCode) {
        return studentRepository.findStudentByNatCode(natCode);
    }
}


