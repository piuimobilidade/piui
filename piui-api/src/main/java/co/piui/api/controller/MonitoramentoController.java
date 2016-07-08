package co.piui.api.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import co.piui.api.entity.MonitoramentoEntity;
import co.piui.api.entity.SensorEntity;
import co.piui.api.entity.Status;
import co.piui.api.http.Monitoramento;
import co.piui.api.repository.MonitoramentoRepository;
import co.piui.api.repository.SensorRepository;

@Path( "/monitoramento" )
public class MonitoramentoController {

	@Inject
	private MonitoramentoRepository repository;
	@Inject
	private SensorRepository sensorRepository;

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	public Response addMonitoramento( @Context UriInfo uriInfo, Monitoramento monitoramento ) {
		MonitoramentoEntity monitoramentoEntity = save( monitoramento );
		repository.save( monitoramentoEntity );
		return Response.status( 201 ).build();
	}

	private Status getStatusLastMonitoramento() {
		return repository.getLastMonitoramento().getStatus();
	}

	private MonitoramentoEntity save( Monitoramento monitoramento ) {
		SensorEntity sensorEntity = sensorRepository.getSensorByIdentificacao( monitoramento.getSensorDescricao() );
		MonitoramentoEntity monitoramentoEntity = new MonitoramentoEntity();
		monitoramentoEntity.setTamanho( monitoramento.getTamanho() );
		monitoramentoEntity.setVelocidade( monitoramento.getVelocidade() );
		monitoramentoEntity.setPassagem( new Date() );
		monitoramentoEntity.setSensorEntity( sensorEntity );
		if ( getStatusLastMonitoramento().equals( Status.ATIVO ) ) {
			monitoramentoEntity.setStatus( Status.INATIVO );
		}
		return monitoramentoEntity;
	}
}
