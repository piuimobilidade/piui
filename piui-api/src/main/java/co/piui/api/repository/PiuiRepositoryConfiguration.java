package co.piui.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.piui.api.config.Configuration;
import co.piui.api.config.HibernateConfig;

public abstract class PiuiRepositoryConfiguration<T> {
	private HibernateConfig hibernateConfig = new HibernateConfig( Configuration.loadExternalConfiguration() );

	protected EntityManagerFactory entityManagerFactory = null;

	protected EntityManager entityManager = null;


	public PiuiRepositoryConfiguration() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory( hibernateConfig.entitiManager() );
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
}
