package br.uel.Prova1AdminAnaPereira;

import jakarta.persistence.*;
import java.util.List;
import javax.annotation.processing.Generated;

@Entity
@Table(name = "restaurante")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @OneToMany(mappedBy = "restaurante")
    private List<ItemCardapio> itensRestaurante;

    public Restaurante(){

    }

    public Restaurante(String nome){
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setItensRestaurante(List<ItemCardapio> itensRestaurante) {
        this.itensRestaurante = itensRestaurante;
    }

    public List<ItemCardapio> getItensRestaurante() {
        return itensRestaurante;
    }
}