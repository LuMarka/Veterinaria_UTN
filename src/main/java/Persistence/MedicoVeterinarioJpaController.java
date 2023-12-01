/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import Entities.MedicoVeterinario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.TurnoMedico;
import Persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
public class MedicoVeterinarioJpaController implements Serializable {

    public MedicoVeterinarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public MedicoVeterinarioJpaController() {
        emf = Persistence.createEntityManagerFactory("VeterinariaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicoVeterinario medicoVeterinario) {
        if (medicoVeterinario.getTurnosAsignados() == null) {
            medicoVeterinario.setTurnosAsignados(new ArrayList<TurnoMedico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TurnoMedico> attachedTurnosAsignados = new ArrayList<TurnoMedico>();
            for (TurnoMedico turnosAsignadosTurnoMedicoToAttach : medicoVeterinario.getTurnosAsignados()) {
                turnosAsignadosTurnoMedicoToAttach = em.getReference(turnosAsignadosTurnoMedicoToAttach.getClass(), turnosAsignadosTurnoMedicoToAttach.getId());
                attachedTurnosAsignados.add(turnosAsignadosTurnoMedicoToAttach);
            }
            medicoVeterinario.setTurnosAsignados(attachedTurnosAsignados);
            em.persist(medicoVeterinario);
            for (TurnoMedico turnosAsignadosTurnoMedico : medicoVeterinario.getTurnosAsignados()) {
                MedicoVeterinario oldMedicoAsignadoOfTurnosAsignadosTurnoMedico = turnosAsignadosTurnoMedico.getMedicoAsignado();
                turnosAsignadosTurnoMedico.setMedicoAsignado(medicoVeterinario);
                turnosAsignadosTurnoMedico = em.merge(turnosAsignadosTurnoMedico);
                if (oldMedicoAsignadoOfTurnosAsignadosTurnoMedico != null) {
                    oldMedicoAsignadoOfTurnosAsignadosTurnoMedico.getTurnosAsignados().remove(turnosAsignadosTurnoMedico);
                    oldMedicoAsignadoOfTurnosAsignadosTurnoMedico = em.merge(oldMedicoAsignadoOfTurnosAsignadosTurnoMedico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicoVeterinario medicoVeterinario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoVeterinario persistentMedicoVeterinario = em.find(MedicoVeterinario.class, medicoVeterinario.getIdMedico());
            List<TurnoMedico> turnosAsignadosOld = persistentMedicoVeterinario.getTurnosAsignados();
            List<TurnoMedico> turnosAsignadosNew = medicoVeterinario.getTurnosAsignados();
            List<TurnoMedico> attachedTurnosAsignadosNew = new ArrayList<TurnoMedico>();
            for (TurnoMedico turnosAsignadosNewTurnoMedicoToAttach : turnosAsignadosNew) {
                turnosAsignadosNewTurnoMedicoToAttach = em.getReference(turnosAsignadosNewTurnoMedicoToAttach.getClass(), turnosAsignadosNewTurnoMedicoToAttach.getId());
                attachedTurnosAsignadosNew.add(turnosAsignadosNewTurnoMedicoToAttach);
            }
            turnosAsignadosNew = attachedTurnosAsignadosNew;
            medicoVeterinario.setTurnosAsignados(turnosAsignadosNew);
            medicoVeterinario = em.merge(medicoVeterinario);
            for (TurnoMedico turnosAsignadosOldTurnoMedico : turnosAsignadosOld) {
                if (!turnosAsignadosNew.contains(turnosAsignadosOldTurnoMedico)) {
                    turnosAsignadosOldTurnoMedico.setMedicoAsignado(null);
                    turnosAsignadosOldTurnoMedico = em.merge(turnosAsignadosOldTurnoMedico);
                }
            }
            for (TurnoMedico turnosAsignadosNewTurnoMedico : turnosAsignadosNew) {
                if (!turnosAsignadosOld.contains(turnosAsignadosNewTurnoMedico)) {
                    MedicoVeterinario oldMedicoAsignadoOfTurnosAsignadosNewTurnoMedico = turnosAsignadosNewTurnoMedico.getMedicoAsignado();
                    turnosAsignadosNewTurnoMedico.setMedicoAsignado(medicoVeterinario);
                    turnosAsignadosNewTurnoMedico = em.merge(turnosAsignadosNewTurnoMedico);
                    if (oldMedicoAsignadoOfTurnosAsignadosNewTurnoMedico != null && !oldMedicoAsignadoOfTurnosAsignadosNewTurnoMedico.equals(medicoVeterinario)) {
                        oldMedicoAsignadoOfTurnosAsignadosNewTurnoMedico.getTurnosAsignados().remove(turnosAsignadosNewTurnoMedico);
                        oldMedicoAsignadoOfTurnosAsignadosNewTurnoMedico = em.merge(oldMedicoAsignadoOfTurnosAsignadosNewTurnoMedico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = medicoVeterinario.getIdMedico();
                if (findMedicoVeterinario(id) == null) {
                    throw new NonexistentEntityException("The medicoVeterinario with id " + id + " no longer exists.");
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
            MedicoVeterinario medicoVeterinario;
            try {
                medicoVeterinario = em.getReference(MedicoVeterinario.class, id);
                medicoVeterinario.getIdMedico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicoVeterinario with id " + id + " no longer exists.", enfe);
            }
            List<TurnoMedico> turnosAsignados = medicoVeterinario.getTurnosAsignados();
            for (TurnoMedico turnosAsignadosTurnoMedico : turnosAsignados) {
                turnosAsignadosTurnoMedico.setMedicoAsignado(null);
                turnosAsignadosTurnoMedico = em.merge(turnosAsignadosTurnoMedico);
            }
            em.remove(medicoVeterinario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicoVeterinario> findMedicoVeterinarioEntities() {
        return findMedicoVeterinarioEntities(true, -1, -1);
    }

    public List<MedicoVeterinario> findMedicoVeterinarioEntities(int maxResults, int firstResult) {
        return findMedicoVeterinarioEntities(false, maxResults, firstResult);
    }

    private List<MedicoVeterinario> findMedicoVeterinarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicoVeterinario.class));
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

    public MedicoVeterinario findMedicoVeterinario(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicoVeterinario.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoVeterinarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicoVeterinario> rt = cq.from(MedicoVeterinario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
