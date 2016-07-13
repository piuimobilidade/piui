package co.piui.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "sonda" )
public class SondaEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Long id;

	@NotNull
	@Column( name = "data" )
	private Date dataLastVerify;

	@NotNull
	@Column( name = "data_inicio" )
	private Date dataStart;

	@OneToOne
	private SensorEntity sensorEntity;

}
