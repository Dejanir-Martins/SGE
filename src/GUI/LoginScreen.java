package GUI;

import javax.swing.*;
import java.awt.*;

import Core.SGE;
import People.Student;
import People.Teacher;

public class LoginScreen extends JFrame {

    private final JTextField idField;
    private final JPasswordField passwordField;
    private final SGE sistema;

    public LoginScreen(SGE sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Gestão Escolar - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 240, 255));
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("LOGIN SGE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(60, 60, 160));
        add(titulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        formPanel.setBackground(new Color(240, 240, 255));

        idField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Entrar");

        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(60, 60, 160));
        loginButton.setForeground(Color.WHITE);

        formPanel.add(new JLabel("ID ou Nº de Processo:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Senha:"));
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 255));
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> realizarLogin());

        setVisible(true);
    }

    private void realizarLogin() {
        String id = idField.getText().trim();
        String senha = new String(passwordField.getPassword()).trim();

        if (id.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (id.equals(sistema.getAdminid()) && senha.equals(sistema.getAdmPass())) {
            JOptionPane.showMessageDialog(this, "Bem-vindo, Administrador!");
            new SGEGUI(sistema, null, null, false).setVisible(true);
            this.dispose();
            return;
        }

        if (sistema.LoginStudent(id, senha)) {
            Student aluno = sistema.findStudent(id);
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + aluno.getName());
            new SGEGUI(sistema, aluno, null, false).setVisible(true);
            this.dispose();
        } else if (sistema.LoginTeacher(id, senha)) {
            Teacher professor = sistema.findTeacher(id);
            JOptionPane.showMessageDialog(this, "Bem-vindo, Prof. " + professor.getName());
            new SGEGUI(sistema, null, professor, false).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// A classe SGEGUI deve ter um construtor como este:
// public SGEGUI(SGE sistema, Student alunoLogado, Teacher professorLogado, boolean isAdmin) { ... }
