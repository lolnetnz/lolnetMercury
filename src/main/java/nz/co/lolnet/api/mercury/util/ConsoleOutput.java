package nz.co.lolnet.api.mercury.util;

public class ConsoleOutput {
	
	public static void info(String string) {
		System.out.println("[" + Date.getTime() + " INFO]: " + string);
	}
	
	public static void warn(String string) {
		System.out.println("[" + Date.getTime() + " WARN]: " + string);
	}
	
	public static void error(String string) {
		System.out.println("[" + Date.getTime() + " ERROR]: " + string);
	}
}