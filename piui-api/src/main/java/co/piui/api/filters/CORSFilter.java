package co.piui.api.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter( ContainerRequestContext containerRequest, ContainerResponseContext containerResponse ) throws IOException {
		containerResponse.getHeaders().add( "Access-Control-Allow-Origin", "*" );
		containerResponse.getHeaders().add( "Access-Control-Allow-Methods", "POST, GET, PUT" );
	}
}
