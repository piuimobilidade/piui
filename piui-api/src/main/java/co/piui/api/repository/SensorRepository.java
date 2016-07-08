package co.piui.api.repository;

import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.SensorEntity;

@Singleton
public class SensorRepository extends PiuiRepository<SensorEntity> {

	public SensorRepository() {
		super( SensorEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<SensorEntity> listAll() {
		String hql = "FROM SensorEntity c ORDER BY c.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

	@SuppressWarnings( "unchecked" )
	public SensorEntity getSensorByIdentificacao( String identificacao ) {
		String hql = "FROM SensorEntity c where c.identificacao = :identificacao ORDER BY c.id";
		List<SensorEntity> listresult = this.entityManager.createQuery( hql ).setParameter( "identificacao", identificacao )
				.setMaxResults( 1 )
				.getResultList();
		return listresult.get( 0 );
	}

}
