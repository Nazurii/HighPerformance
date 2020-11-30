package hbrs.hbrs.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hbrs.hbrs.control.ManagePersonal;
import hbrs.hbrs.model.entities.EvaluationRecord;
import hbrs.hbrs.model.entities.SalesMan;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class DAO implements ManagePersonal {


    private MongoCollection<Document> salesmen;
    private MongoCollection<Document> evaluationRecords;

    private static DAO dao = null;

    public static DAO getInstance(){
        if(dao==null) dao = new DAO();
        return dao;
    }

    public void initCon(){
        // Setting up the connection to a local MongoDB with standard port 27017
        // must be started within a terminal with command 'mongod'
        MongoClient client = new MongoClient("localhost", 27017);

        // Get database 'highperformance' (creates one if not available)
        MongoDatabase supermongo = client.getDatabase("highperformance");

        // // Get Collection 'salesmen' (creates one if not available)
        salesmen = supermongo.getCollection("salesmen");
        evaluationRecords = supermongo.getCollection("evaluationRecords");
    }

    public void createSalesMan(SalesMan record) {
        salesmen.insertOne(record.toDocument());
        System.out.println("Salesmen successfully added.");
    }

    public void addPerformanceRecord(EvaluationRecord record) {
        //Get the latest/max goal-id for the salesman in question. If there's none, group-id will be 1, else max+1
        Document maxDoc = this.evaluationRecords.find(eq("id", record.getSid())).sort(new BasicDBObject("goal_id", -1)).limit(1).first();
        if(maxDoc == null) record.setGoalId(1);
        else record.setGoalId((Integer) maxDoc.get("goal_id")+1);
        Document doc = record.toDocument();
        evaluationRecords.insertOne(doc);
        System.out.println("Evaluation record successfully added.");
    }

    public SalesMan readSalesMan(int sid) {
        Document doc = this.salesmen.find(eq("id", sid)).first();
        if(doc==null){
            System.out.println("There is no salesman with the ID " + sid + ".");
            return null;
        }
        return SalesMan.toSalesman(doc);
    }

    public List<SalesMan> querySalesMan(String attribute, String key) {
        final List<SalesMan> list = new ArrayList<>();
        Block<Document> block = document -> list.add(SalesMan.toSalesman(document));
        this.salesmen.find(eq(key, attribute)).forEach(block);
        return list;
    }

    public EvaluationRecord readEvaluationRecords(int sid) {
        Document doc = this.evaluationRecords.find(eq("id", sid)).first();
        if(doc==null){
            System.out.println("There are no evaluation records for the salesman with the ID " + sid + ".");
            return null;
        }
        return EvaluationRecord.toEvalRec(doc);
    }

    public EvaluationRecord readRecordByGoalId(int sid, int goalid){
        Document doc = this.evaluationRecords.find(and(eq("id", sid), eq("goal_id", goalid))).first();
        if(doc == null){
            System.out.println("There is no evaluation record with the goal-ID " + goalid + " for the salesman with the ID " + sid + ".");
            return null;
        }
        return EvaluationRecord.toEvalRec(doc);
    }

    public List<EvaluationRecord> readEvalutaionRecordsList(int sid){
        List<EvaluationRecord> list = new ArrayList<>();
        Block<Document> block = document -> list.add(EvaluationRecord.toEvalRec(document));
        this.evaluationRecords.find(eq("id", sid)).forEach(block);
        return list;
    }




    public void updateSalesman(SalesMan sm) {
        salesmen.replaceOne(eq("id", sm.getId()),sm.toDocument());
    }


    public void updatePerformanceRecord(EvaluationRecord record) {
        evaluationRecords.replaceOne(and(eq("id", record.getSid()), eq("goal_id", record.getGoalId())), record.toDocument());
    }


    public void deleteSalesman(int sid) {
        this.salesmen.deleteOne(eq("id", sid));

        //Alle zugehörigen Performance Records werden ebenfalls gelöscht
        this.evaluationRecords.deleteMany(eq("id", sid));
    }

    public List<SalesMan> readAllSalesmen() {
        FindIterable<Document> docs = this.salesmen.find();
        List<SalesMan> sm = new ArrayList<>();
        Iterator it = docs.iterator();
        while(it.hasNext()){
            sm.add(SalesMan.toSalesman((Document) it.next()));
        }
        return sm;
    }


    public void deleteRecord(int sid, int goalId) {
        this.evaluationRecords.deleteOne(and(eq("id", sid), eq("goal_id", goalId)));
    }

    //Check if there's already a salesman with this ID
    public boolean checkID(int sid){
        Document doc = this.salesmen.find(eq("id", sid)).first();
        return doc == null;
    }

}
