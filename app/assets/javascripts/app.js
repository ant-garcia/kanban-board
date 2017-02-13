$(document).ready(function(){
	'use strict';
	init();
});

function init(){
    load();
}

function load(){
	$.getJSON('/api/widgets', function(widgets){
		createWidgets(widgets);
	});
}

function createWidgets(widgets){
	for(i = 0; i < widgets.length; i++){
		if(widgets[i].status == 'TODO'){
			$('#todo').append(addWidget(widgets[i]));
		}
		else if(widgets[i].status == 'IN-PROGRESS'){
			$('#inProgress').append(addWidget(widgets[i]));
		}
		else{
			$('#done').append(addWidget(widgets[i]));	
		}
	}
}

function createHeader(widgetName, widgetId, widgetStatus){
	var header = $('<header/>',{
		'class': 'card-header-' + widgetStatus,
		'text': widgetName
	});

	var optionElements = [];

	optionElements.push($('<a/>',{
		'href': '#',
		'onclick': 'return removeWidget(' + widgetId + ')'
	}).append($('<span/>',{'class': 'glyphicon glyphicon-remove'})));

	if(widgetStatus == 'TODO' || widgetStatus == 'IN-PROGRESS'){
		optionElements.push($('<a/>',{
			'href': '#',
			'onclick': 'return updateWidgetStatus(' + widgetId + ', \'' + widgetStatus + '\', \'' + "right" + '\')'
		}).append($('<span/>',{'class': 'glyphicon glyphicon-menu-right'})));
	}

	if(widgetStatus == 'DONE' || widgetStatus == 'IN-PROGRESS'){
		optionElements.push($('<a/>',{
			'href': '#',
			'onclick': 'return updateWidgetStatus(' + widgetId + ', \'' + widgetStatus + '\', \'' + "left" + '\')'
		}).append($('<span/>',{'class': 'glyphicon glyphicon-menu-left'})));
	}

	header.append(optionElements);

	return header;
}

function addWidget(widgetData){
	var li = $('<li/>',{
		'class': 'list-group-item clearfix',
		'id': widgetData.id
	});

	var div = $('<div/>',{
		'class': 'card'
	});

	var header = createHeader(widgetData.name, widgetData.id, widgetData.status);

	var container = $('<div/>',{
		'class': 'card-container'
	});

	var p = $('<p/>',{
		'class': 'card-description',
		'text': widgetData.description
	});

	div.append(header);
	div.append(container);
	container.append(p);
	li.append(div);
	return li;
}

function removeWidget(widgetId){
	$.ajax(jsRoutes.controllers.WidgetController.removeWidget(widgetId))
		.done($('#' + widgetId).remove());
}

function updateWidgetStatus(widgetId, widgetStatus, widgetList){
	var newStatus;

	if(widgetStatus == 'TODO'){
		newStatus = "IN-PROGRESS";
	}
	else if(widgetStatus == 'IN-PROGRESS' && widgetList == 'left'){
		newStatus = "TODO";	
	}
	else if(widgetStatus == 'IN-PROGRESS' && widgetList == 'right'){
		newStatus = "DONE";	
	}
	else{
		newStatus = "IN-PROGRESS";
	}

	$.ajax(jsRoutes.controllers.WidgetController.updateWidget(widgetId, newStatus))
		.done(appendList(widgetId, newStatus));
}

function appendList(widgetId, listName){
	var widget = $('#' + widgetId);
	var headerText = widget.find("header").text();

	widget.find("header").replaceWith(createHeader(headerText, widgetId, listName));

	if(listName == 'TODO'){
		$('#todo').append(widget);
	}
	else if(listName == 'IN-PROGRESS'){
		$('#inProgress').append(widget);
	}
	else if(listName == 'DONE'){
		$('#done').append(widget);
	}
}
