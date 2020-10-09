import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameToNameConverter {

    public void convert() {
        final String uid = getUserId();
        final String name = uidToName(uid);

        System.out.println(name);

    }

    private String getUserId() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String:\n");
        String s;
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s.strip();
    }

    private URL urlForHuman(String uid) {
        final String urlString = "https://www.ecs.soton.ac.uk/people/" + uid;

        try {
            final URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String uidToName(String uid) {
        final URL url = urlForHuman(uid);

        InputStream inputStream;
        try {
            inputStream = url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        final StringBuffer buffer = new StringBuffer();
        while (true) {
            int character;
            try {
                character = inputStream.read();
                // The last character of the HTML string has been reached
                if (character == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
            buffer.append((char) character);
        }

        final String html = buffer.toString();


        Pattern pattern = Pattern.compile("property=\"name\">([^<]*)");
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Name not found for " + uid;
        }
    }
}
