package dto.mapStruct;

import dto.RegisterStudentParam;
import entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    //StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    RegisterStudentParam toDTO(Student student);

    Student toEntity(RegisterStudentParam studentDTO);
}
