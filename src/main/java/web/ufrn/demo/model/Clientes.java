package web.ufrn.demo.model;

public class Clientes {
	private int id;
	private String nome;
	private String email;
	private String senha;
	
	public Clientes(){

	}
	
	public Clientes(String email, String senha){
		this.email = email;
		this.senha = senha;
	}
	
	public Clientes(String email){
		this.email = email;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}