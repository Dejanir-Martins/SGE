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
        FISICA, GEOGRAFIA, HISTORIA, MATEMATICA, QUIMICA

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
    private int message = 0;


    public void setMessage(int message) {
        this.message = message;
    }

    public int getMensagensNaoLidas() {
        return inbox.size() - message;
    }
    public static class Subject {
            private final String name;
            private double p1;
            private double p2;
            private double MAC;
            private double media;

            public Subject(String name, double p1, double p2, double MAC) {
                this.name = name;
                this.p1 = p1;
                this.p2 = p2;
                this.MAC = MAC;
                this.media = calcularMedia();
            }

            private double calcularMedia() {
                return (p1 + p2 + MAC) / 3;
            }

            public String getName() {
                return name;
            }

            public double getP1() {
                return p1;
            }

            public double getP2() {
                return p2;
            }

            public double getMAC() {
                return MAC;
            }

            public double getMedia() {
                return media;
            }

            public void setNotas(double p1, double p2, double MAC) {
                this.p1 = p1;
                this.p2 = p2;
                this.MAC = MAC;
                this.media = calcularMedia();
            }
        }


    }


