package People;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends People {
    private final long processNumber;
    private String course;
    private String studentClass;
    private long year;
    private final Map<Disciplina, List<Double>> notasPorDisciplina = new HashMap<>();
    public static final List<Student> listStudent = new ArrayList<>();
    private List<Subject> subjects;

    public Student(String name, String password, long processNumber, String studentClass, String course, long year) {
        this.name = name;
        this.password = password;
        this.processNumber = processNumber;
        this.course = course;
        this.studentClass = studentClass;
        this.year = year;
        subjects = new ArrayList<>();
    }
    public List<Subject> getsubjects() { return subjects; }
    public void addSubject(Subject s) { subjects.add(s); }

    public Subject findSubject(String name) {
        for (Subject s : subjects) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    public long getProcessNumber() {
        return processNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public Map<Disciplina, List<Double>> getNotasPorDisciplina() {
        return notasPorDisciplina;
    }

}
