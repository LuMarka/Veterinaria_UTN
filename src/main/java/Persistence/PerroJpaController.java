/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Cliente;
import Entities.Perro;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
public class PerroJpaController implements Serializable {

    public PerroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PerroJpaController() {
        emf = Persistence.createEntityManagerFactory("VeterinariaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perro perro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = perro.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                perro.setIdCliente(idCliente);
            }
            em.persist(perro);
            if (idCliente != null) {
                idCliente.getMascotas().add(perro);
                idCliente = em.merge(idCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perro perro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perro persistentPerro = em.find(Perro.class, perro.getIdMascota());
            Cliente idClienteOld = persistentPerro.getIdCliente();
            Cliente idClienteNew = perro.getIdCliente();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                perro.setIdCliente(idClienteNew);
            }
            perro = em.merge(perro);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getMascotas().remove(perro);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getMascotas().add(perro);
                idClienteNew = em.merge(idClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = perro.getIdMascota();
                if (findPerro(id) == null) {
                    throw new NonexistentEntityException("The perro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perro perro;
            try {
                perro = em.getReference(Perro.class, id);
                perro.getIdMascota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perro with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = perro.getIdCliente();
            if (idCliente != null) {
                idCliente.getMascotas().remove(perro);
                idCliente = em.merge(idCliente);
            }
            em.remove(perro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perro> findPerroEntities() {
        return findPerroEntities(true, -1, -1);
    }

    public List<Perro> findPerroEntities(int maxResults, int firstResult) {
        return findPerroEntities(false, maxResults, firstResult);
    }

    private List<Perro> findPerroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perro.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Perro findPerro(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perro.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perro> rt = cq.from(Perro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
