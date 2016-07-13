package co.piui.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import co.piui.api.entity.SensorEntity;
import co.piui.api.entity.SondaEntity;
import co.piui.api.http.Sonda;
import co.piui.api.repository.SensorRepository;
import co.piui.api.repository.SondaRepository;

@Path( "/sonda" )
public class SondaController {

	@Inject
	private SondaRepository repository;
	@Inject
	private SensorRepository sensorRepository;
	private SensorEntity sensorEntity;

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	public Response startSensor( @Context UriInfo uriInfo, Sonda sonda ) {
		sensorEntity = sensorRepository.getSensorByIdentificacao( sonda.getSensorDescricao() );
		SondaEntity sondaEntity = createSonda( sonda, sensorEntity );
		sondaEntity.setDataStart( new Date() );
		if ( sensorEntity == null && sensorEntity.getSondaEntity() == null ) {
			repository.save( sondaEntity );
		} else {
			repository.update( sondaEntity );
		}
		return Response.status( 201 ).build();
	}

	@PUT
	@Consumes( "application/json; charset=UTF-8" )
	public Response addSonda( @Context UriInfo uriInfo, Sonda sonda ) {
		sensorEntity = sensorRepository.getSensorByIdentificacao( sonda.getSensorDescricao() );
		SondaEntity sondaEntity = createSonda( sonda, sensorEntity );
		repository.update( sondaEntity );
		return Response.status( 204 ).build();
	}

	@GET
	@Path( "/problemas/{minutos}" )
	@Consumes( "application/json; charset=UTF-8" )
	public List<Sonda> getProblems( @PathParam( "minutos" ) Integer minutos ) {
		List<SondaEntity> sondaEntity = null;
		sondaEntity = repository.getSondaWithProblemLastMinutes( minutos );
		return setListSonda( sondaEntity );
	}

	@GET
	@Path( "/{sensor}" )
	@Consumes( "application/json; charset=UTF-8" )
	public Response getSensor( @PathParam( "sensor" ) String sensor ) {
		sensorEntity = sensorRepository.getSensorByIdentificacao( sensor );
		return Response.ok( new Sonda( sensorEntity.getDescricao(), sensorEntity.getSondaEntity().getDataLastVerify() ) ).build();
	}

	@GET
	@Consumes( "application/json; charset=UTF-8" )
	public List<Sonda> getAll() {
		List<SondaEntity> sondaEntity = null;
		sondaEntity = repository.listAll();
		return setListSonda( sondaEntity );
	}

	private List<Sonda> setListSonda( List<SondaEntity> sondaEntity ) {
		List<Sonda> listSondaReturn = new ArrayList<>();
		for ( SondaEntity sonda : sondaEntity ) {
			listSondaReturn.add( new Sonda( sonda.getSensorEntity().getDescricao(), sonda.getDataLastVerify() ) );
		}
		return listSondaReturn;
	}

	private SondaEntity createSonda( Sonda sonda, SensorEntity sensorEntity ) {
		SondaEntity sondaEntity = new SondaEntity();
		sondaEntity.setSensorEntity( sensorEntity );
		sondaEntity.setDataLastVerify( new Date() );
		return sondaEntity;
	}
}
