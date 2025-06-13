package GUI;

import Core.SGE;
import People.Student;
import People.Teacher;

import javax.swing.*;
import java.awt.*;

public class SGEGUI extends JFrame {
    private final SGE sge;
    private final Student loggedStudent;
    private final Teacher loggedTeacher;

    public SGEGUI(SGE sge, Student student, Teacher teacher, boolean isAdmin) {
        this.sge = sge;
        this.loggedStudent = student;
        this.loggedTeacher = teacher;

        setTitle("Painel Principal - Sistema de Gestão Escolar");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        if (isAdmin) {
            welcomeLabel.setText("Bem-vindo, Administrador");
        } else if (loggedStudent != null) {
            welcomeLabel.setText("Bem-vindo, Aluno: " + loggedStudent.getName());
        } else if (loggedTeacher != null) {
            welcomeLabel.setText("Bem-vindo, Professor: " + loggedTeacher.getName());
        }

        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnPerfil = new JButton("Ver Perfil");
        JButton btnAtualizar = new JButton("Atualizar Dados");
        JButton btnMensagens = new JButton("Mensagens");
        JButton btnNotas = new JButton("Notas e Avaliações");
        JButton btnCadastro = new JButton("Cadastrar Utilizador");
        JButton btnListar = new JButton("Listar Utilizadores");
        JButton btnApagar = new JButton("Eliminar Utilizador");
        JButton btnSair = new JButton("Terminar Sessão");

        centerPanel.add(btnPerfil);
        centerPanel.add(btnAtualizar);
        centerPanel.add(btnMensagens);
        centerPanel.add(btnNotas);

        if (isAdmin) {
            centerPanel.add(btnCadastro);
            centerPanel.add(btnListar);
            centerPanel.add(btnApagar);
        }

        centerPanel.add(btnSair);
        add(centerPanel, BorderLayout.CENTER);

        // Estilo bonito
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));

        // Ações dos botões
        btnPerfil.addActionListener(e -> mostrarPerfil());
        btnAtualizar.addActionListener(e -> atualizarDados());
        btnMensagens.addActionListener(e -> abrirMensagens());
        btnNotas.addActionListener(e -> abrirNotas());
        btnCadastro.addActionListener(e -> cadastrar());
        btnListar.addActionListener(e -> listar());
        btnApagar.addActionListener(e -> apagar());
        btnSair.addActionListener(e -> {
            dispose();
            new LoginScreen(sge).setVisible(true);
        });

        setVisible(true);
    }

    private void mostrarPerfil() {
        if (loggedStudent != null) {
            JOptionPane.showMessageDialog(this,
                    "Nome: " + loggedStudent.getName() +
                            "\nProcesso: " + loggedStudent.getProcessNumber() +
                            "\nCurso: " + loggedStudent.getCourse() +
                            "\nAno: " + loggedStudent.getYear() +
                            "\nTurma: " + loggedStudent.getStudentClass(),
                    "Perfil do Aluno",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (loggedTeacher != null) {
            JOptionPane.showMessageDialog(this,
                    "Nome: " + loggedTeacher.getName() +
                            "\nIdentificador: " + loggedTeacher.getId() +
                            "\nDisciplina: " + loggedTeacher.getSubject(),
                    "Perfil do Professor",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Administrador não possui perfil pessoal.");
        }
    }

    private void atualizarDados() {
        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome:");
        if (novoNome != null && !novoNome.isBlank()) {
            if (loggedStudent != null) {
                sge.update(loggedStudent, true, false, false, false, novoNome);
            } else if (loggedTeacher != null) {
                sge.update(loggedTeacher, true, false, false, novoNome);
            }
            JOptionPane.showMessageDialog(this, "Nome atualizado com sucesso.");
        }
    }

    private void abrirMensagens() {
        JOptionPane.showMessageDialog(this, "(Em breve) Sistema de mensagens em desenvolvimento.",
                "Mensagens", JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirNotas() {
        JOptionPane.showMessageDialog(this, "(Em breve) Sistema de notas e avaliações será adicionado.",
                "Notas e Avaliações", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cadastrar() {
        String[] opcoes = {"Aluno", "Professor"};
        int tipo = JOptionPane.showOptionDialog(this, "Escolha o tipo de utilizador:",
                "Cadastro", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        String nome = JOptionPane.showInputDialog(this, "Nome:");

        if (tipo == 0) {
            String curso = JOptionPane.showInputDialog(this, "Curso (1-4):");
            String ano = JOptionPane.showInputDialog(this, "Ano (10-13):");
            sge.create(nome, curso, ano);
        } else if (tipo == 1) {
            String disciplina = JOptionPane.showInputDialog(this, "Disciplina (1-10):");
            sge.create(nome, disciplina);
        }
    }

    private void listar() {
        String[] opcoes = {"Alunos", "Professores"};
        int escolha = JOptionPane.showOptionDialog(this, "Listar:",
                "Listar Utilizadores", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        if (escolha == 0) {
            sge.show(true, true, false, null);
        } else {
            sge.show(true, false, true, null);
        }
    }

    private void apagar() {
        String[] opcoes = {"Aluno", "Professor"};
        int tipo = JOptionPane.showOptionDialog(this, "Remover qual tipo de utilizador?",
                "Eliminar", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        String id = JOptionPane.showInputDialog(this, "Digite o ID/Processo:");
        if (id == null || id.isBlank()) return;

        if (tipo == 0) {
            Student student = sge.findStudent(id);
            if (student != null) {
                sge.delete(false, null, student);
                JOptionPane.showMessageDialog(this, "Aluno eliminado com sucesso.");
            }
        } else {
            Teacher teacher = sge.findTeacher(id);
            if (teacher != null) {
                sge.delete(true, teacher, null);
                JOptionPane.showMessageDialog(this, "Professor eliminado com sucesso.");
            }
        }
    }
}
