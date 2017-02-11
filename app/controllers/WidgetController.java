package controllers;

import java.util.List;
import javax.inject.Inject;

import models.Widget;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import play.data.FormFactory;


public class WidgetController extends Controller{
	@Inject
    FormFactory mFormFactory;

    @Inject
    WidgetDB mWidgetDB;

    @Inject
    Application mApplication;

    private Json mJson;
	private List<Widget> mUserWidgets;

	public Result getUserWidgets(){
		mUserWidgets = mWidgetDB.getWidgets(session("email"));
		if(mUserWidgets.isEmpty())
			return showWidgetModal();
		else
			return ok(mJson.toJson(mUserWidgets));
	}

	public Result getWidget(int id){
		Widget widget = mWidgetDB.getWidget(session("email"), id);
		if(widget == null)
			return notFound("No widget exists with that id!");

		return ok(mJson.toJson(widget));
	}

	public Result index(){
		return getUserWidgets();
	}

	public Result createWidget(){
		String name = mFormFactory.form().bindFromRequest().get("name");
		String description = mFormFactory.form().bindFromRequest().get("description");
		int id = mWidgetDB.getUserWidgetCount(session("email")) + 1;

		mWidgetDB.addWidget(session("email"), new Widget(id, name, description));
		return redirect(routes.Application.profile());
	}

	public Result updateWidget(int id, String status){
		if(mWidgetDB.containsWidget(session("email"), id)){
			mWidgetDB.updateWidgetStatus(session("email"), id, status);
			return redirect(routes.Application.profile());
		}
		else
			return notFound("No widget exists with that id!");
	}

	public Result removeWidget(int id){
		if(mWidgetDB.containsWidget(session("email"), id)){
			mWidgetDB.removeWidget(session("email"), id);
			return redirect(routes.Application.profile());
		}
		else
			return notFound("No widget exists with that id!");
	}

	public Result showWidgetModal(){
		return ok(views.html.addwidget.render());
	}
}