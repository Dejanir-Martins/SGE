package Main;
import Core.SGE;
import java.util.Scanner;

import static Core.SGEService.Update;
import static Utils.Colors.*;

public class Main extends SGE {
        public static void main(String[] args) throws InterruptedException {
            Main system = new Main();
            Scanner scan = new Scanner(System.in);
            String menuChoice;

            do {
                System.out.println("\n\n--- BEM-VINDO AO SISTEMA DE GESTÃO ESCOLAR ---");
                System.out.println("----------- SCHOOLMICROSSISTEMS --------------");
                System.out.println("ESCOLHA UMA OPÇÃO:");
                System.out.println("1 - ENTRAR");
                System.out.println("2 - SAIR");
                System.out.println("3 - MODO GRÁFICO");
                System.out.print("OPÇÃO: ");
                menuChoice = scan.nextLine().trim();

                switch (menuChoice) {
                    case "1" -> {
                        System.out.println("ENTRE COM OS SEUS RESPECTIVOS CREDENCIAIS");
                        System.out.print("IDENTIFICADOR / NÚMERO DE PROCESSO: ");
                        String id = scan.nextLine().trim();
                        System.out.print("SENHA: ");
                        String pass = scan.nextLine().trim();

                        if (system.LoginStudant(id, pass)) {
                            System.out.println("LOGIN c    vCOMO ESTUDANTE EFETUADO COM SUCESSO.");
                            String alunoOp;
                            do {
                                System.out.println("\n--- MODO ALUNO ---");
                                System.out.println("1 - VER NOTAS");
                                System.out.println("2 - ENVIAR MENSAGEM");
                                System.out.println("3 - VOLTAR");
                                System.out.print("OPÇÃO: ");
                                alunoOp = scan.nextLine();

                                switch (alunoOp) {
                                    case "1" -> {
                                        String nota = system.notasAlunos.get(id);
                                        System.out.println("SUA NOTA: " + (nota != null ? nota : "Nenhuma nota lançada"));
                                    }
                                    case "2" -> {
                                        System.out.print("DESTINATÁRIO (adm ou id do professor): ");
                                        String dest = scan.nextLine();
                                        System.out.print("MENSAGEM: ");
                                        String msg = scan.nextLine();
                                        system.enviarMensagem(id, dest, msg);
                                        System.out.println("Mensagem enviada!");
                                    }
                                    case "3" -> System.out.println("Saindo do modo aluno...");
                                    default -> System.out.println("Opção inválida.");
                                }
                            } while (!alunoOp.equals("3"));

                        } else if (system.LoginTeacher(id, pass)) {
// PROFESSOR
                            String profOp;
                            do {
                                System.out.println("\n--- MODO PROFESSOR ---");
                                System.out.println("1 - LANÇAR NOTA");
                                System.out.println("2 - VER LISTA DE ALUNOS");
                                System.out.println("3 - CAIXA DE MENSAGENS");
                                System.out.println("4 - SAIR");
                                System.out.print("OPÇÃO: ");
                                profOp = scan.nextLine();

                                switch (profOp) {
                                    case "1" -> {
                                        if (system.listStudent.isEmpty()) {
                                            System.out.println(YELLOW + "SEM ALUNOS AINDA\n" + RESET);
                                            break;
                                        }
                                        System.out.print("NÚMERO DE PROCESSO DO ALUNO: ");
                                        String proc = scan.nextLine();
                                        if (system.findStudent(proc) != null) {
                                            do {
                                                System.out.println("INFORME A NOTA QUE DESEJA LANÇAR \\n 1 - P1\\n2 - P2" +
                                                        "\\n3 - MAC\n0 - VOLTAR ");
                                                profOp = scan.next();
                                                system.receberNotas(scan, system.findStudent(proc), profOp);
                                                if (Number(profOp)) {
                                                    if (Integer.parseInt(profOp) < 0 || Integer.parseInt(profOp) > 3)
                                                        System.out.println(RED + "SELEVIONE UMA OPÇÃO VÁLIDA\n" + RESET);
                                                } else System.out.println(RED + "DIGITE UM NÚMERO VÁLIDO" + RESET);
                                            } while (!profOp.equals("0"));
                                        } else {
                                            System.out.println(YELLOW + "ALUNO NÃO ENCONTRADO" + RESET);
                                        }
                                    }
                                    case "2" -> system.show(true, true, false, null);
                                    case "3" -> verCaixaDeEntrada(id);
                                    case "4" -> System.out.println("Encerrando sessão do professor...");
                                    default -> System.out.println("Opção inválida.");
                                }

                            } while (!profOp.equals("4"));

                        }
                        else if (id.equals(system.getAdmId()) && pass.equals(system.getAdmPass())) {
                            String adminChoice;
                            do {
                                System.out.println("\n--- MODO ADMINISTRADOR ---");
                                System.out.println("1 - CRIAR");
                                System.out.println("2 - LISTAR");
                                System.out.println("3 - ATUALIZAR / REMOVER");
                                System.out.println("0 - SAIR");
                                System.out.print("OPÇÃO: ");
                                adminChoice = scan.nextLine();
                                switch (adminChoice) {
                                    case "1" -> {
                                        System.out.println("DESEJA CRIAR UM NOVO: \n 1 - ALUNO OU 2 - PROFESSOR?");
                                        String escolha = scan.nextLine().trim();
                                        if (escolha.equals("1")) {
                                            system.add(false, true);
                                        } else if (escolha.equals("2")) {
                                            system.add(true, false);
                                        } else System.out.println(RED + "ESCOLHA INVÁLIDA\n" + RESET);
                                    }

                                    case "2" -> {
                                        System.out.println("1 - ALUNOS\n2 - PROFESSOR");
                                        if (scan.nextLine().equals("1")) {
                                            system.show(true, true, false, null);
                                        } else if (scan.nextLine().equals("2")) {
                                            system.show(true, false, true, null);
                                        } else
                                            System.out.println(YELLOW + "INVÁLIDO. DEVIAS DIGITAR UM GRUPO CORRESPONDE AO NÚMER (1-2)");
                                    }

                                    case "3" -> {
                                        if (system.listStudent.isEmpty() && system.listTeachers.isEmpty()) {
                                            System.out.println(RED + "AINDA NÃO EXISTE NINGUÉM PARA ATUALIZAR OU REMOVER" + RESET);
                                            System.out.println(YELLOW + "CONVÉM ADICIONAR ALGUÉM PRIMEIRO\n" + RESET);
                                            break;
                                        }
                                        System.out.print("INFORME O IDENTIFICADOR: ");
                                        String identify = scan.nextLine();

                                        //continuar aqui
                                        if (!system.listStudent.isEmpty()) {
                                            if (system.findStudent(identify) != null) {
                                                Student aluno = system.findStudent(identify);
                                                Update(aluno, scan, identify, system);
                                                System.out.println("DESEJA REALIZAR OUTRA OPERAÇÃO?");
                                                System.out.println("1 - ATUALIZAR NOVAMENTE");
                                                System.out.println("2 - ELIMINAR");
                                                System.out.println("3 - VOLTAR");
                                                System.out.print("OPÇÃO: ");
                                                try {
                                                    int decision = Integer.parseInt(scan.nextLine());
                                                    switch (decision) {
                                                        case 1 -> System.out.println("REINICIE O MENU DE ATUALIZAÇÃO.");
                                                        case 2 -> {
                                                            System.out.println("1 - ELIMINAR TODOS OS ALUNOS");
                                                            System.out.println("2 - APENAS ESTE ALUNO");
                                                            System.out.print("DECISÃO: ");
                                                            int delChoice = Integer.parseInt(scan.nextLine());
                                                            if (delChoice == 1) {
                                                                System.out.print("TEM CERTEZA QUE DESEJA ELIMINAR TODOS OS ALUNOS? (S/N): ");
                                                                String confirm = scan.nextLine();
                                                                if (confirm.equalsIgnoreCase("s")) {
//delete
                                                                } else {
                                                                    System.out.println("ACAO CANCELADA");
                                                                }
                                                            } else if (delChoice == 2) {
                                                                System.out.print("TEM CERTEZA QUE DESEJA ELIMINAR ESTE ALUNO? (S/N): ");
                                                                String confirm = scan.nextLine();
                                                                if (confirm.equalsIgnoreCase("s")) {

                                                                } else {
                                                                    System.out.println("ACAO CANCELADA");
                                                                }
                                                            }
                                                        }
                                                        case 3 -> System.out.println("VOLTANDO AO MENU PRINCIPAL...");
                                                        default -> System.out.println("ESCOLHA INVÁLIDA.");
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("DIGITE UMA OPÇÃO VÁLIDA NUMÉRICA.");
                                                }

                                            } else {
                                                System.out.println("ALUNO NÃO ENCONTRADO.");
                                            }
                                        }
                                    }
                                    case "0" -> {
                                        System.out.print("SAINDO");
                                        for (int i = 0; i < 3; i++) {
                                            System.out.print(".");
                                            Thread.sleep(700);
                                        }
                                        System.out.println("\nMODO ADMINISTRADOR ENCERRADO.");
                                    }

                                    default -> System.out.println("OPÇÃO INVÁLIDA.");
                                }
                            }while (!adminChoice.equals("0"));
                        }
                        else {
                            System.out.println(RED+"IDENTIFICADOR OU SENHA INVÁLIDOS.\n"+RESET);
                            System.out.println(YELLOW+"REVEJA POR FAVOR OS DADOS QUE INTRODUZIO"+RESET);
                        }
                    }

                    case "2" -> System.out.println("ENCERRANDO O SISTEMA...");
                    case "3" -> {
                        System.out.println("Abrindo interface gráfica...");
//javax.swing.SwingUtilities.invokeLater(() -> new SGESystemGUI().mostrarTelaLogin());

//new SGEGUI();

                    }

                    default -> System.out.println("OPÇÃO INVÁLIDA! ESCOLHA ENTRE 1 E 3.");
                }



            } while (!menuChoice.equals("2"));
            scan.close();
        }


}
