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

}
