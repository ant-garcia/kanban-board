package dao;

import models.User;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

import play.db.jpa.JPAApi;

public class UserDao{

	private JPAApi jpaApi;

	@Inject
	public UserDao(JPAApi api){
		this.jpaApi = api;
	}

	public User getUser(String userEmail){
		EntityManager entityManager = jpaApi.em();
	 	User user = entityManager.find(User.class, userEmail);

		return user;
	}

	public void add(User user){
		EntityManager entityManager = jpaApi.em();

		entityManager.persist(user);
	}

	public void update(User user){
		EntityManager entityManager = jpaApi.em();

		entityManager.merge(user);
	}

	public void remove(Integer userEmail){
	 	EntityManager entityManager = jpaApi.em();
	 	User user = entityManager.find(User.class, userEmail);

        entityManager.remove(user);
	}
}