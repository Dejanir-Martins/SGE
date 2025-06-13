package Core;

import Main.Main;
import People.*;
import Utils.Validation;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Utils.Colors.*;

public class SGEService extends People {

    public static int gerarID() {
        final int MIN_ID = 15000;
        final int MAX_ID = 16000;
        final Random random = new Random();
        int newID;
        do {
            newID = random.nextInt(MAX_ID - MIN_ID + 1) + MIN_ID;
        } while (idExist(newID));
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
            System.out.println("1 - NOME\n3 - CLASSE\n4 - CURSO\n5 - SENHA\n6 - ELIMINAR\n0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine().trim();

            String result;
            switch (option) {
                case "6" -> {
                    System.out.println("1 - ELIMINAR TODOS OS ALUNOS\n2 - APENAS ESTE ALUNO\n3 - VOLTAR");
                    System.out.print("DECISÃO: ");
                    String delChoice = scan.nextLine();
                    if (delChoice.equals("1")) {
                        System.out.print("TEM CERTEZA? (S/N): ");
                        if (scan.nextLine().equalsIgnoreCase("s")) {
                            system.delete(false, true);
                            System.out.println("TODOS OS ALUNOS ELIMINADOS");
                        }
                    } else if (delChoice.equals("2")) {
                        System.out.print("TEM CERTEZA? (S/N): ");
                        if (scan.nextLine().equalsIgnoreCase("s")) {
                            system.delete(false, null, aluno);
                            System.out.println("ALUNO ELIMINADO");
                        }
                    }
                }
                case "1" -> {
                    System.out.print("NOVO NOME: ");
                    String nome = scan.nextLine();
                    if (Validation.Name(nome)) {
                        result = system.update(aluno, true, false, false, false, nome);
                        System.out.println(result);
                    } else System.out.println(RED + "NOME INVÁLIDO" + RESET);
                }
                case "3" -> {
                    System.out.print("NOVA CLASSE: ");
                    String classe = scan.nextLine();
                    if (Validation.Name(classe)) {
                        result = system.update(aluno, false, false, true, false, classe);
                        System.out.println(result);
                    } else System.out.println(RED + "CLASSE INVÁLIDA" + RESET);
                }
                case "4" -> {
                    System.out.print("NOVO CURSO: ");
                    System.out.println("1 - INFORMÁTICA");
                    System.out.println("2 - ELETRÔNICA E TELECOMUNICAÇÕES");
                    System.out.println("3 - ELETROMEDICINA");
                    System.out.println("4 - INFORMÁTICA E MULTIMÉDIA");
                    String curso = scan.nextLine();
                    if (Validation.Number(curso)) {
                        result = system.update(aluno, false, true, false, false, curso);
                        System.out.println(result);
                    } else System.out.println(RED + "CURSO INVÁLIDO" + RESET);
                }
                case "5" -> {
                    System.out.print("NOVA SENHA: ");
                    String senha = scan.nextLine();
                    if (Validation.Password(senha, aluno.getPassword())) {
                        result = system.update(aluno, false, false, false, true, senha);
                        System.out.println(result);
                    } else System.out.println(RED + "SENHA INVÁLIDA" + RESET);
                }
                case "0" -> System.out.println("VOLTANDO...");
                default -> System.out.println(RED + "OPÇÃO INVÁLIDA." + RESET);
            }
        } while (!option.equals("0"));
    }
    public void mostrarNotas(Student aluno) {
        System.out.println("Notas do aluno: " + aluno.getName());

        for (Subject s : aluno.getsubjects()) {
            System.out.println("- Disciplina: " + s.getName());
            System.out.println("  P1: " + s.getP1());
            System.out.println("  P2: " + s.getP2());
            System.out.println("  MAC: " + s.getMAC());
            System.out.println("  Média: " + s.getMedia());
            System.out.println();
        }
    }



