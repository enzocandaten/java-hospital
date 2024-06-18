import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Database {
    public static boolean enviarParaArquivo(String valor, String arquivo) throws IOException {
        FileWriter file = new FileWriter(arquivo, true);
		PrintWriter fileOutputer = new PrintWriter(file);
		fileOutputer.print(valor);
		file.close();
		fileOutputer.close();
        return true;
    }

    public static String leDatabase(String arquivo) throws IOException {
        String stringPacientes = "";
		BufferedReader read = new BufferedReader(new FileReader(arquivo));
		String line;
	    while ((line = read.readLine()) != null) {
	    	stringPacientes += line;
	    }
	    read.close();

        return stringPacientes;
    }

	public static boolean existeNoArray(String[] arr, String elem) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(elem)) { return true; }
		}
		return false;
	}

	public static boolean existeNoArray(ArrayList<String> arr, String elem) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).equals(elem)) { return true; }
		}
		return false;
	}
}
