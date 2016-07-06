package co.piui.api.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude( Include.NON_NULL )
public class Cliente {

	private Integer id;
	private String razaoSocial;
	private String documento;
	private Float longitude;
	private Float latitude;
	private Integer cidade;

}
