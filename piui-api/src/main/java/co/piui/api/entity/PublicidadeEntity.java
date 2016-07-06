package co.piui.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "publicidade" )
public class PublicidadeEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Integer id;
	@Column( name = "propaganda", length = 500 )
	@NotNull
	private String propaganda;
	@Column
	@NotNull
	private Integer quantidadeAnuncio;
	@Column
	@NotNull
	private Date vigencia;

	@ManyToOne
	private ClienteEntity clienteEntity;
}
