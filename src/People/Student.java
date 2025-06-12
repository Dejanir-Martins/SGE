package People;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Core.*;

public class Student extends People{
    final SGEService service = new SGEService();

    private String Name;
    private String PIN;
    private final long ProcessNumber;
    public String Class;
    public String Course;
    public long Year;
    final ArrayList<String> correio;
    public double[] notas = new double[3];
    public static final ArrayList<Student> listStudent = new ArrayList<>();
    private final static Map<Disciplina, List<Double>> notasPorDisciplina = new HashMap<>();


    public Student(String name,
                   String pass,
                   long process,
                   String aClass,
                   String curs,
                   long year) {
        this.Name = name;
        this.Course = curs;
        this.ProcessNumber = process;
        this.PIN = pass;
        this.Year = year;
        this.Class = aClass;
        correio = new ArrayList<>();
        service.inicializarDisciplinas();
    }

    public long getProcessNumber() {
        return ProcessNumber;

    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public static Map<Disciplina, List<Double>> getNotasPorDisciplina() {
        return notasPorDisciplina;
    }
}