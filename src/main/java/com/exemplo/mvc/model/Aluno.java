package com.exemplo.mvc.model;

public class Aluno {

    private String nome;
    private String matricula;

    public Aluno(String nome, String matricula) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }
}
