package co.piui.api.repository;

public abstract class PiuiRepository<T> extends PiuiRepositoryConfiguration<T> {

	protected Class<T> clazz;

	public PiuiRepository( Class<T> clazz ) {
		super();
		this.clazz = clazz;
	}

	public T save( T entity ) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist( entity );
		this.entityManager.flush();
		this.entityManager.getTransaction().commit();
		return entity;
	}

	public void update( T entity ) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge( entity );
		this.entityManager.getTransaction().commit();
	}

	public void update( Integer id, T entity ) {
		if ( getItemById( id ) != null ) {
			update( entity );
		}
	}

	public void delete( T entity ) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove( entity );
		this.entityManager.getTransaction().commit();
	}

	public void deleteById( Integer id ) {
		T entity = getItemById( id );
		if ( entity != null ) {
			delete( entity );
		}
	}

	public T getItemById( Integer id ) {
		return super.entityManager.find( clazz, id );
	}
}