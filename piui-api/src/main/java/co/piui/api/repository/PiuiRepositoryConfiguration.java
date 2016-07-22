package co.piui.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class PiuiRepositoryConfiguration<T> {
	//private HibernateConfig hibernateConfig = new HibernateConfig( Configuration.loadExternalConfiguration() );

	protected EntityManagerFactory entityManagerFactory = null;

	protected EntityManager entityManager = null;


	public PiuiRepositoryConfiguration() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory( "development" );
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
}
