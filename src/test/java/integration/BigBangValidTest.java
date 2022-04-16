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

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BigBangValidTest {
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
    public void validAssignment() {
        Tema assignment = new Tema("1", "a", 3, 2);

        assertEquals(assignment, service.addTema(assignment));
    }

    @Test
    public void validStudent() {
        Student student = new Student("1", "Ciupe Sergiu", 932, "email@gmail.com");

        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void validNota() {
        Tema assignment = new Tema("145", "a", 3, 2);
        Student student = new Student("145", "Ciupe Sergiu", 932, "email@gmail.com");
        Nota nota = new Nota("1", "145", "145", 9, LocalDate.of(2018, 11, 4));

        assertEquals(nota.getNota(), service.addNota(nota, "Feedback"), 0.01);
    }

    @Test
    public void allInvalidAdds() {
        validAssignment();
        validStudent();
        validNota();
    }
}
