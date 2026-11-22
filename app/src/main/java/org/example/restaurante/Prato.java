package restaurante;

public class Prato {
    private int id;
    private String nomePrato;
    private String descricao;
    private double preco;
    private int qtdPessoasServe;
    private String categoria;
    private boolean disponivel;
    private int tempoPreparo;

    public Prato() {
        this.id = 0;
        this.nomePrato = "";
        this.descricao = "";
        this.preco = 0.0;
        this.qtdPessoasServe = 0;
        this.categoria = "";
        this.disponivel = true;
        this.tempoPreparo = 0;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getId() {
        return this.id;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    } 

    public String getNomePrato() {
        return this.nomePrato;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    } 

    public String getDescricao() {
        return this.descricao;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    } 

    public boolean getDisponivel() {
        return this.disponivel;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    } 

    public String getCategoria() {
        return this.categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    } 

    public double getPreco() {
        return this.preco;
    }
    
    public void setQtdPessoasServe(int qtdPessoasServe) {
        this.qtdPessoasServe = qtdPessoasServe;
    } 

    public int getQtdPessoasServe() {
        return this.qtdPessoasServe;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    } 

    public int getTempoPreparo() {
        return this.tempoPreparo;
    }
}