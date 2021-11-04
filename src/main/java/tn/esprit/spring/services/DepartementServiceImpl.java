package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class DepartementServiceImpl implements IDepartementService {

/*private static final Logger l = LogManager.getLogger(DepartementServiceImpl.class);*/
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	@Override
	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}

	@Override
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	@Override
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		        
				Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
				deptRepoistory.findById(depId).ifPresent(departement -> { Departement depManagedEntity; depManagedEntity = departement ;
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);
				});
				
				
		
	}

	@Transactional
	public void deleteDepartementById(int depId) {
	
		
		deptRepoistory.findById(depId).ifPresent(departement -> deptRepoistory.delete(departement))	;
		
		
	}

	@Override
	public Departement getDepartementById(int depId) {
		 return deptRepoistory.findById(depId).get();
		
	}
	
	
	
}