    public static void enviarMensagem(People remetente, People destinatario, String mensagem) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String data = LocalDateTime.now().format(formatter);
        String conteudo = "[" + data + "] " + remetente.getName() + ": " + mensagem;
        destinatario.getInbox().add(conteudo);
        System.out.println(VERDE + "Mensagem enviada com sucesso." + RESET);
    }

    public static void mostrarMensagens(People pessoa) {
        System.out.println("📬 Caixa de entrada de " + pessoa.getName() + ":");
        List<String> correio = pessoa.getInbox();
        if (correio.isEmpty()) {
            System.out.println("📭 Sem mensagens.");
        } else {
            for (String mensagem : correio) {
                System.out.println(mensagem);
            }
            pessoa.setMessage(correio.size()); // Marca todas como lidas
        }
    }


    public void lancarNota(String disciplina, Scanner scan, Student student) {
        String option;
        Subject materia = student.findSubject(disciplina);

        if (materia == null) {
            materia = new Subject(disciplina, 0, 0, 0);
            student.addSubject(materia);
            System.out.println(VERDE + "Disciplina adicionada ao aluno." + RESET);
        }

        do {
            mostrarNotas(student); // Mostra sempre as notas atualizadas
            System.out.println("\nQual nota deseja lançar para " + disciplina + "?");
            System.out.println("1 - P1\n2 - P2\n3 - MAC\n0 - VOLTAR");
            option = scan.nextLine().trim();

            if (!Validation.Number(option)) {
                System.out.println(RED + "ENTRADA INVÁLIDA. DIGITE UM NÚMERO." + RESET);
                continue;
            }

            switch (option) {
                case "1", "2", "3" -> {
                    System.out.print("DIGITE A NOVA NOTA (0 - 20): ");
                    String nota = scan.nextLine().trim();
                    if (Validation.Number(nota)) {
                        float valor = Float.parseFloat(nota);
                        if (Validation.Option(valor, 0, 20)) {
                            float p1 = (float) materia.getP1();
                            float p2 = (float) materia.getP2();
                            float mac = (float) materia.getMAC();

                            switch (option) {
                                case "1" -> p1 = valor;
                                case "2" -> p2 = valor;
                                case "3" -> mac = valor;
                            }

                            materia.setNotas(p1, p2, mac);
                            System.out.println(VERDE + "NOTA LANÇADA COM SUCESSO!" + RESET);
                        } else {
                            System.out.println(YELLOW + "NOTA FORA DO INTERVALO 0-20." + RESET);
                        }
                    } else {
                        System.out.println(YELLOW + "INSIRA UM NÚMERO VÁLIDO." + RESET);
                    }
                }
                case "0" -> System.out.println(VERDE + "RETORNANDO AO MENU ANTERIOR..." + RESET);
                default -> System.out.println(RED + "OPÇÃO INVÁLIDA." + RESET);
            }

        } while (!option.equals("0"));
    }

    public static void Update(Teacher teacher, Scanner scan, String identify, Main system) {
        String option;

        do {
            system.show(false, true, false, identify);
            System.out.println("ESCOLHA UMA OPÇÃO DE ATUALIZAÇÃO:");
            System.out.println("1 - NOME\n2 - SENHA\n3 - ELIMINAR\n4 - DISCIPLINA\n0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine().trim();

            String result;
            switch (option) {
                case "4" ->{
                    System.out.println("ESCOLHA A NOVA DISCIPLINA");
                    disciplinas();
                    String subject = scan.nextLine();
                    if (Validation.Number(subject)) {
                        if (Validation.Option(Integer.parseInt(subject), 1, 10)) {
                            result = system.update(teacher,false,true,false,subject);
                            System.out.println(result);
                        }
                    }
                }
                case "1" -> {
                    System.out.print("NOVO NOME: ");
                    String nome = scan.nextLine();
                    if (Validation.Name(nome)) {
                        result = system.update(teacher, true, false, false, nome);
                        System.out.println(result);
                    } else {
                        System.out.println(RED + "NOME INVÁLIDO" + RESET);
                    }
                }
                case "2" -> {
                    System.out.print("NOVA SENHA: ");
                    String senha = scan.nextLine();
                    if (Validation.Password(senha, teacher.getPassword())) {
                        result = system.update(teacher, false, false, true, senha);
                        System.out.println(result);
                    } else {
                        System.out.println(RED + "SENHA INVÁLIDA" + RESET);
                    }
                }
                case "3" -> {
                    System.out.println("1 - ELIMINAR TODOS OS PROFESSORES\n2 - APENAS ESTE PROFESSOR\n3 - VOLTAR");
                    System.out.print("DECISÃO: ");
                    String delChoice = scan.nextLine();
                    if (delChoice.equals("1")) {
                        System.out.print("TEM CERTEZA? (S/N): ");
                        if (scan.nextLine().equalsIgnoreCase("s")) {
                            system.delete(true, false);
                            System.out.println("PROFESSORES ELIMINADOS");
                        }
                    } else if (delChoice.equals("2")) {
                        System.out.print("TEM CERTEZA? (S/N): ");
                        if (scan.nextLine().equalsIgnoreCase("s")) {
                            system.delete(true, teacher, null);
                            System.out.println("PROFESSOR ELIMINADO");
                        }
                    }
                }
                case "0" -> System.out.println("VOLTANDO...");
                default -> System.out.println(RED + "OPÇÃO INVÁLIDA." + RESET);
            }
        } while (!option.equals("0"));

    }

    static void disciplinas() {
        System.out.println("1 - MATEMÁTICA");
        System.out.println("2 - QUÍMICA");
        System.out.println("3 - INGLÊS");
        System.out.println("4 - FÍSICA");
        System.out.println("5 - ELETROTECNIA");
        System.out.println("6 - INFORMÁTICA");
        System.out.println("7 - SEAC");
        System.out.println("8 - PORTUGUES");
        System.out.println("9 - ANATOMIA");
        System.out.println("10 - FAI");
    }

    public static void gerarRelatorioNotas(List<Student> listaAlunos) {
        final float MEDIA_MINIMA = 10.0f;
        System.out.println(VERDE+"==== RELATÓRIO DE NOTAS ===="+RESET);

        for (Student aluno : listaAlunos) {
            System.out.println("\nAluno: " + aluno.getName() + " | Processo: " + aluno.getProcessNumber());
            boolean reprovado = false;

            for (Subject disciplina : aluno.getsubjects()) {
                float media = (float) disciplina.getMedia();
                String estado = media >= MEDIA_MINIMA ? (BLUE+"TRANSITA"+RESET) :    (RED+"NÃO TRANSITA"+RESET);
                if (media < MEDIA_MINIMA) reprovado = true;

                System.out.printf("Disciplina: %-15s | P1: %.1f | P2: %.1f | MAC: %.1f | Média: %.1f -> %s\n",
                        disciplina.getName(), disciplina.getP1(), disciplina.getP2(), disciplina.getMAC(), media, estado);
            }

            String statusFinal = reprovado ? (RED+"REPROVADO"+RESET) : (VERDE+"APROVADO"+RESET);
            System.out.println("Resultado Final: " + reprovado + statusFinal +RESET);
            System.out.println("--------------------------------------------------");
        }
    }
    public static void salvarRelatorioTxt(List<Student> listaAlunos, String caminhoFicheiro) {
        try (PrintWriter writer = new PrintWriter(caminhoFicheiro)) {
            writer.println("==== RELATÓRIO DE NOTAS ====");

            for (Student aluno : listaAlunos) {
                writer.println("\nAluno: " + aluno.getName() + " | Processo: " + aluno.getProcessNumber());
                boolean reprovado = false;

                for (Subject disciplina : aluno.getsubjects()) {
                    float media = (float) disciplina.getMedia();
                    String estado = media >= 10.0 ? "APROVADO" : "REPROVADO";
                    if (media < 10.0) reprovado = true;

                    writer.printf("Disciplina: %-15s | P1: %.1f | P2: %.1f | MAC: %.1f | Média: %.1f -> %s\n",
                            disciplina.getName(), disciplina.getP1(), disciplina.getP2(), disciplina.getMAC(), media, estado);
                }

                String statusFinal = reprovado ? "REPROVADO" : "APROVADO";
                writer.println("Resultado Final: " + statusFinal);
                writer.println("--------------------------------------------------");
            }

            System.out.println("Relatório salvo com sucesso em: " + caminhoFicheiro);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o relatório: " + e.getMessage());
        }
    }


}
