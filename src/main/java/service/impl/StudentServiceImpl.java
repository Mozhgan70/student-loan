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
//        Student student=studentMapper.toEntity(param);
//        studentRepository.registerStudent(student);
        return null;
    }
}
