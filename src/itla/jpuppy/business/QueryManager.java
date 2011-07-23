package itla.jpuppy.business;

import itla.jpuppy.datalayer.*;
import java.util.LinkedList;

import java.util.List;
import java.util.concurrent.CancellationException;
import javax.persistence.EntityManager;

public class QueryManager {

    private EntityManager entityManager;
    

    public QueryManager() {
        entityManager = EntityManagerCreator.getInstanceEM();
    }

    public boolean saveObject(Object object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean deleteObject(Object object) {
        boolean state = true;
        entityManager.getTransaction().begin();
        try{
        entityManager.remove(object);
        entityManager.getTransaction().commit();
        }catch( Exception  e){
            state = false;
        }
        return state;
    }

    public boolean updateObject(Object object) {
        boolean state= true;
        entityManager.getTransaction().begin();
        try{
        entityManager.merge(object);
        entityManager.getTransaction().commit();
        }catch( Exception f ){
            state = false;
                    }
        return state;
    }
    
    //Metodos Disponibles para autocomplete en busqueda
    
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public List<Users> searchUser(String name) {
       List<Users> listUsers =  entityManager.createQuery("SELECT a FROM Users a WHERE a.name LIKE :nameToFind").setParameter("nameToFind", name).getResultList();
       return listUsers;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public List<Customers> searchCustomer(String name) {
        List<Customers> listCustomers =  entityManager.createQuery("SELECT a FROM Customers a WHERE a.name LIKE :nameToFind").setParameter("nameToFind", name).getResultList();
        return listCustomers;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
    public List<Patients> searchPatient(String patientsId) {
         List<Patients> listPatients =  entityManager.createQuery("SELECT a FROM Patients a WHERE a.patientsId LIKE :nameToFind").setParameter("nameToFind", patientsId).getResultList();
        return listPatients;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
    public List<Species> searchSpecie(String name) {
         List<Species> listSpecieses =  entityManager.createQuery("SELECT a FROM Species a WHERE a.speciesName LIKE :nameToFind").setParameter("nameToFind", name).getResultList();
        return listSpecieses;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 
    public List<Breeds> searchBreeds(String name) {
        List<Breeds> listBreeds =  entityManager.createQuery("SELECT a FROM Breeds a WHERE a.breedsName LIKE :nameToFind").setParameter("nameToFind", name).getResultList();
        return listBreeds;
       
    }
    @Override 
      protected void finalize(){
        entityManager.close();
    }
   
}
