import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class GitHubUserStatistics {
    public final static String USER_NAME = "auulina21";
    public static void main(String[] args) {
        String apiUrl = "https://api.github.com/users/" + USER_NAME;
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
            if (conn.getResponseCode() != 200) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) sb.append(output);
            br.close();
            conn.disconnect();
            printStatistics(sb.toString());
        } catch (Exception e) { e.printStackTrace(); }
    }
    private static void printStatistics(String json) {
        System.out.println("GitHub User Statistics:");
        System.out.println("Name: " + extractValue(json, "name"));
        System.out.println("Public Repos: " + extractValue(json, "public_repos"));
        System.out.println("Followers: " + extractValue(json, "followers"));
        System.out.println("Following: " + extractValue(json, "following"));
        System.out.println("Account Created At: " + extractValue(json, "created_at"));
    }
    private static String extractValue(String json, String key) {
        String pattern = Character.toString(34) + key + Character.toString(34) + ":(" + Character.toString(34) + ".*?" + Character.toString(34) + "|[0-9]+)";
        var m = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (!m.find()) return "N/A";
        String value = m.group(1);
        if (value.charAt(0) == 34 && value.charAt(value.length()-1) == 34) value = value.substring(1, value.length()-1);
        return value;
    }
}
