package web.ufrn.demo.model;

public class Produtos {
    private int codigo;
	private String nome;
	private String descricao;
	private float preco;
    private int quantidade;
	
	public Produtos(){

	}
	
	public Produtos(int codigo){
		this.codigo = codigo;
	}

	public Produtos(int codigo, String nome, String descricao, float preco, int quantidade){
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.quantidade = quantidade;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
    public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}