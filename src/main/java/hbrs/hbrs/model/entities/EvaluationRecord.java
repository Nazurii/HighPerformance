package hbrs.hbrs.model.entities;

import org.bson.Document;

public class EvaluationRecord {

    private int goalId;
    private String goalDescr;
    private int tarVal;
    private int actVal;
    private int year;
    private int sid;

    public EvaluationRecord(int sid, int gid, String gd, int tv, int av, int y){
        this.sid = sid;
        this.goalId = gid;
        this.goalDescr = gd;
        this.tarVal = tv;
        this.actVal = av;
        this.year = y;
    }

    public EvaluationRecord(int sid, String gd, int tv, int av, int y){
        this.sid = sid;
        this.goalDescr = gd;
        this.tarVal = tv;
        this.actVal = av;
        this.year = y;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("goal_id" , this.goalId );
        document.append("goal_descr" , this.goalDescr );
        document.append("targetVal" , this.tarVal);
        document.append("actualVal" , this.actVal);
        document.append("year" , this.year);
        document.append("id" , this.sid);
        return document;
    }

    public static EvaluationRecord toEvalRec(Document doc){
        EvaluationRecord er = new EvaluationRecord((Integer) doc.get("id"), (Integer) doc.get("goal_id"), doc.getString("goal_descr"),
                (Integer) doc.get("targetVal"), (Integer) doc.get("actualVal"), (Integer) doc.get("year"));
        return er;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getGoalDescr() {
        return goalDescr;
    }

    public void setGoalDescr(String goalDescr) {
        this.goalDescr = goalDescr;
    }

    public int getTarVal() {
        return tarVal;
    }

    public void setTarVal(int tarVal) {
        this.tarVal = tarVal;
    }

    public int getActVal() {
        return actVal;
    }

    public void setActVal(int actVal) {
        this.actVal = actVal;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String toString(){
        return " Goal-ID: " + goalId + "\n Goal description: " + goalDescr + "\n Target value: " + tarVal + "\n Actual value: " + actVal + "\n Year: " + year;
    }
}
