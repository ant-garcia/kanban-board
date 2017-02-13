package controllers;

import models.User;

import views.html.login;
import views.html.signup;
import views.html.profile;

import javax.inject.Inject;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Controller;
import play.data.FormFactory;

public class Application extends Controller{
	@Inject
    FormFactory mFormFactory;

    @Inject
    UserDB db;

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

	public Result authenticate(){
		String email = mFormFactory.form().bindFromRequest().get("email");
		String password = mFormFactory.form().bindFromRequest().get("password");

		if(db.getUser(email, password) == null){
			System.out.println("Login credentials are not valid!");
			System.out.println(email);
			System.out.println(password);
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

	public Result createUser(){
		String name = mFormFactory.form().bindFromRequest().get("name");
		String email = mFormFactory.form().bindFromRequest().get("email");
		String password = mFormFactory.form().bindFromRequest().get("password");
		String passwordConformation = mFormFactory.form().bindFromRequest().get("passwordConformation");

		if(!password.equals(passwordConformation)){
			System.out.println(password);
			System.out.println(passwordConformation);
			System.out.println("Passwords do not match!");
			return badRequest((signup.render("Sign Up")));
		}
		else if(db.containsUser(email)){
			System.out.println("User already exists!");
			return badRequest((signup.render("Sign Up")));	
		}
		else{
			db.addUser(new User(name, email, password));
			session().clear();
			session("email", email);
			return redirect(routes.Application.profile());
		}
	}
}
