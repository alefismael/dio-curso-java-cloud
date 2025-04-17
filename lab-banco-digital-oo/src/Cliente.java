
public class Cliente {

	private String nome;
	private String cpf;

	public Cliente(String nome, String cpf){
		this.cpf = cpf;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getCpf(){
		return cpf;
	}

	@Override
	public String toString() {
		return "Cliente "+nome+" de CPF: "+cpf+".";
	}

}
