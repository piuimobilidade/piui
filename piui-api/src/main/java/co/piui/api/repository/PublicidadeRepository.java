package co.piui.api.repository;

import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.PublicidadeEntity;

@Singleton
public class PublicidadeRepository extends PiuiRepository<PublicidadeEntity> {

	public PublicidadeRepository() {
		super( PublicidadeEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<PublicidadeEntity> listAll() {
		String hql = "FROM PublicidadeEntity c ORDER BY c.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

}
