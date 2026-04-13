package com.esig.estagio;
 
import com.esig.estagio.model.Tarefa;
import com.esig.estagio.model.Prioridade;
import com.esig.estagio.model.Situacao;
 
import java.time.LocalDate;
 
// public class Main {
 
//     public static void main(String[] args) {
 
//         Tarefa tarefa = new Tarefa();
//         tarefa.setId(1L);
//         tarefa.setTitulo("Desenvolver tela de login");
//         tarefa.setDescricao("Criar a tela de login com JSF");
//         tarefa.setResponsavel("João");
//         tarefa.setPrioridade(Prioridade.ALTA);
//         tarefa.setDeadline(LocalDate.of(2025, 12, 31));
 
//         System.out.println("=== Teste Manual da Entidade Tarefa ===");
//         System.out.println("ID: " + tarefa.getId());
//         System.out.println("Título: " + tarefa.getTitulo());
//         System.out.println("Descrição: " + tarefa.getDescricao());
//         System.out.println("Responsável: " + tarefa.getResponsavel());
//         System.out.println("Prioridade: " + tarefa.getPrioridade());
//         System.out.println("Deadline: " + tarefa.getDeadline());
//         System.out.println("Situação: " + tarefa.getSituacao());
 
//         System.out.println();
 
//         // Testando concluir tarefa
//         tarefa.setSituacao(Situacao.CONCLUIDA);
//         System.out.println("Após concluir:");
//         System.out.println("Situação: " + tarefa.getSituacao());
//     }
// }