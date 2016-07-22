package co.piui.api.controller;

import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import co.piui.api.entity.CidadeEntity;
import co.piui.api.entity.SensorEntity;
import co.piui.api.http.Sensor;
import co.piui.api.repository.CidadeRepository;
import co.piui.api.repository.SensorRepository;

@Path( "/sensor" )
public class SensorController {
	@Inject
	private CidadeRepository repositoryCidade;
	@Inject
	private SensorRepository sensorRepository;;

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	public Response addSensor( @Context UriInfo uriInfo, Sensor sensor ) {
		SensorEntity sensorEntity = save( sensor );
		if ( sensorEntity != null ) {
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path( Integer.toString( sensorEntity.getId() ) );
			return Response.created( builder.build() ).build();
		}
		return Response.status( Status.NOT_ACCEPTABLE ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/{codigo}" )
	public Response getSensorById( @PathParam( "codigo" ) Integer codigo ) {
		SensorEntity entity = sensorRepository.getItemById( codigo );
		if ( entity != null )
			return Response
					.ok( new Sensor( null, entity.getIdentificacao(), entity.getDescricao(), entity.getStatus(), entity.getLongitude(),
							entity.getLatitude(), entity.getCidadeEntity().getId() ) )
					.build();
		return Response.status( Status.NOT_FOUND ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/cidade/{codigo}" )
	public Response getSensorByCidade( @PathParam( "codigo" ) Integer codigo ) {
		CidadeEntity cidadeEntity = repositoryCidade.getItemById( codigo );
		if ( cidadeEntity != null ) {
			List<Sensor> list = new ArrayList<>();
			for ( SensorEntity entity : cidadeEntity.getSensorEntity() ) {
				list.add( new Sensor( entity.getId(), entity.getIdentificacao(), entity.getDescricao(), entity.getStatus(), entity.getLongitude(),
						entity.getLatitude(), null ) );
			}
			return Response.ok( list ).build();
		}
		return Response.status( Status.NOT_FOUND ).build();
	}

	@PUT
	@Path( "/{codigo}/setStatus" )
	public Response setStatusSensor( @PathParam( "codigo" ) Integer codigo ) {
		SensorEntity sensorEntity = sensorRepository.getItemById( codigo );
		if ( sensorEntity != null ) {
			if ( sensorEntity.getStatus() == co.piui.api.entity.Status.ATIVO ) {
				sensorEntity.setStatus( co.piui.api.entity.Status.INATIVO );
			} else {
				sensorEntity.setStatus( co.piui.api.entity.Status.ATIVO );
			}
			sensorRepository.update( sensorEntity );
			return Response.ok().build();
		}
		return Response.status( Status.NOT_FOUND ).build();
	}

	private SensorEntity save( Sensor sensor ) {
		CidadeEntity cidadeEntity = repositoryCidade.getItemById( sensor.getCidade() );
		if ( cidadeEntity != null ) {
			SensorEntity sensorEntity = populateSensorEntity( sensor, cidadeEntity );
			return sensorRepository.save( sensorEntity );
		}
		return null;
	}

	private SensorEntity populateSensorEntity( Sensor sensor, CidadeEntity cidadeEntity ) {
		SensorEntity sensorEntity = new SensorEntity();
		sensorEntity.setCidadeEntity( cidadeEntity );
		sensorEntity.setIdentificacao( sensor.getIdentificacao() );
		sensorEntity.setDescricao( sensor.getDescricao() );
		sensorEntity.setLatitude( sensor.getLatitude() );
		sensorEntity.setLongitude( sensor.getLongitude() );
		return sensorEntity;
	}
}
