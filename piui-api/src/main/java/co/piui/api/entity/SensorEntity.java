package co.piui.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "sensor" )
public class SensorEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Integer id;
	@NotNull
	@Column( name = "identificacao" )
	private String identificacao;

	@Column( name = "descricao" )
	private String descricao;

	@NotNull
	@Column( name = "longitude", precision = 10, scale = 6 )
	private Float longitude;
	@NotNull
	@Column( name = "latitude", precision = 10, scale = 6 )
	private Float latitude;

	@NotNull
	@Column( name = "status" )
	private Status status = Status.ATIVO;
	@ManyToOne
	private CidadeEntity cidadeEntity;

}