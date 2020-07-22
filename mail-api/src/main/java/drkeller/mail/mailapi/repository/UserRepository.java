package drkeller.mail.mailapi.repository;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public class UserRepository {
	private static final String SERVER_URL = "http://localhost:9080/auth";
	private static final String ADMIN_REALM = "master";
	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASSWORD = "admin";
	private static final String ADMIN_CLIENT_ID = "admin-cli";
	private static final String REALM = "mail";

	public static void main(String[] args) {

		Keycloak keycloak 
			= KeycloakBuilder.builder()
				.serverUrl(SERVER_URL)
				.realm(ADMIN_REALM)
				.username(ADMIN_USERNAME)
				.password(ADMIN_PASSWORD)
				.clientId(ADMIN_CLIENT_ID)
				.resteasyClient(
						new ResteasyClientBuilder()
						.connectionPoolSize(10)
						.build())
				.build();

		UsersResource usersResource = keycloak.realm(REALM).users();
//	    UserResource userResource = usersResource.get("08afb701-fae5-40b4-8895-e387ba1902fb");
//	    System.out.println(userResource.toRepresentation().getEmail());

		List<UserRepresentation> users = usersResource.list();
		for (UserRepresentation userRepresentation : users) {
			System.out.println("username: " + userRepresentation.getUsername());
		}
		
		GroupsResource groupsResource = keycloak.realm(REALM).groups();
		List<GroupRepresentation> groups = groupsResource.groups();
		displayGroups(groups, 0);
		
	}
	
	public static void displayGroups(List<GroupRepresentation> groups, int level) {
		int subLevel = level + 1;
		for (GroupRepresentation groupRepresentation : groups) {
			if (level == 0) {
			    System.out.println( "group name: " + groupRepresentation.getName());
			} else {
				String format = "%"+ (level * 4) + "s%s%n";
			    System.out.printf(format, " ", "group name: " + groupRepresentation.getName());
			}
			
			List<GroupRepresentation> subGroups = groupRepresentation.getSubGroups();
			displayGroups(subGroups, subLevel);
		}
	}
}
