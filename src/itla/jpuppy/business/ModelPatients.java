package itla.jpuppy.business;

import itla.jpuppy.datalayer.Customers;
import itla.jpuppy.datalayer.Patients;
import java.util.List;

public class ModelPatients implements GeneralModel {

    private QueryManager queryManager;
    private Patients patient;

    public ModelPatients() {
        queryManager = new QueryManager();
        patient = new Patients();
    }

    public QueryManager getQueryManager() {
        return queryManager;
    }

    //Este metodo todavia no estas totalmente definido , se necesita especificar si id seria unico
    public Patients searchPatient(Long patientsId) {
        Patients patient = queryManager.searchPatient(patientsId);
        return patient;
    }

    // retorna lista de patient para autocomplete
     public List<Patients> searchAllPatientByNameExplicit(String name) {
        return queryManager.searchPatientByName(name);
    }
        public List<Patients> searchAllPatient() {
        return queryManager.searchAllPatient();
    }
    
    public List<Patients> searchAllPatientByName(String name) {
        return queryManager.searchPatientByName(name);
    }
    public Patients getPatientsByName( String name ){
        Patients p = null;
                List<Patients> list = this.searchAllPatientByName(name);
                p = list.get(0);
                return p;
    }

    //Metodos comunes a todos los modelos , se llama el correspondiente de  queryManager
    @Override
    public boolean insertObject(Object object) {
        Patients g = (Patients) object;
        return queryManager.saveObject(g);
    }

    @Override
    public boolean updateObject(Object object) {
        Patients g = (Patients) object;
        return queryManager.updateObject(g);
    }

    @Override
    public boolean deleteObject(Object object) {
        Patients g = (Patients) object;
        return queryManager.deleteObject(g);
    }

    public Patients getPatient() {
        return patient;
    }

    //metodo para buscar un cliente por el id del paciente
    public Customers getCustomerbyPatientId(int id) {
        return new Customers();
    }
}
