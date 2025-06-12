package Utils;


public class Validation {
    public boolean Number(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(input); // Ou Integer.parseInt(input) para números inteiros
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
    public boolean Name(String name) {
            if (name == null || name.trim().isEmpty()) {
                return false; // Rejeita valores nulos ou espaços vazios
            }

            // Remove espaços extras no início e fim
            name = name.trim();

            // Validação de tamanho (exemplo: mínimo 2 caracteres, máximo 50)
            if (name.length() < 2 || name.length() > 50) {
                return false;
            }

            // Expressão regular para validar nomes comuns
            String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ' ]+$";
            if (!name.matches(regex)) {
                return false; // Rejeita caracteres inválidos
            }

            // Evitar múltiplos espaços consecutivos
            if (name.contains("  ")) {
                return false;
            }

            // Evita nomes completamente numéricos, mas permite números mistos se necessário
            if (name.matches("^\\d+$")) {
                return false;
            }

            return true; // Se passou por todas as verificações, é válido!

    }

    public boolean Option(float escolha, float min, float max) {
        return escolha >= min && escolha <= max;
    }

    public boolean Password(String newPass, String oldPass) {
        return newPass != null && !newPass.equals(oldPass) && Name(newPass);
    }
    public boolean Subject(String subject){
        return subject!=null && Integer.parseInt(subject) > 1 && Integer.parseInt(subject) < 11;
    }

}
