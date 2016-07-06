package co.piui.api.repository;

import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.CidadeEntity;
import co.piui.api.entity.UF;

@Singleton
public class CidadeRepository extends PiuiRepository<CidadeEntity> {

	public CidadeRepository() {
		super( CidadeEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<CidadeEntity> listAll() {
		String hql = "FROM CidadeEntity c ORDER BY c.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

	@SuppressWarnings( "unchecked" )
	public List<CidadeEntity> list( UF uf ) {
		String hql = "FROM CidadeEntity c where c.uf = :uf ORDER BY c.id";
		return this.entityManager.createQuery( hql ).setParameter( "uf", uf ).getResultList();
	}
}
