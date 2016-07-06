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
@Table( name = "cliente" )
public class ClienteEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Integer id;

	@Column( name = "razao_social", length = 80 )
	@NotNull
	private String razaoSocial;
	@Column( name = "documento", length = 80 )
	@NotNull
	private String documento;
	@NotNull
	@Column( name = "longitude", precision = 10, scale = 6 )
	private Float longitude;
	@NotNull
	@Column( name = "latitude", precision = 10, scale = 6 )
	private Float latitude;

	@ManyToOne
	private CidadeEntity cidadeEntity;

	@OneToMany( mappedBy = "clienteEntity", targetEntity = PublicidadeEntity.class )
	private List<PublicidadeEntity> publicidadeEntity;
}
