package hbrs.hbrs.control;

import hbrs.hbrs.DAO.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);

        DAO.getInstance().initCon();
        TimeUnit.SECONDS.sleep(1); // Just so that the Logs won't get in the way
        System.out.println("HighPerformance V1.0 - c/o Marie Becker, 2020");
        System.out.println("Welcome!" + "\n" + "Type 'help' for a list of commands. \n");
        
        BufferedReader input = new BufferedReader( new InputStreamReader(System.in ) );
        String strInput = "";

        while ( true ) {
            try {
                System.out.print(">");
                strInput = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(strInput.equals("exit")){
                System.out.println("Good Bye!");
                break;
            }

            switch(strInput){
                case "help":
                    CommandControl.help();
                    break;
                case "create salesman":
                    CommandControl.createSalesman();
                    break;
                case "add performance record":
                    CommandControl.addPerfRec();
                    break;
                case "salesman" :
                    CommandControl.readSalesMan();
                    break;
                case "query salesmen" :
                    CommandControl.querySalesmen();
                    break;
                case "first performance record" :
                    CommandControl.readEvRecs();
                    break;
                case "update salesman" :
                    CommandControl.updateSalesman();
                    break;
                case "update performance record" :
                    CommandControl.updatePerfRec();
                    break;
                case "delete salesman" :
                    CommandControl.deleteSalesman();
                    break;
                case "delete performance record":
                    CommandControl.deleteEvalRec();
                    break;
                case "performance records":
                    CommandControl.readEvRecsList();
                    break;
                default: System.out.println("Unknown command!");
            }
        }
    }
}
