package Main;

import Core.*;
import People.*;

import java.util.Scanner;

import static Core.SGEService.Update;
import static Utils.Colors.*;
import static java.lang.System.out;

public class Main extends SGE {

    public static void main(String[] args) {
        Main system = new Main();
        SGEService service = new SGEService();
        Scanner scan = new Scanner(System.in);
        String menuChoice;

        do {
            out.println("\nWelcome to the system");
            out.println("1. CLI MODE");
            out.println("2. GUI MODE");
            out.println("0. Exit");
            out.print("Please enter your choice: ");
            menuChoice = scan.nextLine().trim();

            switch (menuChoice) {
                case "1" -> runCliMode(system, service, scan);
                case "2" -> out.println("[GUI MODE COMING SOON]");
                case "0" -> out.println("EXITING...");
                default -> out.println(ROXO + "Please enter a valid choice!" + RESET);
            }
        } while (!menuChoice.equals("0"));

        scan.close();
    }

    private static void runCliMode(Main system, SGEService service, Scanner scan) {
        String menuChoice;
        do {
            out.println("\n--- BEM-VINDO AO SISTEMA DE GESTÃO ESCOLAR ---");
            out.println(ROXO + "------------- SCHOOLMICROSSISTEMS ------------" + RESET);
            out.println(VERDE + "------------------- CLI MODE -----------------" + RESET);
            out.println("1 - ENTRAR");
            out.println("2 - SAIR");
            out.print("OPÇÃO: ");
            menuChoice = scan.nextLine().trim();

            if (menuChoice.equals("1")) {
                handleLogin(system, service, scan);
            } else if (!menuChoice.equals("2")) {
                out.println(YELLOW + "OPÇÃO INVÁLIDA! ESCOLHA ENTRE 1 E 2." + RESET);
            }
        } while (!menuChoice.equals("2"));
    }

    private static void handleLogin(Main system, SGEService service, Scanner scan) {
        out.println("ENTRE COM OS SEUS RESPECTIVOS CREDENCIAIS");
        out.print("IDENTIFICADOR / NÚMERO DE PROCESSO: ");
        String id = scan.nextLine().trim();
        out.print("SENHA: ");
        String pass = scan.nextLine().trim();

        if (!Student.listStudent.isEmpty() && system.LoginStudant(id, pass)) {
            Student student = system.findStudent(id);
            runStudentMode(student, scan);
        } else if (!Teacher.listTeachers.isEmpty() && system.LoginTeacher(id, pass)) {
            Teacher teacher = system.findTeacher(id);
            runTeacherMode(system, service, teacher, scan);
        } else if (id.equals(system.getAdminid()) && pass.equals(system.getAdmPass())) {
            runAdminMode(system, scan);
        } else {
            out.println(RED + "IDENTIFICADOR OU SENHA INVÁLIDOS." + RESET);
            out.println(YELLOW + "REVEJA POR FAVOR OS DADOS QUE INTRODUZIO" + RESET);
        }
    }

    private static void runStudentMode(Student student, Scanner scan) {
        String alunoOp;
        do {
            out.println("\nPERFIL: -> " + VERDE + student.getName() + RESET);
            out.println("1 - VER NOTAS");
            out.println("2 - ENVIAR MENSAGEM");
            out.println("3 - VOLTAR");
            out.print("OPÇÃO: ");
            alunoOp = scan.nextLine();

            switch (alunoOp) {
                case "1" -> {
                    out.println("INFORME A DISCIPLINA:");
                    for (People.Disciplina d : People.Disciplina.values()) {
                        out.println(d.name());
                    }
                    scan.nextLine(); // apenas para simular a leitura
                    out.println(student.getNotasPorDisciplina());
                }
                case "2" -> {
                    out.print("DESTINATÁRIO\n1 - ADM\n2 - PROFESSOR: ");
                    scan.nextLine(); // apenas para simular a leitura
                    out.print("MENSAGEM: ");
                    scan.nextLine();
                    out.println("Mensagem enviada!");
                }
                case "3" -> out.println("Saindo do modo aluno...");
                default -> out.println("Opção inválida.");
            }
        } while (!alunoOp.equals("3"));
    }

