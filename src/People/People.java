package People;

import java.util.ArrayList;
import java.util.List;

public abstract class People {
    protected String name;
    protected String password;
    private static final List<String> inbox = new ArrayList<>();

    public List<String> getInbox() {
        return inbox;
    }

    public enum Disciplina {
        MATEMATICA, FISICA, QUIMICA, HISTORIA, GEOGRAFIA
    }

    public void sendMessage(People recipient, String message) {
        inbox.add("De " + this.name + ": " + message);
    }

    public void viewInbox() {
        if (inbox.isEmpty()) {
            System.out.println("📭 Sua caixa de mensagens está vazia.");
        } else {
            System.out.println("📬 Caixa de entrada:");
            inbox.forEach(System.out::println);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private int mensagensLidas = 0;

    public int getMensagensLidas() {
        return mensagensLidas;
    }

    public void setMensagensLidas(int mensagensLidas) {
        this.mensagensLidas = mensagensLidas;
    }

    public int getMensagensNaoLidas() {
        return inbox.size() - mensagensLidas;
    }

}

