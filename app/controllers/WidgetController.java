package controllers;

import javax.inject.Inject;

import models.Widget;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import play.data.FormFactory;


public class WidgetController extends Controller{
	@Inject
    FormFactory mFormFactory;

    private Json mJson;
	private Widget mWidget;

	public Result get(long id){
		if(mWidget == null)
			return notFound("");

		return ok(mJson.toJson(mWidget));
	}

	public Result index(){
		return ok(mJson.toJson(mWidget));
	}

	public Result create(){
		String name = mFormFactory.form().bindFromRequest().get("name");
		String description = mFormFactory.form().bindFromRequest().get("description");
		mWidget = new Widget(name, description);
		return redirect(routes.Application.profile());
	}

	public Result update(long id){
		return TODO;
	}

	public Result delete(long id){
		return TODO;
	}

	public Result showWidgetModal(){
		return ok(views.html.addwidget.render());
	}
}