package hbrs.hbrs.control;

import hbrs.hbrs.DAO.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class MyConsole {
    private BufferedReader input;

    public MyConsole(){
        input = new BufferedReader( new InputStreamReader(System.in ) );
    }

    public String readLine(String prompt){
        String strInput = null;

        System.out.print( prompt );
        try {
            strInput = input.readLine();
            if(strInput.equals("")) readLine(prompt);
        } catch (IOException e) {
            System.out.println("Error while reading command!");
        }
        return strInput;
    }

    public int readLineInt(String prompt){
        String strInput = null;
        System.out.print(prompt);

        // Eingabe des Wertes
        try {
            strInput = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Umwandlung nach Integer --> int
        int id;
        try {
            id = Integer.parseInt(strInput);

        } catch (NumberFormatException e){
            System.out.println("Please enter numbers only!");
            return this.readLineInt(prompt);
        }
        return id;
    }

    public int readLineID(String prompt){
        String regex = "[0-9]{5}";
        Pattern pattern = Pattern.compile(regex);
        String strInput = readLine(prompt);
        int id;
        try {
            id = Integer.parseInt(strInput);
            if(!pattern.matcher(strInput).matches()){
                System.out.println("ID must be 5 digits long (00000-99999)!");
                return this.readLineID(prompt);
            }
        } catch (NumberFormatException e){
            System.out.println("Please enter numbers only!");
            return this.readLineID(prompt);
        }
        if(!DAO.getInstance().checkID(id)) {
            System.out.println("This ID is already in use!");
            return this.readLineID(prompt);
        }
        return id;
    }
}
