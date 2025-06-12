package Core;
import java.util.*;

import People.*;
import Utils.*;
import static Utils.Colors.*;

public abstract class SGE{
    private final String admPass = "SGE2025".trim();
    private final String adminid = "15031".trim();


    protected void create(String name, String subject){


        if (!Validation.Name(name)) {
            System.out.println(RED + " O NOME QUE INSERIU É INVÁLIDO. REVEJA POR FAVOR O QUE INTRODUZIU. " + RESET);
            System.out.println();
            return;
        }
        if (Validation.Number(subject)) {
            if (!Validation.Subject(subject)) {
                System.out.println(RED + " O CURSO QUE INSERIU É INVÁLIDO. REVEJA POR FAVOR O QUE INTRODUZIU. " + RESET);
                return;
            }
        }
        else {
            System.out.println(RED + " A DISCIPLINA QUE INSERIU É INVÁLIDA. REVEJA POR FAVOR O QUE INTRODUZIU. " + RESET);
            return;
        }
        switch (subject){
            case "1" -> subject="MATEMÁTICA";
            case "2" -> subject="QUÍMICA";
            case "3" -> subject="INGLÊS";
            case "4" -> subject="FÍSICA";
            case "5" -> subject="ELETROTECNIA";
            case "6" -> subject="INFORMÁTICA";
            case "7" -> subject="SEAC";
            case "8" -> subject="PORTUGUES";
            case "9" -> subject="ANATOMIA";
            case "10" -> subject="POL";
        }
        int id = SGEService.gerarID();
        String pass = id + admPass ;
        System.out.println("ALUNO: " + name.toUpperCase());
        System.out.println("SENHA: " + pass);
        System.out.println("NÚMERO DE IDENTIFICADOR: " + id);
        System.out.println("SUBJECT: " + subject);
        System.out.println(VERDE + "*NOVO PROFESOR ADICIONADO COM SUCESSO*\n" + RESET);
        Teacher.listTeachers.add(new Teacher(name,id,pass,subject));

    }

    protected void create(String name, String course, String year) {

        if (!Validation.Name(name)) {
            System.out.println(RED + " O NOME QUE INSERIU É INVÁLIDO. REVEJA POR FAVOR O QUE INTRODUZIU. " + RESET);

            return;
        }
        if (Validation.Number(course)) {
            if (!Validation.Option(Integer.parseInt(course),1,4)){
                System.out.println(RED + " O CURSO QUE INSERIU É INVÁLIDO. REVEJA POR FAVOR O QUE INTRODUZIU. " + RESET);
                return;
            }
        } else {
            System.out.println(RED + " O CURSO QUE INSERIU É INVÁLIDO. REVEJA POR FAVOR O QUE INTRODUZIU. \n" + RESET);
            return;
        }
        if (Validation.Number(year)) {
            if (!Validation.Option(Integer.parseInt(year),10,13)) {
                System.out.println(RED + " DIGITE UM ANO DE ESCOLARIDADE VÁLIDO (ENTRE 10 E 13)." + RESET);
                return;
            }
        }
        else {
            System.out.println(RED+"DIGITE UM ANO DE ESCOLARIDADE VÁLIDO"+RESET);
            return;
        }
String turma = null;
        switch (course) {
            case "1" -> {
                course = "INFORMÁTICA";
                turma = "A";
            }
            case "2" -> {
                course = "ELETRÔNICA";
                turma = "D";
            }
            case "3" -> {
                course = "ELETROMEDICINA";
                turma = "E";
            }
            case "4" -> {
                course = "INFORMÁTICA E MULTIMÍDIA";
                turma = "M";
            }
            default ->
                System.out.println("ESCOLHA UM CURSO VÁLIDO");
        }

        long process = SGEService.gerarID();
        String pin = turma + process;

        Student.listStudent.add(new Student(name,pin,process,turma,course,Integer.parseInt(year)));

        System.out.println("ALUNO: " + name.toUpperCase());
        System.out.println("SENHA: " + pin);
        System.out.println("NÚMERO DE PROCESSO: " + process);
        System.out.println("TURMA: " + turma);
        System.out.println(VERDE + "---*ALUNO CADASTRADO COM SUCESSO*---" + RESET);
    }


