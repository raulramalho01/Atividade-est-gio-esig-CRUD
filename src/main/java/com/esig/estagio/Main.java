package com.esig.estagio;

import com.esig.estagio.model.Tarefa;
import com.esig.estagio.model.Prioridade;
import com.esig.estagio.model.Situacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("estagio_esig");
        EntityManager em = emf.createEntityManager();

        try {
            // CRIAR
            em.getTransaction().begin();

            Tarefa t1 = new Tarefa();
            t1.setTitulo("Tarefa 1");
            t1.setDescricao("Descricao da tarefa 1");
            t1.setResponsavel("Joao");
            t1.setPrioridade(Prioridade.ALTA);
            t1.setDeadline(LocalDate.of(2025, 12, 31));
            em.persist(t1);

            Tarefa t2 = new Tarefa();
            t2.setTitulo("Tarefa 2");
            t2.setDescricao("Descricao da tarefa 2");
            t2.setResponsavel("Maria");
            t2.setPrioridade(Prioridade.MEDIA);
            t2.setDeadline(LocalDate.of(2025, 6, 15));
            em.persist(t2);

            em.getTransaction().commit();
            System.out.println("=== CRIAR: 2 tarefas inseridas ===");

            // LISTAR
            List<Tarefa> tarefas = em.createQuery("SELECT t FROM Tarefa t ORDER BY t.id ASC", Tarefa.class)
                    .getResultList();
            System.out.println("\n=== LISTAR: " + tarefas.size() + " tarefas ===");
            for (Tarefa t : tarefas) {
                System.out.println("ID: " + t.getId()
                        + " | Titulo: " + t.getTitulo()
                        + " | Responsavel: " + t.getResponsavel()
                        + " | Prioridade: " + t.getPrioridade()
                        + " | Situacao: " + t.getSituacao());
            }

            // ATUALIZAR
            em.getTransaction().begin();
            Tarefa paraAtualizar = tarefas.get(0);
            paraAtualizar.setTitulo("Tarefa 1 - Atualizada");
            paraAtualizar.setPrioridade(Prioridade.BAIXA);
            em.merge(paraAtualizar);
            em.getTransaction().commit();
            System.out.println("\n=== ATUALIZAR: Tarefa " + paraAtualizar.getId() + " atualizada ===");

            // CONCLUIR
            em.getTransaction().begin();
            Tarefa paraConcluir = tarefas.get(1);
            paraConcluir.setSituacao(Situacao.CONCLUIDA);
            em.merge(paraConcluir);
            em.getTransaction().commit();
            System.out.println("\n=== CONCLUIR: Tarefa " + paraConcluir.getId() + " concluida ===");

            // LISTAR NOVAMENTE
            tarefas = em.createQuery("SELECT t FROM Tarefa t ORDER BY t.id ASC", Tarefa.class)
                    .getResultList();
            System.out.println("\n=== LISTAR APOS ALTERACOES ===");
            for (Tarefa t : tarefas) {
                System.out.println("ID: " + t.getId()
                        + " | Titulo: " + t.getTitulo()
                        + " | Prioridade: " + t.getPrioridade()
                        + " | Situacao: " + t.getSituacao());
            }

            // REMOVER
            em.getTransaction().begin();
            Tarefa paraRemover = em.find(Tarefa.class, tarefas.get(0).getId());
            em.remove(paraRemover);
            em.getTransaction().commit();
            System.out.println("\n=== REMOVER: Tarefa " + paraRemover.getId() + " removida ===");

            // LISTAR FINAL
            tarefas = em.createQuery("SELECT t FROM Tarefa t ORDER BY t.id ASC", Tarefa.class)
                    .getResultList();
            System.out.println("\n=== LISTAR FINAL: " + tarefas.size() + " tarefa(s) restante(s) ===");
            for (Tarefa t : tarefas) {
                System.out.println("ID: " + t.getId()
                        + " | Titulo: " + t.getTitulo()
                        + " | Situacao: " + t.getSituacao());
            }

        } finally {
            em.close();
            emf.close();
        }

        System.out.println("\n=== TODOS OS TESTES PASSARAM ===");
    }
}