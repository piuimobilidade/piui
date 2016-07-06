package co.piui.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import co.piui.api.entity.CidadeEntity;
import co.piui.api.entity.UF;
import co.piui.api.http.Cidade;
import co.piui.api.repository.CidadeRepository;

@Path( "/cidade" )
public class CidadeController {

	private CidadeRepository repository = new CidadeRepository();

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	@Produces( "application/json; charset=UTF-8" )
	public Response addCidade( @Context UriInfo uriInfo, Cidade cidade ) {
		CidadeEntity entity = new CidadeEntity();
		entity.setNome( cidade.getNome() );
		entity.setUf( cidade.getUf() );
		repository.save( entity );
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path( Integer.toString( entity.getId() ) );
		return Response.created( builder.build() ).build();
	}

	@PUT
	@Consumes( "application/json; charset=UTF-8" )
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/{codigo}" )
	public Response updadeCidade( @Context UriInfo uriInfo, @PathParam( "codigo" ) Integer codigo, Cidade cidade ) {
		CidadeEntity entity = new CidadeEntity();
		entity.setId( codigo );
		entity.setNome( cidade.getNome() );
		entity.setUf( cidade.getUf() );
		repository.update( codigo, entity );
		return Response.ok().build();
	}

	@DELETE
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/{codigo}" )
	public Response deleteCidade( @PathParam( "codigo" ) Integer codigo ) {
		repository.deleteById( codigo );
		return Response.noContent().build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/{codigo}" )
	public Response getCidade( @PathParam( "codigo" ) Integer codigo ) {
		CidadeEntity entity = repository.getItemById( codigo );
		if ( entity != null )
			return Response.ok( new Cidade( null, entity.getNome(), entity.getUf() ) ).build();
		return Response.status( Status.NOT_FOUND ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	public List<Cidade> getCidadesFindByUF( @QueryParam( "uf" ) UF uf ) {
		List<CidadeEntity> cidadesEntity = null;
		if ( uf != null ) {
			cidadesEntity = repository.list( uf );
		} else {
			cidadesEntity = repository.listAll();
		}
		return getCidadeSelected( cidadesEntity );
	}

	private List<Cidade> getCidadeSelected( List<CidadeEntity> listCidade ) {
		List<Cidade> cidades = new ArrayList<Cidade>();
		for ( CidadeEntity cidade : listCidade ) {
			cidades.add( new Cidade( cidade.getId(), cidade.getNome(), cidade.getUf() ) );
		}
		return cidades;
	}

	/**
	 * 
	 * Metodos complementares
	 */

	@HEAD
	@Produces( "application/json; charset=UTF-8" )
	@Path( "{path:.*}" )
	public Response getCidadeHead( @PathParam( "path" ) String path ) {
		return Response.status( Response.Status.NO_CONTENT ).build();
	}

	@HEAD
	@Produces( "application/json; charset=UTF-8" )
	public Response getCidadesFindByUFHead( @QueryParam( "uf" ) UF uf ) {
		return Response.status( Response.Status.NO_CONTENT ).build();
	}

	@OPTIONS
	public Response options() {
		return Response.status( Response.Status.NO_CONTENT ).build();
	}

	@OPTIONS
	@Path( "{path:.*}" )
	public Response optionsAll( @PathParam( "path" ) String path ) {
		return Response.status( Response.Status.NO_CONTENT ).build();
	}

}