    protected void show(boolean all, boolean students, boolean teachers, String id) {

        if (all && students) {
            System.out.println(VERDE+ "  ---LISTA DE ALUNOS---   \n"+RESET);
            for (Student studant : Student.listStudent ) {
                System.out.println(CYAN + " |NOME: "+studant.getName()+" | PROCESSO: "+studant.getProcessNumber()+" |" +
                        "TURMA: "+studant.Class+" | CURSO: "+studant.Course+" | ANO: "+studant.Year+RESET);
            }

        }

        else if (all && teachers) {
            System.out.println(ROXO + "   --LISTA DE PROFESSORES--  "+RESET);
            for (Teacher teacher : Teacher.listTeachers) {
                System.out.println(ROXO + " | NOME: "+teacher.getName()+" | IDENTIFICADOR -> "+teacher.getId()+" " +
                        "| DICIPLINA: "+teacher.subject +"    "+RESET);
            }
        }

        else {
            if (Validation.Number(id) && students) {
                Student studant = findStudent(id);
                if (studant==null) {
                    System.out.println(YELLOW+"   O ESTUDANTE NÃO FOI ENCONTRADO. \n"+RESET);
                }else {
                    System.out.println(VERDE+"| NOME: " + studant.getName() + " | PROCESSO -> "+studant.getProcessNumber()+" | CLASSE: " + studant.Year);
                    System.out.println("| TURMA: " + studant.Class + " | CURSO: " + studant.Course +RESET);
                }

            }

            else if(Validation.Number(id) && teachers) {
                Teacher teacher = findTeacher(id);
                if (teacher == null) {
                    System.out.println(YELLOW+" NÃO CONSEGUIMOS ENCONTRAR O PROFESSOR \n "+RESET);
                } else {
                    System.out.println(ROXO+teacher.getName()+" | IDENTIFICADOR -> "+teacher.getId()+
                            " | DISCIPLINA: "+teacher.subject+"   \n"+RESET);
                }
            }
            else System.out.println(RED + "ATENÇÃO! IDENTIFICADOR INVÁLIDO.\n"+RESET);
        }

    }

