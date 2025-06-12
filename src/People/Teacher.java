package People;

import java.util.ArrayList;

public class Teacher {
    private String name;
    private final long id;
    private ArrayList<String> email;
    private String password;
    public String subject;
    private final ArrayList<Teacher> listTeachers = new ArrayList<>();

    public Teacher(String name, long id, String password, String subject) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Teacher> getListTeachers() {
        return listTeachers;
    }
}
