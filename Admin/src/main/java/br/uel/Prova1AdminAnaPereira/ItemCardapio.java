package br.uel.Prova1AdminAnaPereira;

import jakarta.persistence.*;


@Entity
@Table(name = "item_cardapio")
public class ItemCardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     private String nome;

     private String descricao;

     private double preco;

     @ManyToOne
     @JoinColumn(name = "id_restaurante", nullable = false)
     private Restaurante restaurante;

     public ItemCardapio(){

     }

     public ItemCardapio(String nome, String descricao, double preco, Restaurante restaurante){
         this.nome = nome;
         this.descricao = descricao;
         this.preco = preco;
         this.restaurante = restaurante;
     }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
