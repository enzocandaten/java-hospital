public class Paciente {
    public String cpf;
    public String nome;
    public String cidade;
    public String nascimento;

    public String getData() {
        return String.format("%s;%s;%s;%s;#;", cpf, nome, cidade, nascimento);
    }
}
