package integration;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BigBangInvalidTest {
    private Service service;

    @Before
    public void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void invalidAssignmentId() {
        Tema assignment = new Tema("", "a", 3, 2);

        Throwable exception = assertThrows(ValidationException.class, () -> service.addTema(assignment));
        assertEquals("Numar tema invalid!", exception.getMessage());
    }

    @Test
    public void invalidStudentId() {
        Student student = new Student("", "Ciupe Sergiu", 932, "email@gmail.com");

        Throwable exception = assertThrows(ValidationException.class, () -> service.addStudent(student));
        assertEquals("Id incorect!", exception.getMessage());
    }

    @Test
    public void invalidNota() {
        Tema assignment = new Tema("a", "a", 3, 2);
        Nota nota = new Nota("1", "abc123", "a", 15, LocalDate.now());

        Throwable exception = assertThrows(ValidationException.class, () -> service.addNota(nota, "Feedback"));
        assertEquals("Studentul nu exista!", exception.getMessage());
    }

    @Test
    public void allInvalidAdds() {
        invalidAssignmentId();
        invalidStudentId();
        invalidNota();
    }
}
