package br.edu.ifsp.dao;

import br.edu.ifsp.model.Base;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DAO<T extends Base> implements Serializable {

    private static final long serialVersionUID = 1L;

    public T buscarPorId(Class<T> classe, long id) {
        EntityManager gerente = GerenciadorConexao.getGerente();
        return gerente.find(classe, id);
    }

    public void salvar(T dados) {
        EntityManager gerente = GerenciadorConexao.getGerente();
        gerente.getTransaction().begin();
        try {
            if (dados.getId() == null) {
                gerente.persist(dados);
            } else {
                gerente.merge(dados);
            }
            gerente.getTransaction().commit();

        } catch (Exception e) {
            gerente.getTransaction().rollback();
        }
        gerente.close();
    }

    public void remover(Class<T> classe, Long id) {
        EntityManager gerente = GerenciadorConexao.getGerente();

        try {
            gerente.getTransaction().begin();
            T dados = gerente.find(classe, id);
            gerente.remove(dados);
            gerente.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            gerente.getTransaction().rollback();
        }
        gerente.close();
    }

    public List<T> buscarTodos(String sql) {
        EntityManager gerente = GerenciadorConexao.getGerente();
        Query query = gerente.createQuery(sql);
        return query.getResultList();
    }
}
