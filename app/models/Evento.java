//oioi
package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import models.exceptions.EventoInvalidoException;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
public class Evento {
	
	private static final int TAMANHO = 450, QUARENTA = 40, ZERO=0; 

	@Id
	@GeneratedValue
	private long id;

	@Required
	@MaxLength(value = QUARENTA)
	private String titulo;

	@Required
	@MaxLength(value = TAMANHO)
	@Column(name = "CONTENT", length = TAMANHO)
	private String descricao;

	@Temporal(value = TemporalType.DATE)
	@Required
	private Date data;

	@OneToMany(mappedBy = "evento")
	private List<Participante> participantes = new ArrayList<Participante>();

	@ElementCollection
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private List<Tema> temas = new ArrayList<Tema>();

	public Evento() {
	}

	public Evento(String titulo, String descricao, Date data, List<Tema> temas)
			throws EventoInvalidoException {
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.temas = temas;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getData() {
		return data;
	}

	public long getId() {
		return id;
	}

	public Integer getTotalDeParticipantes() {
		return participantes.size();
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTitulo(String titulo) throws EventoInvalidoException {
		if (titulo == null){
			throw new EventoInvalidoException("Parametro nulo");
		}if (titulo.length() > QUARENTA){
			throw new EventoInvalidoException("Título longo");
		}
		this.titulo = titulo;
	}

	public void setDescricao(String descricao) throws EventoInvalidoException {
		if (descricao == null){
			throw new EventoInvalidoException("Parametro nulo");
		}if (descricao.length() > TAMANHO){
			throw new EventoInvalidoException("Descrição longa");
		}
		this.descricao = descricao;
	}

	public void setData(Date data) throws EventoInvalidoException {
		if (data == null){
			throw new EventoInvalidoException("Parametro nulo");
		}if (data.compareTo(new Date()) < ZERO){
			throw new EventoInvalidoException("Data inválida");
		}
		this.data = data;
	}

	public void setTemas(List<Tema> temas) throws EventoInvalidoException {
		if (temas == null){
			throw new EventoInvalidoException("Parametro nulo");
		}if (temas.size() == ZERO){
			throw new EventoInvalidoException("Nenhum tema");
		}
		this.temas = temas;
	}
}
