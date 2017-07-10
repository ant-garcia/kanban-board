package dao;

import models.Widget;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

import play.db.jpa.JPAApi;

public class WidgetDao{

	private JPAApi jpaApi;

	@Inject
	public WidgetDao(JPAApi api){
		this.jpaApi = api;
	}

	public Widget getWidgetById(Integer widgetId){
		EntityManager entityManager = jpaApi.em();
	 	Widget widget = entityManager.find(Widget.class, widgetId);

		return widget;
	}

	public void add(Widget widget){
		EntityManager entityManager = jpaApi.em();

		entityManager.persist(widget);
	}

	public void update(Widget widget){
		EntityManager entityManager = jpaApi.em();

		entityManager.merge(widget);
	}

	public void remove(Widget widget){
	 	EntityManager entityManager = jpaApi.em();

        entityManager.remove(widget);
	}

	@SuppressWarnings("unchecked")
	public List<Widget> getAll(String userEmail){
		EntityManager entityManager = jpaApi.em();
		TypedQuery<Widget> query = entityManager.createQuery("select w from Widget w where w.email=:mail", Widget.class);

		query.setParameter("mail", userEmail);

		List<Widget> widgets = query.getResultList();

		return widgets;
	}

	public Long getUserWidgetCount(){
		EntityManager entityManager = jpaApi.em();
		Query query = entityManager.createQuery("select count(*) from Widget w ");

		Long count = (Long) query.getSingleResult();

		return count;	
	}
}