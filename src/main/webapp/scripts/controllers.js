MyApp.controller('MainCtrl', function($scope, $rootScope, FileService) {
	
	$scope.incrementResource = function(resourceName){
		FileService.incrementResource(resourceName);
	};
	
	$scope.decrementResource = function(resourceName){
		FileService.decrementResource(resourceName);
	};
	
	$scope.resources = [];
	
	$scope.getRessources = function(){
		FileService.getRessources();
	};
	$scope.removeResource = function(resourceName){
		FileService.removeResource(resourceName);
	};
	
	$scope.addResource = function(resourceName, resourceValue){
		FileService.addResource(resourceName, resourceValue);
		$scope.name = null;
		$scope.value = null;
	};
	$scope.login = function(username, password){
		FileService.login(username, password);
	};
	var connecting = function(){
		FileService.connect();
	};
	
	FileService.subscribe(function(data){
		var obj = JSON.parse(data);
		console.log(obj);
		var action = obj.action;
		switch(action){
			case 'login' :
				if (!obj.role)
					$scope.autherror = true;
				else{
					window.location.replace(obj.role + ".html");
					$rootScope.username = obj.username;
				}
				
				break;
			case 'getResources':
				$scope.resources = obj.resources;
				break;
			case 'removeResource':
				$scope.resources = obj.resources;
				break;
			case 'incrementResource':
				for (var i = 0; i < $scope.resources.length; i++) {
					var resource = $scope.resources[i];
					if(resource.name == obj.name){
						resource.value = obj.value;
						break;
					}
				}
				break;
			case 'addResource':
				$scope.resources = obj.resources;
				break;
			case 'decrementResource':
				for (var i = 0; i < $scope.resources.length; i++) {
					var resource = $scope.resources[i];
					if(resource.name == obj.name){
						resource.value = obj.value;
						break;
					}
				}
				break;
		}
		console.log($scope.resources);
		$scope.$apply();
	});
		
	
	connecting();
	
	setTimeout(function(){$scope.getRessources();}, 2000);
	//setInterval(function(){$scope.getRessources();}, 5000);
});
