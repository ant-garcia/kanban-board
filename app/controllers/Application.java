package controllers;

import models.UserDB;

import javax.inject.Inject;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Controller;
import play.data.FormFactory;

import views.html.*;

public class Application extends Controller{
	@Inject
    FormFactory mFormFactory;

    UserDB db = new UserDB();

	public Result index(){
		return redirect(routes.Application.login());
	}

	@Security.Authenticated(SecurityController.class)
	public Result home(){
		return ok(home.render("Your new application is ready."));
	}

	@Security.Authenticated(SecurityController.class)
	public Result profile(){
		return ok(profile.render("Your new application is ready.", null));
	}

	public Result login(){
		return ok(login.render("Log In"));
	}

	public Result authenticate(){
		String email = mFormFactory.form().bindFromRequest().get("email");
		String password = mFormFactory.form().bindFromRequest().get("password");

		if(!db.isValid(email, password)){
			flash("errors", "Login credentials are not valid!");
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
			flash("errors", "Passwords do not match!");
			System.out.println(password);
			System.out.println(passwordConformation);
			System.out.println("Passwords do not match!");
			return badRequest((signup.render("Sign Up")));
		}
		else if(db.isUser(email) && db.isEmpty()){
			System.out.println("User already exists!");
			return badRequest((signup.render("Sign Up")));	
		}
		else{
			db.addUser(name, email, password);
			session().clear();
			session("email", email);
			return redirect(routes.Application.profile());
		}
	}
}
