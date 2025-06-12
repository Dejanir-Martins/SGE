package People;
import java.util.ArrayList;
public class Student {

    private String Name;
    private String PIN;
    private final long ProcessNumber;
    public String Class;
    public String Course;
    public long Year;
    final ArrayList<String> correio;
    public double[] notas = new double[3];
    private final ArrayList<Student> listStudent = new ArrayList<>();


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

    public ArrayList<Student> getListStudent() {
        return listStudent;
    }
}