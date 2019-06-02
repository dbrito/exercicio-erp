/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads.pi3.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ninck
 */
    public class Cliente {
    

    //Atributos
    private Integer ativo;
    private Integer id;
    private String cpf;
    private String nome;
    private Date dataNascimento;
    private String telefone;
    private String email;



    
    //Métodos de acesso
    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }
    
    public String getDataNascimentoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");        
        return sdf.format(dataNascimento);
    }

    public void setData_nascimento(Date data_nascimento) {
        this.dataNascimento = data_nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}

   
