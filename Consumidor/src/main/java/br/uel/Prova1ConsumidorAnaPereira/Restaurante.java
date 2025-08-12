package br.uel.Prova1ConsumidorAnaPereira;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "restaurante")
public class Restaurante implements Serializable {
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

    @Override
    public boolean equals(Object o){
        if(o == null || this.getClass() != o.getClass()){
            return false;
        }

        return ((Restaurante)o).id == (this.id);
    }

    @Override
    public int hashCode(){
        return id * 12345;
    }
}