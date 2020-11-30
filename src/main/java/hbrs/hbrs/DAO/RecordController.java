package hbrs.hbrs.DAO;

import hbrs.hbrs.model.entities.EvaluationRecord;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @PostMapping
    public void createRecord(@RequestBody()EvaluationRecord ev){
        DAO.getInstance().addPerformanceRecord(ev);
    }

    @GetMapping("/{id}")
    public List<EvaluationRecord> getAllRecords(@PathVariable() int id){
        return DAO.getInstance().readEvalutaionRecordsList(id);
    }

    @GetMapping("/{id}/{goalid}")
    public EvaluationRecord getOneRecord(@PathVariable() int id, @PathVariable int goalid){
        return DAO.getInstance().readRecordByGoalId(id, goalid);
    }

    @PutMapping("/{id}/{goalid}")
    public void updateRecord(@RequestBody EvaluationRecord rec){
        DAO.getInstance().updatePerformanceRecord(rec);
    }

    @DeleteMapping("/{id}/{goalid}")
    public void deleteRecord(@PathVariable() int id, @PathVariable int goalid){
        DAO.getInstance().deleteRecord(id, goalid);
    }

}
