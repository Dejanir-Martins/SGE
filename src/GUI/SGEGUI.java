package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Core.SGE;
import People.*;
import Utils.*;

import static Core.SGE.*;

public class SGEGUI {
        private SGE sge;

        public SGEGUI(SGE sge) {
            this.sge = sge;
            criarJanelaPrincipal();
        }

        private void criarJanelaPrincipal() {
            JFrame frame = new JFrame("Sistema de Gestão Escolar - GUI");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel painel = new JPanel();
            painel.setLayout(new GridLayout(0, 1, 10, 10));

            JButton cadastrarAluno = new JButton("Cadastrar Aluno");
            JButton cadastrarProfessor = new JButton("Cadastrar Professor");
            JButton lancarNota = new JButton("Lançar Nota");
            JButton verNotas = new JButton("Ver Notas");
            JButton enviarMensagem = new JButton("Enviar Mensagem");
            JButton verMensagens = new JButton("Ver Mensagens");

            cadastrarAluno.addActionListener(e -> cadastrarAluno());
            cadastrarProfessor.addActionListener(e -> cadastrarProfessor());
            lancarNota.addActionListener(e -> lancarNota());
            verNotas.addActionListener(e -> verNotas());
            enviarMensagem.addActionListener(e -> enviarMensagem());
            verMensagens.addActionListener(e -> verMensagens());

            painel.add(cadastrarAluno);
            painel.add(cadastrarProfessor);
            painel.add(lancarNota);
            painel.add(verNotas);
            painel.add(enviarMensagem);
            painel.add(verMensagens);

            frame.add(painel);
            frame.setVisible(true);
        }

        private void cadastrarAluno() {
            String nome = JOptionPane.showInputDialog("Nome do aluno:");
            String curso = JOptionPane.showInputDialog("Curso (1 a 4):");
            String ano = JOptionPane.showInputDialog("Ano (10 a 13):");
            sge.create(nome, curso, ano);
        }

        private void cadastrarProfessor() {
            String nome = JOptionPane.showInputDialog("Nome do professor:");
            String disciplina = JOptionPane.showInputDialog("Disciplina (1 a 10):");
            sge.create(nome, disciplina);
        }

        private void lancarNota() {
            String processo = JOptionPane.showInputDialog("Número de processo do aluno:");
            Student aluno = sge.findStudent(processo);
            if (aluno != null) {
                String disciplina = JOptionPane.showInputDialog("Disciplina:");
                String notaStr = JOptionPane.showInputDialog("Nota:");
                try {
                    double nota = Double.parseDouble(notaStr);
                    //continuar aqui
                    aluno.adicionarNota(disciplina, nota);
                    JOptionPane.showMessageDialog(null, "Nota lançada com sucesso.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nota inválida.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
            }
        }

        private void verNotas() {
            String processo = JOptionPane.showInputDialog("Número de processo do aluno:");
            Student aluno = sge.findStudent(processo);
            if (aluno != null) {
                StringBuilder sb = new StringBuilder("Notas de " + aluno.getName() + ":\n");
                aluno.getNotas().forEach((disc, nota) -> sb.append(disc).append(" -> ").append(nota).append("\n"));
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
            }
        }

        private void enviarMensagem() {
            String remetenteId = JOptionPane.showInputDialog("Seu ID:");
            String destinatarioId = JOptionPane.showInputDialog("ID do destinatário:");
            String conteudo = JOptionPane.showInputDialog("Mensagem:");
            User remetente = sge.findStudent(remetenteId);
            if (remetente == null) remetente = sge.findTeacher(remetenteId);
            User destinatario = sge.findStudent(destinatarioId);
            if (destinatario == null) destinatario = sge.findTeacher(destinatarioId);

            if (remetente != null && destinatario != null) {
                remetente.enviarMensagem(destinatario, conteudo);
                JOptionPane.showMessageDialog(null, "Mensagem enviada com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "ID(s) inválido(s).");
            }
        }

        private void verMensagens() {
            String id = JOptionPane.showInputDialog("Seu ID:");
            User user = sge.findStudent(id);
            if (user == null) user = sge.findTeacher(id);

            if (user != null) {
                StringBuilder sb = new StringBuilder("Mensagens recebidas:\n");
                for (Mensagem m : user.getMensagens()) {
                    sb.append("De ").append(m.getRemetente().getName()).append(" -> ")
                            .append(m.getConteudo()).append(" (em ").append(m.getData()).append(")\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
            }
        }
    }

}
