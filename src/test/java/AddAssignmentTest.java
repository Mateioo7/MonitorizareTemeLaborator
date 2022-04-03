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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AddAssignmentTest {
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
    public void invalidId() {
        Tema assignment = new Tema("", "a", 3, 2);

        Throwable exception = assertThrows(ValidationException.class, () -> service.addTema(assignment));
        assertEquals("Numar tema invalid!", exception.getMessage());
    }

    @Test
    public void invalidDescription() {
        Tema assignment = new Tema("9000", "", 3, 2);

        Throwable exception = assertThrows(ValidationException.class, () -> service.addTema(assignment));
        assertEquals("Descriere invalida!", exception.getMessage());
    }

    @Test
    public void invalidDeadline() {
        Tema assignment = new Tema("9000", "a", 0, 2);

        Throwable exception = assertThrows(ValidationException.class, () -> service.addTema(assignment));
        assertEquals("Deadlineul trebuie sa fie intre 1-14.", exception.getMessage());
    }

    @Test
    public void invalidTurnIn() {
        Tema assignment = new Tema("9000", "a", 3, 0);

        Throwable exception = assertThrows(ValidationException.class, () -> service.addTema(assignment));
        assertEquals("Saptamana primirii trebuie sa fie intre 1-14.", exception.getMessage());
    }
}
