package tn.esprit.spring;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;
@SpringBootTest
@RunWith(SpringRunner.class)

public class EmployeTest {
	@Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private EmployeServiceImpl employeService;
    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    EntrepriseServiceImpl entrepriseService;
    private final static Logger l = LogManager.getLogger(EmployeTest.class);
    
    @Test
    void ajoutEmploye() {
        var employe = new Employe("houda", "bettayeb", "houda.bettayeb@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        employeService.ajouterEmploye(employe);
        long start = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - start;
        l.info("Method execution time: " + elapsedTime + " milliseconds.");
        l.info("l'employé est ajouté");
    }

    @Test
  public  void getNombreEmploye() {
        int nombre = employeService.getNombreEmployeJPQL();
        Employe employe = new Employe("houda", "bettayeb", "houda.bettayeb@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        employeService.ajouterEmploye(employe);
        int secondNombre = employeService.getNombreEmployeJPQL();
        Assert.assertNotEquals(nombre, secondNombre);
        l.info("le nombre des employés est : " + nombre);
    }

    @Test
  public  void AjouterContrat() {
        Date date = new Date();
        Contrat contrat = new Contrat(date, "CDI", 20000);
        int resultat = employeService.ajouterContrat(contrat);
        Assert.assertEquals(resultat, contrat.getReference() );
        l.info("le contrat ajoute avec succes");
    }

    @Test
   public void affecterContratAEmploye() {
        Employe employe = new Employe("houda", "bettayeb", "houda.bettayeb@esprit.tn", true, 
Role.CHEF_DEPARTEMENT);
        Date date = new Date();
        Contrat contrat = new Contrat(date, "CDD", 1000);
        employeService.ajouterEmploye(employe);
        employeService.ajouterContrat(contrat);
        employeService.affecterContratAEmploye(contrat.getReference(), employe.getId());
        l.info("le contrat d'id" + contrat.getReference() + "est bien affecté à l'employé d'id: " + employe.getId());
    }

    @Test
    void affecterEmployeADepartement() {
        Employe employe = new Employe("houda", "bettayeb", "houda.bettayeb@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        employeService.ajouterEmploye(employe);
        Departement departement = new Departement("testing");
        entrepriseService.ajouterDepartement(departement);
        employeService.affecterEmployeADepartement(employe.getId(), departement.getId());
        l.info("l'employé qui a l'id: " + employe.getId() + " est affecté au département avec l'id : " + departement.getId());
    }

    @Test
  public  void getSalaireByEmployeIdJPQL() {
        Employe employe = new Employe("houda", "bettayeb", "houdabettayeb@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        employeService.ajouterEmploye(employe);
        Date date = new Date();
        Contrat contrat = new Contrat(date, "CDD", 1000);
        employeService.ajouterContrat(contrat);
        employeService.affecterContratAEmploye(contrat.getReference(), employe.getId());
        float salaire1=0 ;
        float salaire = employeService.getSalaireByEmployeIdJPQL(employe.getId());
        Assert.assertNotEquals(salaire, salaire1);
        l.info("l'employé d'id: " + employe.getId() + " a un salaire de " + salaire);
    }

    @Test
  public  void DesaffecterEmployeDuDepartement() {
        Employe employe = new Employe("houda", "bettayeb", "houdabettayeb@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        employeService.ajouterEmploye(employe);
        Entreprise entreprise = new Entreprise("premiére entreprise", "raison");
        Departement departement = new Departement("Geo");
        departement.setEntreprise(entreprise);
        entrepriseService.ajouterEntreprise(entreprise);
        entrepriseService.ajouterDepartement(departement);
        employeService.desaffecterEmployeDuDepartement(employe.getId(), departement.getId());
        l.info("l'employé qui a l'id : " + employe.getId() + " est désaffecté du département qui a l'id : " + departement.getId());
    }

    @Test
    
   public void DeleteContrat() {
        Date date = new Date();
        Contrat contrat = new Contrat(date, "CDD", 1000);
        employeService.ajouterContrat(contrat);
        boolean existBeforeDelete = contratRepository.findById(contrat.getReference()).isPresent();
        employeService.deleteContratById(contrat.getReference());
        boolean existAfterDelete = contratRepository.findById(contrat.getReference()).isPresent();
        Assert.assertTrue(existBeforeDelete);
        Assert.assertFalse(existAfterDelete);
        l.info("Le contrat est supprimé");
    }

    @Test
   public void DeleteEmploye() {
        Employe employe = new Employe("houda", "bettayeb", "houdabettayeb@esprit.tn", true, Role.CHEF_DEPARTEMENT);
        employeService.ajouterEmploye(employe);
        boolean existBeforeDelete = employeRepository.findById(employe.getId()).isPresent();
        Assert.assertTrue(existBeforeDelete);
        employeService.deleteEmployeById(employe.getId());
        boolean existAfterDelete = employeRepository.findById(employe.getId()).isPresent();
        Assert.assertFalse(existAfterDelete);
        l.info("L'employé est supprimé");
    }

    @Test
  public  void DeleteAllContrat() {
        employeService.deleteAllContratJPQL();
        Assert.assertEquals("[]", contratRepository.findAll().toString());
        l.info("tous les contrats sont supprimés");
    }
    

}


