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

public class TopDownTest {
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

    public void addValidStudent() {
        Student student = new Student("145", "Ciupe Sergiuu", 932, "emailsergiu@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    public void addValidAssignment() {
        Tema assignment = new Tema("145", "a", 3, 2);
        assertEquals(assignment, service.addTema(assignment));
    }

    public void addValidGrade() {
        Nota grade = new Nota("1", "145", "145", 9, LocalDate.of(2018, 11, 4));
        assertEquals(grade.getNota(), service.addNota(grade, "Feedback"), 0.01);
    }

    @Test
    public void topDown_addStudentIntegration() {
        addValidStudent();
    }

    @Test
    public void topDown_addAssignmentIntegration() {
        addValidStudent();
        addValidAssignment();
    }

    @Test
    public void topDown_addGradeIntegration() {
        addValidStudent();
        addValidAssignment();
        addValidGrade();
    }
}
