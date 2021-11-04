package tn.esprit.spring.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.DepartementServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;


@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartementTest {
	

@Autowired
private  EntrepriseServiceImpl entrepriseservice ;
@Autowired
private  DepartementServiceImpl departementservice ;
@Autowired
private DepartementRepository departmentRep;
private static final Logger l = LogManager.getLogger(DepartementTest.class);

	 @Test
	  public  void Add_Departement() 
	 {
	        Departement dep = new Departement("dev");
	        departementservice.ajouterDepartement(dep);
	        departementservice.deleteDepartementById(dep.getId());
	        l.info("your departement added avec succes");
	        
	    }
	


}
