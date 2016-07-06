package co.piui.api.entity;

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
@Table( name = "passagem_nivel" )
public class PassagemNivelEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Integer id;
	@Column( name = "descricao", length = 40 )
	private String descricao;
	@NotNull
	@Column( name = "longitude", precision = 10, scale = 6 )
	private Float longitude;
	@NotNull
	@Column( name = "latitude", precision = 10, scale = 6 )
	private Float latitude;

	@ManyToOne
	private CidadeEntity cidadeEntity;
}
