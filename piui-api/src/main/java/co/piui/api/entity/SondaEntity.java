package co.piui.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// @Table( name = "sonda" )
@Table( name = "sonda", uniqueConstraints = { @UniqueConstraint( columnNames = { "sensor_id" } ) } )
public class SondaEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Integer id;

	@NotNull
	@Column( name = "data" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date dataLastVerify;

	@Column( name = "data_inicio" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date dataStart;

	@OneToOne
	@JoinColumn( name = "sensor_id" )
	private SensorEntity sensorEntity;

	@PreUpdate
	private void onUpdate() {
		dataLastVerify = new Date();
	}

	@PrePersist
	private void onCreate() {
		dataStart = new Date();
		onUpdate();
	}

}
