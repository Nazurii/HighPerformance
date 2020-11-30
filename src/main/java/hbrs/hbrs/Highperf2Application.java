package hbrs.hbrs;

import hbrs.hbrs.DAO.DAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Highperf2Application {

	public static void main(String[] args) {
		SpringApplication.run(Highperf2Application.class, args);
		DAO.getInstance().initCon();
	}

}
