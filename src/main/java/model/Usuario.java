package model;

public class Usuario {

	private int id;
    private String nome;
    private String sobrenome;
    private String email;
    private int status;
    private int idPerfil;
    private String perfil;

    public Usuario() {} 


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



	public String getSobrenome() {
		return sobrenome;
	}



	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getIdPerfil() {
		return idPerfil;
	}



	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}



	public String getPerfil() {
		return perfil;
	}



	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	
	
	public boolean eColaborador() {
		return perfil.equalsIgnoreCase("Colaborador");
	}
	
	
	public boolean eAdministrador() {
		return perfil.equalsIgnoreCase("Administrador");
	}
}
