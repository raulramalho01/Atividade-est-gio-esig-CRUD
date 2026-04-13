package com.esig.estagio.model;

public enum Situacao {
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDA("Concluida");

    private final String label;

    Situacao(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}