    private static void runTeacherMode(Main system, SGEService service, Teacher teacher, Scanner scan) {
        String profOp;
        do {
            out.println("\nPERFIL: -> " + VERDE + teacher.getName() + RESET);
            out.println("1 - LANÇAR NOTA");
            out.println("2 - VER LISTA DE ALUNOS");
            out.println("4 - SAIR");
            out.print("OPÇÃO: ");
            profOp = scan.nextLine();

            switch (profOp) {
                case "1" -> {
                    if (Student.listStudent.isEmpty()) {
                        out.println(YELLOW + "SEM ALUNOS AINDA\n" + RESET);
                        break;
                    }
                    out.print("NÚMERO DE PROCESSO DO ALUNO: ");
                    String proc = scan.nextLine();
                    Student student1 = system.findStudent(proc);
                    if (student1 != null) {
                        SGEService.lancarNota(People.Disciplina.FISICA, scan, student1);
                        service.mostrarMedias(student1);
                    } else {
                        out.println(YELLOW + "ALUNO NÃO ENCONTRADO" + RESET);
                    }
                }
                case "2" -> system.show(true, true, false, null);
                case "4" -> out.println("Encerrando sessão do professor...");
                default -> out.println("Opção inválida.");
            }
        } while (!profOp.equals("4"));
    }

    private static void runAdminMode(Main system, Scanner scan) {
        String adminChoice;
        do {
            out.println(VERDE + "\n--- MODO ADMINISTRADOR ---" + RESET);
            out.println("1 - CADASTRAR");
            out.println("2 - LISTAR");
            out.println("3 - ATUALIZAR");
            out.println("0 - SAIR");
            out.print("OPÇÃO: ");
            adminChoice = scan.nextLine().trim();

            switch (adminChoice) {
                case "1" -> {
                    out.println("DESEJA CADASTRAR UM NOVO: \n 1 - ALUNO OU 2 - PROFESSOR X - VOLTAR");
                    String escolha = scan.nextLine().trim().toLowerCase();
                    switch (escolha) {
                        case "1" -> system.add(false, true);
                        case "2" -> system.add(true, false);
                        case "x" -> out.println(VERDE + "<--" + RESET);
                        default -> out.println(RED + "ESCOLHA INVÁLIDA\n" + RESET);
                    }
                }
                case "2" -> {
                    out.println("1 - ALUNOS\n2 - PROFESSOR\n3 - VOLTAR");
                    String escolha = scan.nextLine().trim();
                    switch (escolha) {
                        case "1" -> {
                            if (Student.listStudent.isEmpty()) {
                                out.println(YELLOW + "SEM ALUNOS AINDA\n" + RESET);
                                break;
                            }
                            system.show(true, true, false, null);
                        }
                        case "2" -> {
                            if (Teacher.listTeachers.isEmpty()) {
                                out.println(YELLOW + "AINDA NENHUM PROFESSOR PARA LISTAR" + RESET);
                                break;
                            }
                            system.show(true, false, true, null);
                        }
                        case "3" -> out.println(VERDE + "<--" + RESET);
                        default -> out.println(YELLOW + "INVÁLIDO. DIGITE UM NÚMERO ENTRE 1-3" + RESET);
                    }
                }
                case "3" -> {
                    out.print("INFORME O IDENTIFICADOR: ");
                    String identify = scan.nextLine();
                    if (!Student.listStudent.isEmpty() && system.findStudent(identify) != null) {
                        Update(system.findStudent(identify), scan, identify, system);
                    } else if (!Teacher.listTeachers.isEmpty() && system.findTeacher(identify) != null) {
                        Update(system.findTeacher(identify), scan, identify, system);
                    } else {
                        out.println(RED + "NENHUM REGISTRO ENCONTRADO." + RESET);
                    }
                }
                case "0" -> out.println("MODO ADMINISTRADOR ENCERRADO.");
                default -> out.println("OPÇÃO INVÁLIDA.");
            }
        } while (!adminChoice.equals("0"));
    }
}
