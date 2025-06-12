package Utils;

import java.util.*;

enum Disciplina {
    MATEMATICA, FISICA, QUIMICA, HISTORIA, GEOGRAFIA;
}

class Aluno {
    private String nome;
    private Map<Disciplina, List<Double>> notasPorDisciplina;

    public Aluno(String nome) {
        this.nome = nome;
        this.notasPorDisciplina = new HashMap<>();
        inicializarDisciplinas();
    }

    private void inicializarDisciplinas() {
        for (Disciplina disciplina : Disciplina.values()) {
            notasPorDisciplina.put(disciplina, new ArrayList<>());
        }
    }

    public void adicionarNota(Disciplina disciplina, double nota) {
        List<Double> notas = notasPorDisciplina.get(disciplina);
        if (notas.size() < 3) {
            notas.add(nota);
            System.out.println("✅ Nota " + nota + " adicionada em " + disciplina);
        } else {
            System.out.println("❌ '" + disciplina + "' já tem 3 notas registradas.");
        }
    }

    public void atualizarNota(Disciplina disciplina, int indice, double novaNota) {
        List<Double> notas = notasPorDisciplina.get(disciplina);
        if (indice >= 0 && indice < notas.size()) {
            notas.set(indice, novaNota);
            System.out.println("🔄 Nota atualizada em " + disciplina + ": " + novaNota);
        } else {
            System.out.println("⚠️ Índice inválido! Escolha entre 0, 1 ou 2.");
        }
    }

    public double calcularMedia(Disciplina disciplina) {
        List<Double> notas = notasPorDisciplina.get(disciplina);
        return notas.size() == 3 ? notas.stream().mapToDouble(Double::doubleValue).average().orElse(0) : -1;
    }

    public void mostrarMedias() {
        System.out.println("📊 Médias das disciplinas de " + nome + ":");
        for (Disciplina disciplina : Disciplina.values()) {
            double media = calcularMedia(disciplina);
            System.out.println("📚 " + disciplina + " → Média: " + (media >= 0 ? media : "Ainda faltam notas."));
        }
    }
}

public class Sistema {
    public static void main(String[] args) {
        Aluno aluno = new Aluno("Carlos");

        aluno.adicionarNota(Disciplina.MATEMATICA, 8.5);
        aluno.adicionarNota(Disciplina.MATEMATICA, 7.2);
        aluno.adicionarNota(Disciplina.MATEMATICA, 9.0);



        aluno.atualizarNota(Disciplina.MATEMATICA, 1, 8.0); // Atualiza a segunda nota

        aluno.mostrarMedias();
    }
}