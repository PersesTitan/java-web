import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.HashSet;
import java.util.Set;

public class Crawling {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        new Crawling(set, "PersesTitan");
        System.out.println(set);
    }

    private String getGithub(String username, int page) {
        return "https://github.com/" + username + "?page=" + page + "&tab=repositories";
    }

    private void github(Set<String> set, String username) {
        for (int i = 1;;i++) {
            String urlPath = getGithub(username, i);
            Set<String> s = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlPath).openStream()))) {
                br.lines()
                        .map(String::strip)
                        .filter(v -> v.startsWith("<a class=\"Link--muted mr-3\" href=\""))
                        .forEach(s::add);
            } catch (IOException ignored) {break;}
            if (s.isEmpty()) break;
            else set.addAll(s);
        }
    }

    public Crawling(Set<String> set, String urlPath) {
        github(set, urlPath);
    }
}
