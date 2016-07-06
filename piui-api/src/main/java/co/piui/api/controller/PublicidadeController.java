package co.piui.api.controller;

import java.util.ArrayList;
import java.util.List;

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

import co.piui.api.entity.ClienteEntity;
import co.piui.api.entity.PublicidadeEntity;
import co.piui.api.http.Publicidade;
import co.piui.api.repository.ClienteRepository;
import co.piui.api.repository.PublicidadeRepository;

@Path( "/publicidade" )
public class PublicidadeController {
	private PublicidadeRepository repositoryPublicidade = new PublicidadeRepository();
	private ClienteRepository clienteRepository = new ClienteRepository();

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	@Produces( "application/json; charset=UTF-8" )
	public Response addPublicidade( @Context UriInfo uriInfo, Publicidade publicidade ) {
		PublicidadeEntity publicidadeEntity = this.save( publicidade );
		if ( publicidadeEntity != null ) {
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path( Integer.toString( publicidadeEntity.getId() ) );
			return Response.created( builder.build() ).build();
		}
		return Response.status( Status.NOT_ACCEPTABLE ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/cliente/{codigo}" )
	public Response getPublicidadeByCliente( @PathParam( "codigo" ) Integer codigo ) {
		ClienteEntity clienteEntity = clienteRepository.getItemById( codigo );
		if ( clienteEntity != null ) {
			List<Publicidade> list = new ArrayList<>();
			for ( PublicidadeEntity entity : clienteEntity.getPublicidadeEntity() ) {
				list.add( new Publicidade( entity.getPropaganda(), entity.getQuantidadeAnuncio(), entity.getVigencia(), null ) );
			}
			return Response.ok( list ).build();
		}
		return Response.status( Status.NOT_FOUND ).build();
	}

	private PublicidadeEntity save( Publicidade publicidade ) {
		PublicidadeEntity publicidadeEntity = null;
		ClienteEntity clienteEntity = clienteRepository.getItemById( publicidade.getCliente() );
		if ( clienteEntity != null ) {
			publicidadeEntity = populatePublicidadeEntity( publicidade, clienteEntity );
			repositoryPublicidade.save( publicidadeEntity );
		}
		return publicidadeEntity;

	}

	private PublicidadeEntity populatePublicidadeEntity( Publicidade publicidade, ClienteEntity clienteEntity ) {
		PublicidadeEntity publicidadeEntity = new PublicidadeEntity();
		publicidadeEntity.setPropaganda( publicidade.getPropaganda() );
		publicidadeEntity.setQuantidadeAnuncio( publicidade.getQuantidadeAnuncio() );
		publicidadeEntity.setVigencia( publicidade.getVigencia() );
		publicidadeEntity.setClienteEntity( clienteEntity );
		return publicidadeEntity;
	}

}