    protected  String update(Student student, boolean name, boolean curs, boolean year,boolean pass, String New){
        String updated = "INFORMAÇÃO";
        String sucess = "SUCESSO";

        if (name){
            updated = "NOME";
            if (Validation.Name(New)){
                student.setName(New);

            }else {
                System.out.println(YELLOW+"O NOME QUE INTRODUZIO É INVÁLIDO"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: NOME"+RESET;
            }
        }
        else if (pass) {
            updated = "PASS-WORD";
            if (Validation.Password(New,student.getPIN())) {
                student.setPIN(New);
            }else {
                System.out.println(RED+"PALAVRA-PASSE INVÁLIDA: NÃO DEFINIDA"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: PALAVRA_PASSE"+RESET;
            }
        }
        else if (curs) {
            updated = "CURSO";
            if( Validation.Number(New))
            if (Validation.Option(Integer.parseInt(New),1,4)){

                switch (New) {
                    case "1" -> {
                        New = "INFORMÁTICA";
                        student.Class = "A";
                    }
                    case "2" -> {
                        New = "ELETRÔNICA";
                        student.Class = "D";
                    }
                    case "3" -> {
                        New = "ELETROMEDICINA";
                        student.Class = "E";
                    }
                    case "4" -> {
                        New = "INFORMÁTICA E MULTIMÍDIA";
                        student.Class = "M";
                    }
                    default ->
                            System.out.println("ESCOLHA UM CURSO VÁLIDO");
                }
            }else {
                System.out.println(YELLOW+"O NOVO CURSO QUE ESCOLHEU É INVÁLIDO"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: CURSO"+RESET;
            }else {
                System.out.println(RED+"DIGITE UM NÚMERO"+RESET);
            }
            student.Course=New;
        }//COLOCAR AS OPCOES DE CURSOS

        else if (year){
            updated = "ANO";
            if (Validation.Number(New)){
                if (Validation.Option(Integer.parseInt(New),10,13)){
                    student.Year=Integer.parseInt(New);

                }else {
                    System.out.println(YELLOW+"O O NOVO ANO QUE INTRODUZIU É INVÁLIDO"+RESET);
                    return RED+"ERRO NA ATUALIZAÇÃO: ANO"+RESET;
                }
            }else {
                System.out.println(YELLOW+"DIGITE NÚMERO INVÉS DE LETRAS OU SIMBOLOS"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: ID"+RESET;
            }
        }
        else {
            sucess = "FALHA";
        }
        return VERDE +updated+" ATUALIZADO/A COM "+sucess+RESET;
    }//ALUNOS
    protected  String update(Teacher teacher, boolean name, boolean subject,boolean pass, String New){
        String updated = "INFORMAÇÃO";
        String sucess = "SUCESSO";

        if (name){updated = "NOME";

            if (Validation.Name(New)){
                teacher.setName(New);

            }else {
                System.out.println(YELLOW+"O NOME QUE INTRODUZIO É INVÁLIDO"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: NOME"+RESET;
            }
        }
        else if (pass) {
            updated = "PASS-WORD";
            if (Validation.Password(New,teacher.getPassword())) {
                teacher.setPassword(New);
            }else {
                System.out.println(RED+"PALAVRA-PASSE INVÁLIDA: NÃO DEFINIDA"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: PALAVRA_PASSE"+RESET;
            }
        }
       else if (subject) {
            updated = "DISCIPLINA";
            if(Validation.Number(New))
            if (Validation.Option(Integer.parseInt(New),0,0)){
                //COLOCAR AS OPÇÕES DE DISCIPLINA
            }else {
                System.out.println(YELLOW+"A DISCIPLINA QUE ESCOLHEU É INVÁLIDA"+RESET);
                return RED+"ERRO NA ATUALIZAÇÃO: DISCIPLINA"+RESET;
            }else {
                System.out.println(RED+"DIGITE UM NÚMERO"+RESET);
            }
        }
       else {
            sucess = "FALHA";
        }
        return VERDE +updated+" ATUALIZADO/A COM "+sucess+RESET;

    }//PROFESSORES


    protected void delete(boolean teachers, boolean students) {
        if (students) {
            Student.listStudent.clear();
        }
        else if (teachers) {
            Teacher.listTeachers.clear();
        }

    }
    protected void delete(boolean teachers, Teacher teacher,Student student) {
        if (teachers) {
            Teacher.listTeachers.remove(teacher);
            System.out.println(ROXO+"PROFESSOR ELIMINADO!"+RESET);
        }else {
            Student.listStudent.remove(student);
            System.out.println(ROXO+"ESTUDANTE ELIMINADO!"+RESET);
        }
    }

    protected Student findStudent(String process) {
        return Student.listStudent.stream()
                .filter(student -> String.valueOf(student.getProcessNumber()).equals(process))
                .findFirst().orElse(null);
    }
    protected Teacher findTeacher(String id){
        return Teacher.listTeachers.stream().filter(teachers -> String.valueOf(teachers.getId()).equals(id))
                .findFirst().orElse(null);
    }

    protected boolean LoginStudant(String id, String pass){
        if(findStudent(id) != null)
            return findStudent(id).getPIN().equals(pass) && !   Student.listStudent.isEmpty();
        return false;
    }
    protected boolean LoginTeacher(String id,String pass){
        if(findTeacher(id) != null)
            return findTeacher(id).getPassword().equals(pass) && !Teacher.listTeachers.isEmpty();
        return false;
    }
    public void add(boolean teacher, boolean studant) {
        Scanner scan = new Scanner(System.in);
        System.out.println("DIGITE O NOME: ");
        String nome = scan.nextLine();
        if (teacher) {
            System.out.println("DIGITE A DISCIPLINA");
            String disciplina = scan.nextLine();
            create(nome, disciplina);
            return;
        }
        if (studant) {
            System.out.println("INFORME O CURSO:");
            System.out.println("1 - INFORMÁTICA");
            System.out.println("2 - ELETRÔNICA E TELECOMUNICAÇÕES");
            System.out.println("3 - ELETROMEDICINA");
            System.out.println("4 - INFORMÁTICA E MULTIMÉDIA");
            String course = scan.nextLine();
            System.out.print("INFORME O ANO DE ESCOLARIDADE: ");
            String year = scan.nextLine();
            create(nome, course, year);
            return;
        }
        System.out.println(RED + "NADA MUDADO\n" + RESET);
    }

    protected String getAdminid() {
        return adminid;
    }
    protected String getAdmPass(){
        return admPass;
    }
}
