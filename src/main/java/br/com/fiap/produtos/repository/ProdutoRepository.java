package br.com.fiap.produtos.repository;

import br.com.fiap.produtos.model.Produto;
import br.com.fiap.produtos.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProdutoRepository {

    public Produto save(Produto produto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (produto.getId() == null) {
                em.persist(produto);
            } else {
                produto = em.merge(produto);
            }
            em.getTransaction().commit();
            return produto;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Produto> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            String jpql = "SELECT p FROM Produto p LEFT JOIN FETCH p.categoria ORDER BY p.nome";
            List<Produto> produtos = em.createQuery(jpql, Produto.class).getResultList();
            em.getTransaction().commit();
            return produtos;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Optional<Produto> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, id);
            if (produto != null) {
                // Força o carregamento da categoria se for lazy
                produto.getCategoria();
            }
            em.getTransaction().commit();
            return Optional.ofNullable(produto);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Produto produto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(produto)) {
                produto = em.merge(produto);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Produto> findByCategoria(String nomeCategoria) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            String jpql = "SELECT p FROM Produto p JOIN FETCH p.categoria c WHERE c.nome = :nomeCategoria ORDER BY p.nome";
            TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
            query.setParameter("nomeCategoria", nomeCategoria);
            List<Produto> produtos = query.getResultList();
            em.getTransaction().commit();
            return produtos;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Produto> findByPrecoMenorQue(java.math.BigDecimal precoMaximo) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Usando JOIN FETCH para carregar as categorias em uma única consulta
            String jpql = "SELECT p FROM Produto p LEFT JOIN FETCH p.categoria WHERE p.preco <= :precoMaximo ORDER BY p.preco";
            TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
            query.setParameter("precoMaximo", precoMaximo);
            List<Produto> produtos = query.getResultList();
            em.getTransaction().commit();
            return produtos;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
