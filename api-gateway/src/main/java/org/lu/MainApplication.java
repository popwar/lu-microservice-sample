package org.lu;

public class MainApplication {

	public static void main(String[] args) {
		String serverName = args[0] != null ? args[0].toLowerCase()
				: "not specified server";

		if (serverName.equals("gateway") || serverName.equals("reg")) {
			ApiGatewayApplication.main(args);
		} else if (serverName.equals("caller")) {
			ApiCallerApplication.main(args);
		} else {
			System.out.println("Unknown server type: " + serverName);
		}
	}
}
