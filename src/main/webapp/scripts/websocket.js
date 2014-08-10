MyApp.factory("FileService", function() {
var FileService = {};

FileService.connect = function(){
	if(FileService.ws && FileService.ws.readyState == WebSocket.OPEN){
		return;
	}
	
	var wsUri = "ws://"+document.location.host + "/PJL/" + "fileEndPoint";
	var webSocket = new WebSocket(wsUri);
	webSocket.onopen = function(){
		console.log("CONNECTED TO FILE WEBSOCKET");
	};
	webSocket.onerror = function(){
		console.log("Failed to open a connection to file websocket");
	};
	webSocket.onclose = function(){
		console.log("DISCONNECTED FROM FILE WEBSOCKET");
	};
	webSocket.onmessage = function(message){
		FileService.callback(message.data);
	};
	
	FileService.ws = webSocket;
};


	FileService.subscribe = function(callback){
		FileService.callback = callback;
};


	FileService.getRessources = function (){
		var json = JSON.stringify({
			action : "getResources"
		});
		FileService.ws.send(json);
	};
	
	
	FileService.incrementResource = function (resourceName){
		var json = JSON.stringify({
			action: "incrementResource",
			name: resourceName
		});
		FileService.ws.send(json);
	};
	FileService.decrementResource = function (resourceName){
		var json = JSON.stringify({
			action: "decrementResource",
			name: resourceName,
		});
		FileService.ws.send(json);
	};
	FileService.removeResource = function (resourceName){
		var json = JSON.stringify({
			action: "removeResource",
			name: resourceName,
		});
		FileService.ws.send(json);
	};
	FileService.addResource = function(resourceName, resourceValue){
		var json = JSON.stringify({
			action: "addResource",
			name: resourceName,
			value: resourceValue,
		});
		FileService.ws.send(json);
	};
	FileService.login = function(username, password){
		var json = JSON.stringify({
			action: "login",
			username: username,
			password: password,
		});
		FileService.ws.send(json);
	};
	return FileService;
});

