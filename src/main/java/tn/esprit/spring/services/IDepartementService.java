package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Departement;


public interface IDepartementService {

	int ajouterDepartement(Departement dep);

	List<String> getAllDepartementsNamesByEntreprise(int entrepriseId);

	void affecterDepartementAEntreprise(int depId, int entrepriseId);

	void deleteDepartementById(int depId);
	public Departement getDepartementById(int depId);


	

}
