package hbrs.hbrs.control;

import hbrs.hbrs.model.entities.EvaluationRecord;
import hbrs.hbrs.model.entities.SalesMan;

import java.util.List;

public interface ManagePersonal {

    void createSalesMan(SalesMan record); //C

    void addPerformanceRecord(EvaluationRecord record); //C

    SalesMan readSalesMan(int sid);  //R

    List<SalesMan> querySalesMan(String attribute, String key);  //R

    EvaluationRecord readEvaluationRecords(int sid); //R



    void updateSalesman(SalesMan sm);   //U

    void updatePerformanceRecord(EvaluationRecord record);  //U

    void deleteSalesman(int sid);    //D

    void deleteRecord(int sid, int goalId);  //D

}
