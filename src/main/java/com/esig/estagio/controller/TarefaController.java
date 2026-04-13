package com.esig.estagio.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.esig.estagio.dao.TarefaDAO;
import com.esig.estagio.model.Prioridade;
import com.esig.estagio.model.Situacao;
import com.esig.estagio.model.Tarefa;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("tarefaController")
@SessionScoped
public class TarefaController implements Serializable {

    @Inject
    private TarefaDAO tarefaDAO;

    private List<Tarefa> tarefas;
    private Tarefa tarefaAtual;

    private Long filtroNumero;
    private String filtroTituloDescricao;
    private String filtroResponsavel;
    private Situacao filtroSituacao = Situacao.EM_ANDAMENTO;

    private List<String> listaResponsaveis;

    @PostConstruct
    public void init() {
        tarefaAtual = new Tarefa();
        carregarTarefas();

        listaResponsaveis = Arrays.asList("Afonso", "Bruno", "Carla", "Denilson", "Elen");
        listaResponsaveis.sort(String::compareTo);
    }

    public void carregarTarefas() {
        tarefas = tarefaDAO.buscarFiltrado(filtroNumero, filtroTituloDescricao, filtroResponsavel, filtroSituacao);
    }

    public String prepararCriacao() {
        tarefaAtual = new Tarefa();
        return "criarTarefa.xhtml?faces-redirect=true";
    }

    public String prepararEdicao(Tarefa tarefa) {
        this.tarefaAtual = tarefa;
        return "editarTarefa.xhtml?faces-redirect=true";
    }

    public String salvarTarefa() {
        try {
            boolean retornarParaIndex = false;
            if (tarefaAtual.getId() == null) {
                tarefaDAO.criar(tarefaAtual);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tarefa cadastrada!"));
                retornarParaIndex = true;
            } else {
                tarefaDAO.atualizar(tarefaAtual);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tarefa atualizada!"));
            }
            carregarTarefas();
            tarefaAtual = new Tarefa();
            return (retornarParaIndex ? "index.xhtml?faces-redirect=true" : "listarTarefas.xhtml?faces-redirect=true");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Nao foi possivel salvar a tarefa: " + e.getMessage()));
            return null;
        }
    }

    public void excluirTarefa(Long tarefaId) {
        try {
            tarefaDAO.remover(tarefaId);
            carregarTarefas();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tarefa removida!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Nao foi possivel remover a tarefa: " + e.getMessage()));
        }
    }

    public void concluirTarefa(Long tarefaId) {
        try {
            Tarefa tarefa = tarefaDAO.buscarPorId(tarefaId);
            if (tarefa != null) {
                tarefa.setSituacao(Situacao.CONCLUIDA);
                tarefaDAO.atualizar(tarefa);
                carregarTarefas();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tarefa concluida!"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Nao foi possivel concluir a tarefa: " + e.getMessage()));
        }
    }

    public Prioridade[] getPrioridades() {
        return Prioridade.values();
    }

    public Situacao[] getSituacoes() {
        return Situacao.values();
    }

    public List<Tarefa> getTarefas() { return tarefas; }

    public Tarefa getTarefaAtual() { return tarefaAtual; }
    public void setTarefaAtual(Tarefa tarefa) { this.tarefaAtual = tarefa; }

    public Long getFiltroNumero() { return filtroNumero; }
    public void setFiltroNumero(Long filtroNumero) { this.filtroNumero = filtroNumero; }

    public String getFiltroTituloDescricao() { return filtroTituloDescricao; }
    public void setFiltroTituloDescricao(String filtroTituloDescricao) { this.filtroTituloDescricao = filtroTituloDescricao; }

    public String getFiltroResponsavel() { return filtroResponsavel; }
    public void setFiltroResponsavel(String filtroResponsavel) { this.filtroResponsavel = filtroResponsavel; }

    public Situacao getFiltroSituacao() { return filtroSituacao; }
    public void setFiltroSituacao(Situacao filtroSituacao) { this.filtroSituacao = filtroSituacao; }

    public List<String> getListaResponsaveis() { return listaResponsaveis; }
}