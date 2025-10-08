package service;

import dao.StudentDAO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.Student;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService {
	StudentDAO studentDAO = new StudentDAO();
	
	public Student getStudentProfile(int userId) {
		return studentDAO.findByUserId(userId);
	}
	
	public boolean saveStudent(Student student) {
		return studentDAO.saveStudent(student);
	}
}
