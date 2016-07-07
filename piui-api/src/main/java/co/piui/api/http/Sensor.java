package co.piui.api.http;

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
public class Sensor {

	private Integer id;
	private String identificacao;
	private String descricao;
	private Status status;
	private Float longitude;
	private Float latitude;
	private Integer cidade;

}
