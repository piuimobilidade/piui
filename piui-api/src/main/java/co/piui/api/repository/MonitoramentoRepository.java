package co.piui.api.repository;

import java.util.List;

import javax.inject.Singleton;

import co.piui.api.entity.MonitoramentoEntity;
import co.piui.api.entity.Status;

@Singleton
public class MonitoramentoRepository extends PiuiRepository<MonitoramentoEntity> {

	public MonitoramentoRepository() {
		super( MonitoramentoEntity.class );
	}

	@SuppressWarnings( "unchecked" )
	public List<MonitoramentoEntity> listAll() {
		String hql = "FROM MonitoramentoEntity c ORDER BY c.id";
		return this.entityManager.createQuery( hql ).getResultList();
	}

	@SuppressWarnings( "unchecked" )
	public Status getLastMonitoramento() {
		String hql = "FROM MonitoramentoEntity c ORDER BY c.id DESC";
		List<MonitoramentoEntity> listResult = this.entityManager.createQuery( hql ).setMaxResults( 1 ).getResultList();
		return ( listResult.isEmpty() ? Status.INATIVO : listResult.get( 0 ).getStatus() );
	}

}
