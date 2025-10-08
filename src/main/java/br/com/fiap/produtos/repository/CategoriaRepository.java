package br.com.fiap.produtos.repository;

import br.com.fiap.produtos.model.Categoria;
import br.com.fiap.produtos.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CategoriaRepository {

    public Categoria save(Categoria categoria) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (categoria.getId() == null) {
                em.persist(categoria);
            } else {
                categoria = em.merge(categoria);
            }
            em.getTransaction().commit();
            return categoria;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Categoria> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM Categoria c ORDER BY c.nome";
            return em.createQuery(jpql, Categoria.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Categoria> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Categoria.class, id));
        } finally {
            em.close();
        }
    }

    public Optional<Categoria> findByNome(String nome) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM Categoria c WHERE c.nome = :nome";
            TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
            query.setParameter("nome", nome);
            return query.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    public void delete(Categoria categoria) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(categoria)) {
                categoria = em.merge(categoria);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
