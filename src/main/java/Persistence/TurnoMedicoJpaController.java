
package Persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.MedicoVeterinario;
import Entities.TurnoMedico;
import Persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class TurnoMedicoJpaController implements Serializable {

    public TurnoMedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public TurnoMedicoJpaController() {
        emf = Persistence.createEntityManagerFactory("VeterinariaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TurnoMedico turnoMedico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoVeterinario medicoAsignado = turnoMedico.getMedicoAsignado();
            if (medicoAsignado != null) {
                medicoAsignado = em.getReference(medicoAsignado.getClass(), medicoAsignado.getIdMedico());
                turnoMedico.setMedicoAsignado(medicoAsignado);
            }
            em.persist(turnoMedico);
            if (medicoAsignado != null) {
                medicoAsignado.getTurnosAsignados().add(turnoMedico);
                medicoAsignado = em.merge(medicoAsignado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TurnoMedico turnoMedico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TurnoMedico persistentTurnoMedico = em.find(TurnoMedico.class, turnoMedico.getId());
            MedicoVeterinario medicoAsignadoOld = persistentTurnoMedico.getMedicoAsignado();
            MedicoVeterinario medicoAsignadoNew = turnoMedico.getMedicoAsignado();
            if (medicoAsignadoNew != null) {
                medicoAsignadoNew = em.getReference(medicoAsignadoNew.getClass(), medicoAsignadoNew.getIdMedico());
                turnoMedico.setMedicoAsignado(medicoAsignadoNew);
            }
            turnoMedico = em.merge(turnoMedico);
            if (medicoAsignadoOld != null && !medicoAsignadoOld.equals(medicoAsignadoNew)) {
                medicoAsignadoOld.getTurnosAsignados().remove(turnoMedico);
                medicoAsignadoOld = em.merge(medicoAsignadoOld);
            }
            if (medicoAsignadoNew != null && !medicoAsignadoNew.equals(medicoAsignadoOld)) {
                medicoAsignadoNew.getTurnosAsignados().add(turnoMedico);
                medicoAsignadoNew = em.merge(medicoAsignadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = turnoMedico.getId();
                if (findTurnoMedico(id) == null) {
                    throw new NonexistentEntityException("The turnoMedico with id " + id + " no longer exists.");
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
            TurnoMedico turnoMedico;
            try {
                turnoMedico = em.getReference(TurnoMedico.class, id);
                turnoMedico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turnoMedico with id " + id + " no longer exists.", enfe);
            }
            MedicoVeterinario medicoAsignado = turnoMedico.getMedicoAsignado();
            if (medicoAsignado != null) {
                medicoAsignado.getTurnosAsignados().remove(turnoMedico);
                medicoAsignado = em.merge(medicoAsignado);
            }
            em.remove(turnoMedico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TurnoMedico> findTurnoMedicoEntities() {
        return findTurnoMedicoEntities(true, -1, -1);
    }

    public List<TurnoMedico> findTurnoMedicoEntities(int maxResults, int firstResult) {
        return findTurnoMedicoEntities(false, maxResults, firstResult);
    }

    private List<TurnoMedico> findTurnoMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TurnoMedico.class));
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

    public TurnoMedico findTurnoMedico(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TurnoMedico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TurnoMedico> rt = cq.from(TurnoMedico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
