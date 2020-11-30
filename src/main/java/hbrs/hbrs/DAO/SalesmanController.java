package hbrs.hbrs.DAO;

import hbrs.hbrs.model.entities.EvaluationRecord;
import hbrs.hbrs.model.entities.SalesMan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesman")
public class SalesmanController {

    @PostMapping
    public void createSalesMan(@RequestBody() SalesMan sm){
        DAO.getInstance().createSalesMan(sm);
    }


    @GetMapping
    public List<SalesMan> getAll(){
        return DAO.getInstance().readAllSalesmen();
    }

    @GetMapping("/{id}")
    public SalesMan getSalesman(@PathVariable() int id){
        return DAO.getInstance().readSalesMan(id);
    }

    @GetMapping("/{id}/records")
    public List<EvaluationRecord> getOnesRecords(@PathVariable int id){
        return DAO.getInstance().readEvalutaionRecordsList(id);
    }

    @GetMapping("/{key}/{attribute}")
    public List<SalesMan> querySalesmen(@PathVariable String key, @PathVariable String attribute){
        return DAO.getInstance().querySalesMan(attribute, key);
    }


    @PutMapping("/{id}")
    public void updateSalesman(@PathVariable() int id, @RequestBody() SalesMan sm){
        DAO.getInstance().updateSalesman(sm);
    }

    @DeleteMapping("/{id}")
    public void deleteSalesman(@PathVariable() int id) {
        DAO.getInstance().deleteSalesman(id);
    }
}
