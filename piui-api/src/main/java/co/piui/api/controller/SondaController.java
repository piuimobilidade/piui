package co.piui.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import co.piui.api.entity.SensorEntity;
import co.piui.api.entity.SondaEntity;
import co.piui.api.http.Sonda;
import co.piui.api.repository.SensorRepository;
import co.piui.api.repository.SondaRepository;
import lombok.extern.java.Log;

@Path( "/sonda" )
@Produces( "application/json; charset=UTF-8" )
@Consumes( "application/json; charset=UTF-8" )
@Log
public class SondaController {

	@Inject
	private SondaRepository repository;
	@Inject
	private SensorRepository sensorRepository;
	private SensorEntity sensorEntity;

	@POST
	public Response startSensor( @Context UriInfo uriInfo, Sonda sonda ) {
		sensorEntity = sensorRepository.getSensorByIdentificacao( sonda.getSensorDescricao() );
		log.log( Level.SEVERE, "Verificando os dados ", sensorEntity );
		if ( sensorEntity.getSondaEntity() != null ) {
			repository.deleteById( sensorEntity.getSondaEntity().getId() );
		}
		SondaEntity sondaEntity = new SondaEntity();
		sondaEntity.setSensorEntity( sensorEntity );
		repository.save( sondaEntity );
		return Response.status( 201 ).build();
	}

	@PUT
	public Response addSonda( @Context UriInfo uriInfo, Sonda sonda ) {
		sensorEntity = sensorRepository.getSensorByIdentificacao( sonda.getSensorDescricao() );
		repository.update( sensorEntity.getSondaEntity() );
		return Response.status( 204 ).build();
	}

	@GET
	@Path( "/problemas/{minutos}" )
	public List<Sonda> getProblems( @PathParam( "minutos" ) Integer minutos ) {
		List<SondaEntity> sondaEntity = null;
		sondaEntity = repository.getSondaWithProblemLastMinutes( minutos );
		return setListSonda( sondaEntity );
	}

	@GET
	@Path( "/{sensor}" )
	public Response getSensor( @PathParam( "sensor" ) String sensor ) {
		sensorEntity = sensorRepository.getSensorByIdentificacao( sensor );
		return Response.ok( new Sonda( null, sensorEntity.getDescricao(), sensorEntity.getSondaEntity().getDataLastVerify(),
				sensorEntity.getSondaEntity().getDataStart() ) ).build();
	}

	public Response deleteAll(){
		repository.deleteAll();
		return Response.ok().build();
		
	}
	
	@GET
	public List<Sonda> getAll() {
		List<SondaEntity> sondaEntity = null;
		sondaEntity = repository.listAll();
		return setListSonda( sondaEntity );
	}

	private List<Sonda> setListSonda( List<SondaEntity> sondaEntity ) {
		List<Sonda> listSondaReturn = new ArrayList<>();
		for ( SondaEntity sonda : sondaEntity ) {
			listSondaReturn.add(
					new Sonda( sonda.getId(), sonda.getSensorEntity().getDescricao(), sonda.getDataLastVerify(), sonda.getDataStart() ) );
		}
		return listSondaReturn;
	}
}
