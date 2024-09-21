package dto.mapStruct;

import dto.RegisterStudentDto;
import entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    RegisterStudentDto toDTO(Student student);

    Student toEntity(RegisterStudentDto studentDTO);
}
