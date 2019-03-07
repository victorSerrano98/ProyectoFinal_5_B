/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author VICTOR SERRANO
 */
public class CrudService {
    
    static final Logger LOGGER = Logger.getLogger(CrudService.class.getSimpleName());
    public static final String PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";
    public static final String PERSISTENCE_LOADGRAPH = "javax.persistence.loadgraph";
    public static final String PERSISTENCE_NAME = "ProyectoFinalPU";

    EntityManager em;
    
    private void setUp(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        em = emf.createEntityManager();
    }

    public CrudService() {
        setUp();
    }
    
    public <T> T create(T t) {
//LOGGER.log(Level.INFO, "sing in create: {0}", t);
        this.em.getTransaction().begin();
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        this.em.getTransaction().commit();
        return t;
    }
    
    public <T> T update(T t) {
//LOGGER.log(Level.INFO, "sing in update: {0}", t); 
        this.em.getTransaction().begin();
        T updated = this.em.merge(t);
        this.em.flush();
        this.em.getTransaction().commit();
        return updated;
    }
    
    public <T> T find(Class<T> type, Object id) {
        this.em.getTransaction().begin();
        T result = (T) this.em.find(type, id);
        this.em.getTransaction().commit();
        return result;
    }
    
    public <T> Boolean delete(Class<T> type, Object id) {
        this.em.getTransaction().begin();
        Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
        this.em.getTransaction().commit()   ;
        return true;
    }
    
    public <T> List<T> findWithQuery(String queryString) {
        this.em.getTransaction().begin();
        Query query = this.em.createQuery(queryString);
        List<T> lista = query.getResultList();
        this.em.getTransaction().commit();
        return lista;
    }
    
    
}
