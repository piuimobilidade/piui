package co.piui.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "cidade" )
public class CidadeEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Integer id;
	@Column( name = "nome", unique = true , length = 80 )
	@NotNull
	private String nome;

	@Column( name = "uf" )
	@NotNull
	private UF uf;

	@OneToMany( mappedBy = "cidadeEntity", targetEntity = PassagemNivelEntity.class )
	private List<PassagemNivelEntity> passagemNivel;

	@OneToMany( mappedBy = "cidadeEntity", targetEntity = ClienteEntity.class )
	private List<ClienteEntity> clienteEntity;
	
	@OneToMany( mappedBy = "cidadeEntity", targetEntity = SensorEntity.class)
	private List<SensorEntity> sensorEntity;

}
