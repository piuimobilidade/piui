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
import co.piui.api.entity.ClienteEntity;
import co.piui.api.http.Cliente;
import co.piui.api.repository.CidadeRepository;
import co.piui.api.repository.ClienteRepository;

@Path( "/cliente" )
public class ClienteController {
	@Inject
	private CidadeRepository repositoryCidade;
	@Inject
	private ClienteRepository clienteRepository;

	@POST
	@Consumes( "application/json; charset=UTF-8" )
	@Produces( "application/json; charset=UTF-8" )
	public Response addCliente( @Context UriInfo uriInfo, Cliente cliente ) {
		ClienteEntity clienteEntity = save( cliente );
		if ( clienteEntity != null ) {
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path( Integer.toString( clienteEntity.getId() ) );
			return Response.created( builder.build() ).build();
		}
		return Response.status( Status.NOT_ACCEPTABLE ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/{codigo}" )
	public Response getCliente( @PathParam( "codigo" ) Integer codigo ) {
		ClienteEntity entity = clienteRepository.getItemById( codigo );
		if ( entity != null )
			return Response
					.ok( new Cliente( null, entity.getRazaoSocial(), entity.getDocumento(), entity.getLongitude(), entity.getLatitude(),
							entity.getCidadeEntity().getId() ) )
					.build();
		return Response.status( Status.NOT_FOUND ).build();
	}

	@GET
	@Produces( "application/json; charset=UTF-8" )
	@Path( "/cidade/{codigo}" )
	public Response getClienteByCidade( @PathParam( "codigo" ) Integer codigo ) {
		CidadeEntity cidadeEntity = repositoryCidade.getItemById( codigo );
		if ( cidadeEntity != null ) {
			List<Cliente> list = new ArrayList<>();
			for ( ClienteEntity entity : cidadeEntity.getClienteEntity() ) {
				list.add( new Cliente( null, entity.getRazaoSocial(), entity.getDocumento(), entity.getLongitude(), entity.getLatitude(),
						null ) );
			}
			return Response.ok( list ).build();
		}
		return Response.status( Status.NOT_FOUND ).build();
	}

	private ClienteEntity save( Cliente cliente ) {
		CidadeEntity cidadeEntity = repositoryCidade.getItemById( cliente.getCidade() );
		if ( cidadeEntity != null ) {
			ClienteEntity clienteEntity = populateCidadeEntity( cliente, cidadeEntity );
			return clienteRepository.save( clienteEntity );
		}
		return null;

	}

	private ClienteEntity populateCidadeEntity( Cliente cliente, CidadeEntity cidadeEntity ) {
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setCidadeEntity( cidadeEntity );
		clienteEntity.setRazaoSocial( cliente.getRazaoSocial() );
		clienteEntity.setDocumento( cliente.getDocumento() );
		clienteEntity.setLatitude( cliente.getLatitude() );
		clienteEntity.setLongitude( cliente.getLongitude() );
		return clienteEntity;
	}
}
