
package endpoint;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import service.FileService;


@ServerEndpoint("/fileEndPoint")
public class FileEndPoint {
	
	static Set<Session> openSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	
	
	@OnMessage
    public void onMessage(String message, Session session) throws IOException {
		System.out.println("receiving message  : "+message);
		String result = "";
		FileService fileService = new FileService();
		JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
		String action = jsonObject.getString("action");
		String name = null;
		String username = null;
		String password = null;
		switch (action){
		case "login" :
			username = jsonObject.getString("username");
			password = jsonObject.getString("password");
			String role = fileService.login(username, password);
			JsonObjectBuilder user = Json.createObjectBuilder();
			user.add("action", "login");
			user.add("username", username);
			if (role != null){
				user.add("role", role);
			}
			result = user.build().toString();
			session.getBasicRemote().sendText(result);
			break;
		case "incrementResource":
			name = jsonObject.getString("name");
			int value = fileService.incrementResource(name);
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			objectBuilder.add("action", "incrementResource");
			objectBuilder.add("name", name);
			objectBuilder.add("value", value);
			result = objectBuilder.build().toString();
			sendUpdate(result);
			break;
		case "decrementResource":
			name = jsonObject.getString("name");
			int val = fileService.decrementResource(name);
			JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();
			objectBuilder2.add("action", "decrementResource");
			objectBuilder2.add("name", name);
			objectBuilder2.add("value", val);
			result = objectBuilder2.build().toString();
			sendUpdate(result);
			break;
		case "addResource":
			name = jsonObject.getString("name");
			String val1 = jsonObject.getString("value");
			fileService.addResource(name, val1);
			Map<String, Integer> ressource2 = fileService.getRessources();
			JsonObjectBuilder resourceBuilder2 = Json.createObjectBuilder();
			JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
			for (Map.Entry<String, Integer> ressourceEntry : ressource2.entrySet()) {
				arrayBuilder2.add(Json.createObjectBuilder().add("name", ressourceEntry.getKey()).add("value", ressourceEntry.getValue()));
			}
			resourceBuilder2.add("action", "addResource");
			resourceBuilder2.add("resources", arrayBuilder2);
			result = resourceBuilder2.build().toString();
			sendUpdate(result);
			break;
		case "removeResource":
			name =jsonObject.getString("name");
			fileService.removeResource(name);
			Map<String, Integer> ressource1 = fileService.getRessources();
			JsonObjectBuilder resourceBuilder1 = Json.createObjectBuilder();
			JsonArrayBuilder arrayBuilder1 = Json.createArrayBuilder();
			for (Map.Entry<String, Integer> ressourceEntry : ressource1.entrySet()) {
				arrayBuilder1.add(Json.createObjectBuilder().add("name", ressourceEntry.getKey()).add("value", ressourceEntry.getValue()));
			}
			resourceBuilder1.add("action", "removeResource");
			resourceBuilder1.add("resources", arrayBuilder1);
			result = resourceBuilder1.build().toString();
			sendUpdate(result);
			break;
		case "getResources":
			Map<String, Integer> ressource = fileService.getRessources();
			JsonObjectBuilder resourceBuilder = Json.createObjectBuilder();
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (Map.Entry<String, Integer> ressourceEntry : ressource.entrySet()) {
				arrayBuilder.add(Json.createObjectBuilder().add("name", ressourceEntry.getKey()).add("value", ressourceEntry.getValue()));
			}
			resourceBuilder.add("action", "getResources");
			resourceBuilder.add("resources", arrayBuilder);
			result = resourceBuilder.build().toString();
			session.getBasicRemote().sendText(result);
			break;
		default:
			break;
		}
		
			
    }
    @OnOpen
    public void onOpen (Session peer) {
        System.out.println("Client connecté...");
        openSessions.add(peer);
    }

    @OnClose
    public void onClose (Session peer) {
    	System.out.println("Client déconnecté...");
    	openSessions.remove(peer);
    }
    
    public void sendUpdate(String message){
    	for (Session session : openSessions) {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
}