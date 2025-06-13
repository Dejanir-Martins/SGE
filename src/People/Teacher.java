package People;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends People {
    private final long id;
    private String subject;
    public static final List<Teacher> listTeachers = new ArrayList<>();

    public Teacher(String name, long id, String password, String subject) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
