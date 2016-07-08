package co.piui.api.http;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import co.piui.api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude( Include.NON_NULL )
public class Monitoramento {

	private Long id;

	private Date passagem;

	private Integer tamanho;

	private Float velocidade;

	private Status status;

	private Integer sensorId;
	
	private String sensorDescricao;

}
