package co.piui.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import co.piui.api.entity.CidadeEntity;
import co.piui.api.entity.PassagemNivelEntity;
import co.piui.api.http.PassagemNivel;
import co.piui.api.repository.CidadeRepository;
import co.piui.api.repository.PassagemNivelRepository;

@Path( "/passagem-nivel" )
public class PassagemNivelController {
	@Inject
	private CidadeRepository repositoryCidade;
	@Inject
	private PassagemNivelRepository passagemNivelRepository;

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	public Response addPassagemNivel( @Context UriInfo uriInfo, PassagemNivel passagemNivel ) {
		PassagemNivelEntity passagemNivelEntity = save( passagemNivel );
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path( Integer.toString( passagemNivelEntity.getId() ) );
		return Response.created( builder.build() ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/{codigo}" )
	public Response getPassagemNivel( @PathParam( "codigo" ) Integer codigo ) {
		PassagemNivelEntity entity = passagemNivelRepository.getItemById( codigo );
		if ( entity != null )
			return Response.ok( new PassagemNivel( entity.getDescricao(), entity.getLatitude(), entity.getLongitude(),
					entity.getCidadeEntity().getId() ) ).build();
		return Response.status( Status.NOT_FOUND ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/cidade/{codigo}" )
	public Response getPassagemNivelByCidade( @PathParam( "codigo" ) Integer codigo ) {
		CidadeEntity cidadeEntity = repositoryCidade.getItemById( codigo );
		if ( cidadeEntity != null ) {
			List<PassagemNivel> list = new ArrayList<>();
			for ( PassagemNivelEntity passagem : cidadeEntity.getPassagemNivel() ) {
				list.add( new PassagemNivel( passagem.getDescricao(), passagem.getLatitude(), passagem.getLongitude(), null ) );
			}
			return Response.ok( list ).build();
		}
		return Response.status( Status.NOT_FOUND ).build();
	}

	private PassagemNivelEntity save( PassagemNivel passagemNivel ) {
		CidadeEntity cidadeEntity = repositoryCidade.getItemById( passagemNivel.getCidade() );
		if ( cidadeEntity != null ) {
			PassagemNivelEntity passagemNivelEntity = populatePassagemNivelEntity( passagemNivel, cidadeEntity );
			return passagemNivelRepository.save( passagemNivelEntity );
		}
		return null;
	}

	private PassagemNivelEntity populatePassagemNivelEntity( PassagemNivel passagemNivel, CidadeEntity cidadeEntity ) {
		PassagemNivelEntity passagemNivelEntity = new PassagemNivelEntity();
		passagemNivelEntity.setCidadeEntity( cidadeEntity );
		passagemNivelEntity.setDescricao( passagemNivel.getDescricao() );
		passagemNivelEntity.setLatitude( passagemNivel.getLatitude() );
		passagemNivelEntity.setLongitude( passagemNivel.getLongitude() );
		return passagemNivelEntity;
	}
}
