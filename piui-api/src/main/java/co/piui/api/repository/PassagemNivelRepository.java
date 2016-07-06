package co.piui.api.repository;

import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.PassagemNivelEntity;

@Singleton
public class PassagemNivelRepository extends PiuiRepository<PassagemNivelEntity> {

	public PassagemNivelRepository() {
		super( PassagemNivelEntity.class );

	}

	@SuppressWarnings( "unchecked" )
	public List<PassagemNivelEntity> listAll() {
		String hql = "FROM PassagemNivelEntity p ORDER BY p.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

}
