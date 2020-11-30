package hbrs.hbrs.control;

import hbrs.hbrs.DAO.DAO;
import hbrs.hbrs.model.entities.EvaluationRecord;
import hbrs.hbrs.model.entities.SalesMan;

import java.util.List;

public class CommandControl {

    private CommandControl(){}

    public static void createSalesman() {
        MyConsole con = new MyConsole();
        String first = con.readLine("First name: ");
        String last = con.readLine("Last name: ");
        int id = con.readLineID("ID: ");
        SalesMan sm = new SalesMan(first, last, id);
        DAO.getInstance().createSalesMan(sm);
    }

    //TODO value einschränkung?
    //Datum einschränken, anderer Datentyp
    public static void addPerfRec(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("Salesman ID: ");
        String goalDescr = con.readLine("Goal description: ");
        int targetVal = con.readLineInt("Target value: ");
        int actualVal = con.readLineInt("Actual value: ");
        int year = con.readLineInt("Year: ");

        EvaluationRecord er = new EvaluationRecord(sid, goalDescr, targetVal, actualVal, year);
        DAO.getInstance().addPerformanceRecord(er);
    }

    public static void readSalesMan(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("ID of wanted salesman: ");
        SalesMan sm = DAO.getInstance().readSalesMan(sid);
        if(sm!=null) System.out.println(sm);

    }

    public static void querySalesmen(){
        MyConsole con = new MyConsole();
        System.out.println("Possible keys: firstname, lastname");
        String key = con.readLine("Key: ");
        String attribute = con.readLine("Attribute: ");
        List<SalesMan> list = DAO.getInstance().querySalesMan(attribute, key);
        final String leftAlignFormat = "| %-5d | %-30s | %-30s |%n";
        System.out.format("+-------+--------------------------------+--------------------------------+%n");
        System.out.format("|  ID   | First Name                     | Last Name                      |%n");
        System.out.format("+-------+--------------------------------+--------------------------------+%n");
        list.forEach(SalesMan -> System.out.format(leftAlignFormat, SalesMan.getId(), SalesMan.getFirstname(), SalesMan.getLastname()));
        System.out.format("+-------+--------------------------------+--------------------------------+%n");
    }

    public static void readEvRecs(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("Salesman ID: ");
        SalesMan sm = DAO.getInstance().readSalesMan(sid);
        EvaluationRecord er = DAO.getInstance().readEvaluationRecords(sid);
        if(er!= null){
            System.out.println("Evaluation Record for salesman " + sm.getFirstname() + " " + sm.getLastname() + ": ");
            System.out.println(DAO.getInstance().readEvaluationRecords(sid));
        }
    }

    public static void readEvRecsList(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("ID of salesman, whose records are requested: ");
        EvaluationRecord er = DAO.getInstance().readEvaluationRecords(sid);
        List<EvaluationRecord> list = DAO.getInstance().readEvalutaionRecordsList(sid);
        if(er!= null){
            final String leftAlignFormat = "| %-2d | %-41s | %-12d | %-12d | %-4d |%n";
            System.out.format("+----+-------------------------------------------+--------------+--------------+------+%n");
            System.out.format("| ID | Goal description                          | Target value | Actual Value | Year |%n");
            System.out.format("+----+-------------------------------------------+--------------+--------------+------+%n");
            list.forEach(EvaluationRecord -> System.out.format(leftAlignFormat, EvaluationRecord.getGoalId(), EvaluationRecord.getGoalDescr(),
            EvaluationRecord.getTarVal(), EvaluationRecord.getActVal(), EvaluationRecord.getYear()));
            System.out.format("+----+-------------------------------------------+--------------+--------------+------+%n");
        }
    }

    public static void updateSalesman(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("ID of salesman: ");
        SalesMan sm = DAO.getInstance().readSalesMan(sid);
        String option = con.readLine("Which attribute do you want to change? (firstname/lastname)");

        switch(option){
            case "firstname" :
                sm.setFirstname(con.readLine("What do you want the first name (currently " + sm.getFirstname() + ") change to?\n>"));
                break;
            case "lastname" :
                sm.setLastname(con.readLine("What do you want the last name (currently " + sm.getLastname() + ") change to?\n>"));
                break;
            default:
                System.out.println("Process canceled.");
        }
        DAO.getInstance().updateSalesman(sm);
        System.out.println("Salesmen was updated successfully. Updated salesman data:");
        System.out.println(DAO.getInstance().readSalesMan(sm.getId()));
    }

    public static void updatePerfRec(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("ID of salesman's evaluation record: ");
        int gid = con.readLineInt("Goal-ID of performance record: ");
        EvaluationRecord er = DAO.getInstance().readRecordByGoalId(sid, gid);
        //EvaluationRecord er = DAO.getInstance().readEvaluationRecords(sid);
        String option = con.readLine("Which attribute do you want to change? (goaldescription/targetvalue/actualvalue/year)\n");

        switch(option){
            case "goaldescription" :
                er.setGoalDescr(con.readLine("What do you want the goal description change to?\nCurrently it's " + er.getGoalDescr()));
                break;
            case "targetvalue" :
                er.setTarVal(con.readLineInt("What do you want the target value (currently " + er.getTarVal() + ") change to?\n"));
                break;
            case "actualvalue":
                er.setActVal(con.readLineInt("What do you want the actual value (currently " + er.getActVal() + ") change to?\n"));
                break;
            case "year":
                er.setYear(con.readLineInt("What do you want the year (currently " + er.getYear() + "} change to?\n"));
                break;
            default:
                System.out.println("Process cancelled.");
        }
        DAO.getInstance().updatePerformanceRecord(er);
        System.out.println("Evaluation record was updated successfully. Updated record data: ");
        System.out.println(er);
    }

    public static void deleteSalesman(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("ID of the salesman to be deleted: ");

        SalesMan sm = DAO.getInstance().readSalesMan(sid);
        if(sm==null) return;
        String s = con.readLine("Do you really want to delete salesman " + sm.getFirstname() + " " + sm.getLastname() + " (" + sm.getId() + ")? (y/n)\n>");
        if(s.equals("y")){
            DAO.getInstance().deleteSalesman(sid);
            System.out.println("Salesman was deleted successfully.");
        }
        else System.out.println("Process cancelled.");
    }

    public static void deleteEvalRec(){
        MyConsole con = new MyConsole();
        int sid = con.readLineInt("ID of affected salesman: ");
        int goalId = con.readLineInt("ID of the record to be deleted: ");

        DAO.getInstance().deleteRecord(sid, goalId);
        System.out.println("Performance record was deleted successfully.");
    }

    public static void help(){
        System.out.println(
                        "add performance record             Create a new performance record for a salesman." + "\n" +
                        "create salesman                    Create a new salesman." + "\n" +
                        "delete performance record          Delete performance record by salesman- and goal-ID." + "\n" +
                        "delete salesman                    Delete salesman by ID." + "\n" +
                        "exit                               Exit the program." + "\n" +
                        "first performance record           Show the first performance record of a salesman." + "\n"+
                        "help                               Show list of commands." + "\n" +
                        "performance records                Show all performance records of a salesman." + "\n" +
                        "query salesmen                     Show all salesmen who match a certain attribute." + "\n" +
                        "update performance record          Change the attributes of a certain performance report." + "\n" +
                        "update salesman                    Change the first or last name of a salesman." + "\n" +
                        "salesman                           Show information of a certain salesman." + "\n");
    }
}
