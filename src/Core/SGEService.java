package Core;

import Main.Main;
import People.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Utils.Validation;

import static Utils.Colors.*;

public class SGEService extends People{

    public static int gerarID() {
            final int MIN_ID = 15000;
            final int MAX_ID = 16000;
            final Random random = new Random();

            int newID;
            do {
                newID = random.nextInt(MAX_ID - MIN_ID + 1) + MIN_ID;
            } while (idExist(newID)); // Só aceita IDs que não existam

            return newID;
        }
        private static boolean idExist(int id) {
            return Student.listStudent.stream().anyMatch(student -> student.getProcessNumber() == id) ||
                    Teacher.listTeachers.stream().anyMatch(teacher -> teacher.getId() == id);
        }


    public static void Update(Student aluno, Scanner scan, String identify, Main system) {
        String option;
        do {
            system.show(false, true, false, identify);
            System.out.println("ESCOLHA UMA OPÇÃO DE ATUALIZAÇÃO:");
            System.out.println("1 - NOME");
            System.out.println("3 - CLASSE");
            System.out.println("4 - CURSO");
            System.out.println("5 - SENHA");
            System.out.println("6 - ELIMINAR");
            System.out.println("0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine();

            String result;
            switch (option) {
                case "6" ->{
                    System.out.println("1 - ELIMINAR TODOS OS ALUNOS");
                    System.out.println("2 - APENAS ESTE ALUNO");
                    System.out.println("3 - VOLTAR");
                    System.out.print("DECISÃO: ");
                    String delChoice = scan.nextLine();
                    if (delChoice.equals("1")) {
                        System.out.print("TEM CERTEZA QUE DESEJA ELIMINAR TODOS OS ALUNOS? (S/N): ");
                        String confirm = scan.nextLine();
                        if (confirm.equalsIgnoreCase("s")) {
                            system.delete(false,true);
                            System.out.println("ALUNOS ELIMINADO COM SUCESSO!");
                        } else {
                            System.out.println("ACAO CANCELADA");
                        }
                    } else if (delChoice.equals("2")) {
                        System.out.print("TEM CERTEZA QUE DESEJA ELIMINAR ESTE ALUNO? (S/N): ");
                        String confirm = scan.nextLine();
                        if (confirm.equalsIgnoreCase("s")) {
                            system.delete(false,null,aluno);
                            System.out.println("ALUNO ELIMINADO COM SUCESSO!");
                        } else {
                            System.out.println("ACAO CANCELADA");
                        }
                    }
                }
                case "1" -> {
                    System.out.print("DIGITE O NOVO NOME: ");
                    String nome = scan.nextLine();
                    result = system.update(aluno,true,false,false,false,nome);
                    System.out.println(result);
                }
                case "3" -> {
                    System.out.print("DIGITE A NOVA CLASSE: ");
                    String classe = scan.nextLine();
                    result = system.update(aluno, false, false,true,false, classe);
                    System.out.println(result);
                }
                case "4" -> {
                    System.out.print("DIGITE A NOVA CURSO: ");
                    String curso = scan.nextLine();
                    result = system.update(aluno, false, true,false, false, curso);
                    System.out.println(result);
                }
                case "5" ->{
                    System.out.println("DIGITE A NOVA SENHA");
                    String senha = scan.nextLine();
                    result = system.update(aluno,false,false,false,true,senha);
                    System.out.println(result);
                }
                case "0" -> System.out.println("VOLTANDO AO MENU ANTERIOR...");
                default -> System.out.println(RED + "OPÇÃO INVÁLIDA." + RESET);
            }
        } while (!option.equals("0"));
    }
    public static void Update(Teacher teacher, Scanner scan, String identify, Main system) {
        String option;
        do {
            system.show(false, true, false, identify);
            System.out.println("ESCOLHA UMA OPÇÃO DE ATUALIZAÇÃO:");
            System.out.println("1 - NOME");
            System.out.println("2 - DISCIPLINA");
            System.out.println("3 - SENHA");
            System.out.println("4 - ELIMINAR");
            System.out.println("0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine().trim();

            String result;
            switch (option) {
                case "4" ->{
                    System.out.println("1 - ELIMINAR TODOS OS PROFESSORES");
                    System.out.println("2 - APENAS ESTE PROF");
                    System.out.println("3 - VOLTAR");
                    System.out.print("DECISÃO: ");
                    String delChoice = scan.nextLine();
                    if (delChoice.equals("1")) {
                        System.out.print("TEM CERTEZA QUE DESEJA ELIMINAR TODOS OS PROFESSORES? (S/N): ");
                        String confirm = scan.nextLine();
                        if (confirm.equalsIgnoreCase("s")) {
                            system.delete(true,false);
                            System.out.println("PROFESSORES ELIMINADOS COM SUCESSO!");
                        } else {
                            System.out.println("AÇÃO CANCELADA");
                        }
                    } else if (delChoice.equals("2")) {
                        System.out.print("TEM CERTEZA QUE DESEJA ELIMINAR ESTE PROF? (S/N): ");
                        String confirm = scan.nextLine();
                        if (confirm.equalsIgnoreCase("s")) {
                            system.delete(true,teacher,null);
                            System.out.println("ALUNO ELIMINADO COM SUCESSO!");
                        } else {
                            System.out.println("ACAO CANCELADA");
                        }
                    }
                }
                case "1" -> {
                    System.out.print("DIGITE O NOVO NOME: ");
                    String nome = scan.nextLine();
                    result = system.update(teacher,true,false,false,nome);
                    System.out.println(result);
                }
                case "2" -> {
                    System.out.print("DIGITE A NOVA DISCIPLINA: ");
                    String classe = scan.nextLine();
                    result = system.update(teacher, false,true,false, classe);
                    System.out.println(result);
                }
                case "3" -> {
                    System.out.print("DIGITE A NOVA SENHA: ");
                    String senha = scan.nextLine();
                    result = system.update(teacher,false,false,true, senha);
                    System.out.println(result);
                }
                case "0" -> System.out.println("VOLTANDO AO MENU ANTERIOR...");
                default -> System.out.println(RED + "OPÇÃO INVÁLIDA." + RESET);
            }
        } while (!option.equals("0"));

    }

    public void inicializarDisciplinas() {
        for (Disciplina disciplina : Disciplina.values()) {
            Student.getNotasPorDisciplina().put(disciplina, new ArrayList<>());
        }
    }

   /* public void adicionarNota(Disciplina disciplina, double nota) {
        List<Double> notas = student.getNotasPorDisciplina().get(disciplina);
        if (notas.size() < 3) {
            notas.add(nota);
            System.out.println("✅ Nota " + nota + " adicionada em " + disciplina);
        } else {
            System.out.println("❌ '" + disciplina + "' já tem 3 notas registradas.");
        }
    }*/

    public static void atualizarNota(Disciplina disciplina, int indice, double novaNota, Student student) {
        List<Double> notas = student.getNotasPorDisciplina().get(disciplina);
        if (indice >= 0 && indice < notas.size()) {
            notas.set(indice, novaNota);
            System.out.println("🔄 Nota atualizada em " + disciplina + ": " + novaNota);
        } else {
            System.out.println("⚠️ Índice inválido! Escolha entre 0, 1 ou 2.");
        }
    }

    public double calcularMedia(Disciplina disciplina) {
        List<Double> notas = Student.getNotasPorDisciplina().get(disciplina);
        return notas.size() == 3 ? notas.stream().mapToDouble(Double::doubleValue).average().orElse(0) : -1;
    }

    public void mostrarMedias(Student student) {
        System.out.println("📊 Médias das disciplinas de " + student.getName() + ":");
        for (Disciplina disciplina : Disciplina.values()) {
            double media = calcularMedia(disciplina);
            System.out.println("📚 " + disciplina + " → Média: " + (media >= 0 ? media : "Ainda faltam notas."));
        }
    }
    public static void lancarNota(Disciplina disciplina,Scanner scan, Student student) {
        String option;
        do {
            System.out.println("1 - P1\n2- P2\n3 - MAC\nO - VOLTAR");
            option = scan.nextLine();
            switch (option) {
                case "1" -> {
                    System.out.println("DIGITE A NOTA: ");
                    String nota = scan.nextLine();
                    if (Validation.Number(nota)) {
                        if (Validation.Option(Float.parseFloat(nota),0,20)){
                        atualizarNota(disciplina, 0, Float.parseFloat(nota),student);
                        //NO METODO MAIN CHMAR O METODO QUE IMPRIME AS NOTAS DO ESTUDANTE
                            System.out.println(VERDE+"NOTA LAÇADA"+RESET);
                        }else System.out.println(YELLOW+"DIGITE UMA NOTA DE O - 20"+RESET);
                    }else {
                        System.out.println(YELLOW+"DIGITE UMA NOTA VÁLIDA, DE PREFERENCIA NÚMEROS"+RESET);
                    }
                }
                case "2" ->{
                    System.out.println("DIGITE A NOTA: ");
                    String nota = scan.nextLine();
                    if (Validation.Number(nota)) {
                        if (Validation.Option(Float.parseFloat(nota),0,20)){
                            atualizarNota(disciplina, 1, Float.parseFloat(nota),student);
                            //NO METODO MAIN CHMAR O METODO QUE IMPRIME AS NOTAS DO ESTUDANTE
                            System.out.println(VERDE+"NOTA LAÇADA"+RESET);
                        }else System.out.println(YELLOW+"DIGITE UMA NOTA DE O - 20"+RESET);
                    }else {
                        System.out.println(YELLOW+"DIGITE UMA NOTA VÁLIDA, DE PREFERENCIA NÚMEROS"+RESET);
                    }
                }
                case "3" ->{
                    System.out.println("DIGITE A NOTA: ");
                    String nota = scan.nextLine();
                    if (Validation.Number(nota)) {
                        if (Validation.Option(Float.parseFloat(nota),0,20)){
                            atualizarNota(disciplina, 2, Float.parseFloat(nota),student);
                            //NO METODO MAIN CHMAR O METODO QUE IMPRIME AS NOTAS DO ESTUDANTE
                            System.out.println(VERDE+"NOTA LAÇADA"+RESET);
                        }else System.out.println(YELLOW+"DIGITE UMA NOTA DE O - 20"+RESET);
                    }else {
                        System.out.println(YELLOW+"DIGITE UMA NOTA VÁLIDA, DE PREFERENCIA NÚMEROS"+RESET);
                    }
                }
                }
        }while (!option.equals("0"));



    }

}
