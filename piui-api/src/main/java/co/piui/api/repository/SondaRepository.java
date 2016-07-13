package co.piui.api.repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.SondaEntity;

@Singleton
public class SondaRepository extends PiuiRepository<SondaEntity> {

	public SondaRepository() {
		super( SondaEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<SondaEntity> listAll() {
		String hql = "FROM SondaEntity c ORDER BY c.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

	@SuppressWarnings( "unchecked" )
	public List<SondaEntity> getSondaWithProblemLastMinutes( int minutes ) {
		Date data = new Date( Instant.now().minusSeconds( minutes * 60 ).getEpochSecond() );
		String hql = "FROM SondaEntity c where c.data < :data ORDER BY c.id";
		return this.entityManager.createQuery( hql ).setParameter( "data", data ).getResultList();
	}
}
