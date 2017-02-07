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
    SecurityController mSecurityController;

    private Json mJson;
	private List<Widget> mUserWidgets;
	private final USER_EMAIL = mSecurityController.getUsername(ctx);

	public Result getUserWidgets(){
		mUserWidgets = mWidgetDB.getWidgets(USER_EMAIL);
		if(mUserWidgets.isEmpty())
			return redirect(showWidgetModal());
		else
			return ok(mJson.toJson(mUserWidgets));
	}

	public Result getWidget(long id){
		Widget widget = mWidgetDB.getWidget(USER_EMAIL, id);
		if(widget == null)
			return notFound("No widget exists with that id!");

		return ok(mJson.toJson(mWidget));
	}

	public Result index(){
		return redirect(getUserWidgets());
	}

	public Result createWidget(){
		String name = mFormFactory.form().bindFromRequest().get("name");
		String description = mFormFactory.form().bindFromRequest().get("description");
		long id = ++mWidgetDB.getUserWidgetCount(USER_EMAIL);

		mWidgetDB.addWidget(USER_EMAIL, new Widget(id, name, description));
		return redirect(routes.Application.profile());
	}

	public Result updateWidget(long id, String status){
		if(mWidgetDB.containsWidget(USER_EMAIL, id)){
			mWidgetDB.updateWidgetStatus(USER_EMAIL, id);
			return redirect(routes.Application.profile());
		}
		else
			return notFound("No widget exists with that id!");
	}

	public Result removeWidget(long id){
		if(mWidgetDB.containsWidget(USER_EMAIL, id)){
			mWidgetDB.removeWidget(USER_EMAIL, id);
			return redirect(routes.Application.profile());
		}
		else
			return notFound("No widget exists with that id!");
	}

	public Result showWidgetModal(){
		return ok(views.html.addwidget.render());
	}
}