package controllers;

import java.util.List;
import javax.inject.Inject;

import models.Widget;

import dao.WidgetDao;

import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;
import play.data.FormFactory;
import play.db.jpa.Transactional;


public class WidgetController extends Controller{
	@Inject
    FormFactory mFormFactory;

    @Inject
    WidgetDao widgetDao;

    @Inject
    Application mApplication;

    private Json mJson;

	@Transactional
	public Result getUserWidgets(){
		String email = session("email");
		System.out.println(email);
		List<Widget> widgets = widgetDao.getAll(email);
		if(widgets.isEmpty())
			return showWidgetModal();
		else
			return ok(mJson.toJson(widgets));
	}

	@Transactional
	public Result getWidget(int id){
		Widget widget = widgetDao.getWidgetById(id);
		if(widget == null)
			return notFound("No widget exists with that id!");

		return ok(mJson.toJson(widget));
	}

	@Transactional
	public Result index(){
		return getUserWidgets();
	}

	@Transactional
	public Result createWidget(){
		String name = mFormFactory.form().bindFromRequest().get("name");
		String description = mFormFactory.form().bindFromRequest().get("description");
		int id = Math.toIntExact(widgetDao.getUserWidgetCount() + 1);

		widgetDao.add(new Widget(id, session("email"), name, description));
		return redirect(routes.Application.profile());
	}

	@Transactional
	public Result updateWidget(int id, String status){
		Widget widget = widgetDao.getWidgetById(id);
		if(widget != null){
			widget.setStatus(status);
			widgetDao.update(widget);
			return redirect(routes.Application.profile());
		}
		else
			return notFound("No widget exists with that id!");
	}

	@Transactional
	public Result removeWidget(int id){
		Widget widget = widgetDao.getWidgetById(id);
		if(widget != null){
			widgetDao.remove(widget);
			return redirect(routes.Application.profile());
		}
		else
			return notFound("No widget exists with that id!");
	}

	public Result showWidgetModal(){
		return ok(views.html.addwidget.render()); //enables the widget model
	}
}