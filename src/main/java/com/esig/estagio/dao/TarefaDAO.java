package com.esig.estagio.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esig.estagio.model.Situacao;
import com.esig.estagio.model.Tarefa;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class TarefaDAO {

    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("estagio_esig");
        }
        return emf.createEntityManager();
    }

    public void criar(Tarefa tarefa) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tarefa);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remover(Long tarefaId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tarefa tarefa = em.find(Tarefa.class, tarefaId);
            if (tarefa != null) {
                em.remove(tarefa);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Tarefa atualizar(Tarefa tarefa) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tarefa resultado = em.merge(tarefa);
            em.getTransaction().commit();
            return resultado;
        } finally {
            em.close();
        }
    }

    public Tarefa buscarPorId(Long tarefaId) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarefa.class, tarefaId);
        } finally {
            em.close();
        }
    }

    public List<Tarefa> buscarTodos() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tarefa t ORDER BY t.id ASC", Tarefa.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tarefa> buscarFiltrado(Long numero, String tituloDescricao, String responsavel, Situacao situacao) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder sb = new StringBuilder("SELECT t FROM Tarefa t WHERE 1=1");
            Map<String, Object> parametros = new HashMap<>();

            if (numero != null) {
                sb.append(" AND t.id = :numero");
                parametros.put("numero", numero);
            }

            if (tituloDescricao != null && !tituloDescricao.trim().isEmpty()) {
                sb.append(" AND (LOWER(t.titulo) LIKE LOWER(:tituloDescricao) OR LOWER(t.descricao) LIKE LOWER(:tituloDescricao))");
                parametros.put("tituloDescricao", "%" + tituloDescricao + "%");
            }

            if (responsavel != null && !responsavel.trim().isEmpty()) {
                sb.append(" AND t.responsavel = :responsavel");
                parametros.put("responsavel", responsavel);
            }

            if (situacao != null) {
                sb.append(" AND t.situacao = :situacao");
                parametros.put("situacao", situacao);
            } else {
                sb.append(" AND t.situacao = :defaultSituacao");
                parametros.put("defaultSituacao", Situacao.EM_ANDAMENTO);
            }

            sb.append(" ORDER BY t.id ASC");

            TypedQuery<Tarefa> query = em.createQuery(sb.toString(), Tarefa.class);
            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}