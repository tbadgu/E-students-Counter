package db;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;

public class InputHelper {
	
	static BufferedReader stdin = new BufferedReader(
			new InputStreamReader(System.in));

	public static String getInput(String prompt) {
		

		System.out.print(prompt);
		System.out.flush();

		try {
			return stdin.readLine();
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	public static double getDoubleInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		return Double.parseDouble(input);

	}

	public static int getIntegerInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		return Integer.parseInt(input);	
	}
	
	public static char getCharInput(String prompt) throws IOException{
		
		char input=(char) (stdin.read());
		return input;
	}
	
	public static Date getDateInput(java.util.Date ip){
		@SuppressWarnings("deprecation")
		Date input=new Date(ip.getYear(),ip.getMonth(),ip.getDate());
		return input;
	}
}

