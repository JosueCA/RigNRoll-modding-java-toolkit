package rickroll.utils;

import menu.JavaEvents;

public class PeekNativeMessage {

	public static void function(String message) {
		JavaEvents.SendEvent(46, 0, new NativeMessage(message));
	}
	
	private static class NativeMessage {
        String message;

        NativeMessage(String message) {
            this.message = message;
        }
    }
}
