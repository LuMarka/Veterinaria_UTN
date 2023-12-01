
package Persistence;

import Entities.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Mascota;
import Persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public ClienteJpaController() {
        emf = Persistence.createEntityManagerFactory("VeterinariaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getMascotas() == null) {
            cliente.setMascotas(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mascota> attachedMascotas = new ArrayList<>();
            for (Mascota mascotasMascotaToAttach : cliente.getMascotas()) {
                mascotasMascotaToAttach = em.getReference(mascotasMascotaToAttach.getClass(), mascotasMascotaToAttach.getIdMascota());
                attachedMascotas.add(mascotasMascotaToAttach);
            }
            cliente.setMascotas(attachedMascotas);
            em.persist(cliente);
            for (Mascota mascotasMascota : cliente.getMascotas()) {
                Cliente oldIdClienteOfMascotasMascota = mascotasMascota.getIdCliente();
                mascotasMascota.setIdCliente(cliente);
                mascotasMascota = em.merge(mascotasMascota);
                if (oldIdClienteOfMascotasMascota != null) {
                    oldIdClienteOfMascotasMascota.getMascotas().remove(mascotasMascota);
                    oldIdClienteOfMascotasMascota = em.merge(oldIdClienteOfMascotasMascota);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            List<Mascota> mascotasOld = persistentCliente.getMascotas();
            List<Mascota> mascotasNew = cliente.getMascotas();
            List<Mascota> attachedMascotasNew = new ArrayList<>();
            for (Mascota mascotasNewMascotaToAttach : mascotasNew) {
                mascotasNewMascotaToAttach = em.getReference(mascotasNewMascotaToAttach.getClass(), mascotasNewMascotaToAttach.getIdMascota());
                attachedMascotasNew.add(mascotasNewMascotaToAttach);
            }
            mascotasNew = attachedMascotasNew;
            cliente.setMascotas(mascotasNew);
            cliente = em.merge(cliente);
            for (Mascota mascotasOldMascota : mascotasOld) {
                if (!mascotasNew.contains(mascotasOldMascota)) {
                    mascotasOldMascota.setIdCliente(null);
                    mascotasOldMascota = em.merge(mascotasOldMascota);
                }
            }
            for (Mascota mascotasNewMascota : mascotasNew) {
                if (!mascotasOld.contains(mascotasNewMascota)) {
                    Cliente oldIdClienteOfMascotasNewMascota = mascotasNewMascota.getIdCliente();
                    mascotasNewMascota.setIdCliente(cliente);
                    mascotasNewMascota = em.merge(mascotasNewMascota);
                    if (oldIdClienteOfMascotasNewMascota != null && !oldIdClienteOfMascotasNewMascota.equals(cliente)) {
                        oldIdClienteOfMascotasNewMascota.getMascotas().remove(mascotasNewMascota);
                        oldIdClienteOfMascotasNewMascota = em.merge(oldIdClienteOfMascotasNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Mascota> mascotas = cliente.getMascotas();
            for (Mascota mascotasMascota : mascotas) {
                mascotasMascota.setIdCliente(null);
                mascotasMascota = em.merge(mascotasMascota);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void destroy(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Cliente findCliente(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
