$(document).ready(function(){
	'use strict';
	init();
});

function init(){
    load();
}

function load(){
	$.getJSON('/api/widgets', function(data){
		$('body').append(newWidget(data));
	});
}

function createWidgets(data){

}

function newWidget(data){
	var div = $('<div/>',{
		'class': 'card'
	});

	var header = $('<header/>',{
		'class': 'card-header',
		'text': data.name
	});

	var container = $('<div/>',{
		'class': 'card-container'
	});

	var p = $('<p/>',{
		'class': 'card-description',
		'text': data.description
	});

	div.append(header);
	div.append(container);
	container.append(p);
	return div;
}