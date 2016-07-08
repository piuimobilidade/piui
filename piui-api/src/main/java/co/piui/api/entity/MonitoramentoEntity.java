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
@Table( name = "monitoramento" )
public class MonitoramentoEntity {
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id" )
	private Long id;

	@NotNull
	@Column( name = "passagem" )
	private Date passagem;

	@NotNull
	@Column( name = "tamanho" )
	private Integer tamanho;

	@Column( name = "velocidade", precision = 3, scale = 2 )
	private Float velocidade;

	@NotNull
	@Column( name = "status" )
	private Status status = Status.ATIVO;

	@ManyToOne
	private SensorEntity sensorEntity;

}
