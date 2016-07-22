package co.piui.api.repository;

import java.util.List;

import lombok.val;

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

	public void update( Long id, T entity ) {
		if ( getItemById( id ) != null ) {
			update( entity );
		}
	}

	public void update( Integer id, T entity ) {
		if ( getItemById( id ) != null ) {
			update( entity );
		}
	}

	public void delete( T entity ) {
		this.entityManager.getTransaction().begin();
		T mergedEntity = this.entityManager.merge( entity );
		this.entityManager.remove( mergedEntity );
		this.entityManager.flush();
		this.entityManager.getTransaction().commit();
		val a = this.listAll();
	}
	//
	// public void deleteById( Integer id ) {
	// T entity = getItemById( id );
	// if ( entity != null ) {
	// delete( entity );
	// }
	// }

	public void deleteById( Object id ) {
		T entity = getItemById( id );
		if ( entity != null ) {
			delete( entity );
		}
	}

	public T getItemById( Object id ) {
		return super.entityManager.find( clazz, id );
	}

	// public T getItemById( Long id ) {
	// return super.entityManager.find( clazz, id );
	// }

	public abstract List<T> listAll();
}