package controllers;

import models.User;

import dao.UserDao;

import views.html.login;
import views.html.signup;
import views.html.profile;

import javax.inject.Inject;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Controller;
import play.data.FormFactory;
import play.db.jpa.Transactional;

public class Application extends Controller{
	@Inject
    FormFactory mFormFactory;

    @Inject
    UserDao userDao;

	public Result index(){
		return redirect(routes.Application.login()); //just redirect to login screen
	}

	@Security.Authenticated(SecurityController.class)
	public Result profile(){
		return ok(profile.render("Welcome " + session("email"), null));
	}

	public Result login(){
		return ok(login.render("Log In"));
	}

	@Transactional
	public Result authenticate(){
		String email = mFormFactory.form().bindFromRequest().get("email");
		String password = mFormFactory.form().bindFromRequest().get("password");
		User user = userDao.getUser(email);

		if(user == null || !user.getEmail().equals(email)){
			flash("error", "Login credentials are not valid!");
			return badRequest((login.render("Log In")));
		}
		else{
			session().clear();
			session("email", email);
			return redirect(routes.Application.profile());
		}
	}

	@Security.Authenticated(SecurityController.class)
	public Result logout(){
		session().clear();
		return redirect(routes.Application.login());
	}

	public Result signup(){
		return ok(signup.render("Sign Up"));
	}

	@Transactional
	public Result createUser(){
		String name = mFormFactory.form().bindFromRequest().get("name");
		String email = mFormFactory.form().bindFromRequest().get("email");
		String password = mFormFactory.form().bindFromRequest().get("password");
		String passwordConformation = mFormFactory.form().bindFromRequest().get("passwordConformation");
		User user = userDao.getUser(email);

		if(!password.equals(passwordConformation)){
			flash("error", "Passwords do not match!");
			return badRequest((signup.render("Sign Up")));
		}
		else if(user != null){
			flash("error", "User already exists!");
			return badRequest((signup.render("Sign Up")));	
		}
		else{
			userDao.add(new User(name, email, password));
			session().clear();
			session("email", email);
			return redirect(routes.Application.profile());
		}
	}
}