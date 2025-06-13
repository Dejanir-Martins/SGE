package People;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends People {
    private final long id;
    private String disciplina;
    public static final List<Teacher> listTeachers = new ArrayList<>();

    public Teacher(String name, long id, String password, String disciplina) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.disciplina = disciplina;
    }

    public long getId() {
        return id;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
}
