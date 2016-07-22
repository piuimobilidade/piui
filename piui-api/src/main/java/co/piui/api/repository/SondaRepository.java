package co.piui.api.repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import co.piui.api.entity.SondaEntity;

@Singleton
public class SondaRepository extends PiuiRepository<SondaEntity> {

	public SondaRepository() {
		super( SondaEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<SondaEntity> listAll() {
		String hql = "FROM SondaEntity c ORDER BY c.id";
		// String hql = "FROM SondaEntity ";
		Query query = this.entityManager.createQuery( hql );
		return query.getResultList();
	}

	@SuppressWarnings( "unchecked" )
	public List<SondaEntity> getSondaWithProblemLastMinutes( int minutes ) {
		Date data = new Date( Instant.now().minusSeconds( minutes * 60 ).getEpochSecond() );
		String hql = "FROM SondaEntity c where c.data < :data ORDER BY c.id";
		return this.entityManager.createQuery( hql ).setParameter( "data", data ).getResultList();
	}
	
	public void deleteAll(){
		this.entityManager.getTransaction().begin();
		Query q2 = this.entityManager.createNativeQuery("DELETE FROM sonda");
	    q2.executeUpdate();
		this.entityManager.getTransaction().commit();
	}
}
