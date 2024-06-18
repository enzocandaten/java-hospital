public class Consulta {
    public String cpf;
    public String data;
    public String cidade;
    public String consultaTipo;

    public String getData() {
        return String.format("%s;%s;%s;%s;#;", cpf, data, cidade, consultaTipo);
    }
}
