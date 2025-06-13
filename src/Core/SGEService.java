package Core;

import Main.Main;
import People.*;
import Utils.Validation;

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
                    String curso = scan.nextLine();
                    if (Validation.Name(curso)) {
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

    public void inicializarDisciplinas(Student aluno) {
        for (Disciplina disciplina : Disciplina.values()) {
            List<Double> notas = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
            aluno.getNotasPorDisciplina().put(disciplina, notas);
        }
    }

    public static void atualizarNota(Disciplina disciplina, int indice, double novaNota, Student student) {
        List<Double> notas = student.getNotasPorDisciplina().get(disciplina);
        if (indice >= 0 && indice < notas.size()) {
            notas.set(indice, novaNota);
            System.out.println("🔄 Nota atualizada em " + disciplina + ": " + novaNota);
        } else {
            System.out.println("⚠️ Índice inválido! Escolha entre 0, 1 ou 2.");
        }
    }

    public double calcularMedia(Disciplina disciplina, Student aluno) {
        List<Double> notas = aluno.getNotasPorDisciplina().get(disciplina);
        return notas.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
    }

    public void mostrarMedias(Student aluno) {
        System.out.println("📊 Médias de " + aluno.getName() + ":");
        for (Disciplina disciplina : Disciplina.values()) {
            double media = calcularMedia(disciplina, aluno);
            System.out.println("📚 " + disciplina + " → Média: " + (media >= 0 ? media : "Notas insuficientes"));
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
            pessoa.setMensagensLidas(correio.size()); // Marca todas como lidas
        }
    }


    public static void lancarNota(Disciplina disciplina, Scanner scan, Student student) {
        String option;
        do {
            System.out.println("1 - P1\n2 - P2\n3 - MAC\n0 - VOLTAR");
            option = scan.nextLine().trim();
            switch (option) {
                case "1", "2", "3" -> {
                    int indice = Integer.parseInt(option) - 1;
                    System.out.print("DIGITE A NOTA: ");
                    String nota = scan.nextLine();
                    if (Validation.Number(nota)) {
                        float valor = Float.parseFloat(nota);
                        if (Validation.Option(valor, 0, 20)) {
                            atualizarNota(disciplina, indice, valor, student);
                            System.out.println(VERDE + "NOTA LANÇADA" + RESET);
                        } else System.out.println(YELLOW + "NOTA FORA DO INTERVALO 0-20" + RESET);
                    } else {
                        System.out.println(YELLOW + "INSIRA UM NÚMERO VÁLIDO" + RESET);
                    }
                }
            }
        } while (!option.equals("0"));
    }
    public static void Update(Teacher teacher, Scanner scan, String identify, Main system) {
        String option;
        do {
            system.show(false, true, false, identify);
            System.out.println("ESCOLHA UMA OPÇÃO DE ATUALIZAÇÃO:");
            System.out.println("1 - NOME\n2 - SENHA\n3 - ELIMINAR\n0 - VOLTAR");
            System.out.print("OPÇÃO: ");
            option = scan.nextLine().trim();

            String result;
            switch (option) {
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

}
