package controllers;

import models.User;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

public class SecurityController extends Security.Authenticator{

	@Override
	public String getUsername(Context ctx){
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx){
		return redirect(routes.Application.login());
	}

	public String getUserEmail(Context ctx){
		return ctx.session().get("email");
	}

	public boolean isLoggedIn(Context ctx){
		return (getUserEmail(ctx) != null);
	}

	/*public User getUser(Context ctx){
		return (isLoggedIn(ctx) ? UserDB.getUser(getUser(ctx)) : null);
	}*/
}