package co.piui.api.repository;

import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.ClienteEntity;

@Singleton
public class ClienteRepository extends PiuiRepository<ClienteEntity> {

	public ClienteRepository() {
		super( ClienteEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<ClienteEntity> listAll() {
		String hql = "FROM ClienteEntity c ORDER BY c.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

}
