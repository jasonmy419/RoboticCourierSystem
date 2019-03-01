package util;

public class Tool {

	public static String StringProccessing(String input) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			int count = 0;
			while (i < input.length() && input.charAt(i) == '\\') {
				count++;
				i++;
			}
			for (int j = 0; j < count*2; j++) {
				sb.append('\\');
			}
			sb.append(input.charAt(i));
		}
		return sb.toString();
	}
}
