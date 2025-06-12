package Core;

import Main.Main;
import People.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Utils.*;

import static Utils.Colors.RED;
import static Utils.Colors.RESET;

public class SGEService{
    Student student;
    Teacher teacher;

    public int gerarID() {
            final int MIN_ID = 15000;
            final int MAX_ID = 16000;
            final Random random = new Random();

            int newID;
            do {
                newID = random.nextInt(MAX_ID - MIN_ID + 1) + MIN_ID;
            } while (idExist(newID)); // Só aceita IDs que não existam

            return newID;
        }
        private boolean idExist(int id) {
            return student.getListStudent().stream().anyMatch(student -> student.getProcessNumber() == id) ||
                    teacher.getListTeachers().stream().anyMatch(teacher -> teacher.getId() == id);
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
            System.out.println("0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine();

            String result;
            switch (option) {
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
            System.out.println("0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine();

            String result;
            switch (option) {
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

    protected void receberNotas(Scanner scan, Student student, String posiction){
        if (!ehNumero(posiction)) {
            System.out.println(RED+"INVÁLIDO, DIGITE UM NÚMERO"+RESET);
            return;
        }
        student.notas[Integer.parseInt(posiction)-1] = validarEntrada(scan,"NOTA (0 a 20): ");
    }

    public void enviarMensagem(String de, String para, String conteudo) {
        caixaMensagens.computeIfAbsent(para, k -> new ArrayList<>())
                .add(new Mensagem(de, para, conteudo));
    }
}
