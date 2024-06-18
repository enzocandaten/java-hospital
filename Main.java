import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static String PACIENTES;
    public static String CONSULTAS;

    public static void main(String[] args) throws IOException {
		FileWriter pacientes = new FileWriter("pacientes.txt", true);
		FileWriter consultas = new FileWriter("consultas.txt", true);
		pacientes.close();
		consultas.close();

        try { PACIENTES = Database.leDatabase("pacientes.txt"); } catch (IOException error) { System.out.println(error); }
		try { CONSULTAS = Database.leDatabase("consultas.txt"); } catch (IOException error) { System.out.println(error); }

        Scanner mainScan = new Scanner(System.in);
		while (true) {
			//gera o menu inical
			System.out.print("1 - Paciente\n");
			System.out.print("2 - Consulta\n");
			System.out.print("3 - Relatório por paciente\n");
			System.out.print("4 - Relatório por cidade\n");
			System.out.print("5 - Sair\n");

			//faz a escolha dos itens
			String choice = mainScan.nextLine();
			if (choice.equals("1")) {
				cadastrarPaciente();
			} else if (choice.equals("2")) {
			 	marcarConsulta();
			} else if (choice.equals("3")) {
				relatorioPorPaciente();
			} else if (choice.equals("4")) {
				RelatorioPorCidade();
			}
			else if (choice.equals("5")) { break; }
			else {
				System.out.print("Por favor insira uma opção válida\n");
			}
		}
		mainScan.close();
    }

    public static boolean cadastrarPaciente() throws IOException {
        Paciente paciente = new Paciente();
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Qual o cpf do paciente: ");
        paciente.cpf = scan.nextLine();
        System.out.print("Qual o nome do paciente: ");
        paciente.nome = scan.nextLine();
        System.out.print("Qual a cidade do paciente: ");
        paciente.cidade = scan.nextLine();
        System.out.print("Qual o aniversario do paciente: ");
        paciente.nascimento = scan.nextLine();

		Database.enviarParaArquivo(paciente.getData(), "pacientes.txt");

		return true;
    }

	public static boolean marcarConsulta() throws IOException {
		Consulta consulta = new Consulta();
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Qual o cpf do paciente: ");
        consulta.cpf = scan.nextLine();
		
		//checa se o CPF digitado é valido no contexto atual
		String[] pacientes = Database.leDatabase("pacientes.txt").split(";");
		if (!Database.existeNoArray(pacientes, consulta.cpf)) { System.out.println("Esse usuário não existe no nosso sistema"); return false; }

		//continua perguntando o resto das informações
        System.out.print("Qual a data da consulta: ");
        consulta.data = scan.nextLine();
        System.out.print("Qual a cidade que vai ser marcada: ");
        consulta.cidade = scan.nextLine();

		//pergunta qual o tipo da cirgirgia
		System.out.print("Qual o tipo da cirurgia: \n"
						+ "1 - Joelho\n"
						+ "2 - Tornozelo\n"
						+ "3 - Cotovelo\n"
						+ "4 - Ombro\n"
						+ "Reposta: ");
		consulta.consultaTipo = scan.nextLine();
		switch (consulta.consultaTipo) {
			case "1":
				consulta.consultaTipo = "Joelho";
				break;
			case "2":
				consulta.consultaTipo = "Tornozelo";
				break;
			case "3":
				consulta.consultaTipo = "Cotovelo";
				break;
			case "4":
				consulta.consultaTipo = "Ombro";
				break;
			default:
				System.out.print("Digite uma resposta válida!\n");
		}
		Database.enviarParaArquivo(consulta.getData(), "consultas.txt");

		return true;
	}

	public static boolean relatorioPorPaciente() throws IOException {
		String[] pacientesDB = Database.leDatabase("pacientes.txt").split("#;");
		String[] consultasDB = Database.leDatabase("consultas.txt").split("#;");

		for (int p = 0; p < pacientesDB.length; p++) {
			String[] paciente = pacientesDB[p].split(";");//cada paciente é representado por essa variavel
																//agora eu vou rodar por todas as consultas procurando
																//pelo mesmo cpf que esse cara tem
			System.out.println(String.format("%s - %s", paciente[1], paciente[2]));
			for (int c = 0; c < consultasDB.length; c++) {
				String[] consulta = consultasDB[c].split(";");
				if (consulta[0].equals(paciente[0])) {
					System.out.println(String.format("%s - %s", consulta[1], consulta[3]));
				}
			}
		}

		return true;
	}

	public static boolean RelatorioPorCidade() throws IOException {
		String[] pacientesDB = Database.leDatabase("pacientes.txt").split("#;");
		String[] consultasDB = Database.leDatabase("consultas.txt").split("#;");
		ArrayList<String> cidades = new ArrayList<String>();

		for (int c = 0; c < consultasDB.length; c++) {
			String[] consulta = consultasDB[c].split(";");
			if (!Database.existeNoArray(cidades, consulta[2])) { cidades.add(consulta[2]); }
		}

		for (int i = 0; i < cidades.size(); i++) {
			int count = 0;

			for (int c = 0; c < consultasDB.length; c++) {
				String[] consulta = consultasDB[c].split(";");
				if (consulta[2].equals(cidades.get(i))) { count++; }
			}

			System.out.println(String.format("%s - %d consultas", cidades.get(i), count));
		}

		return true;
	}

    
}