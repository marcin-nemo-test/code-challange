package tk.zielony.codechallange.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Marcin on 2016-05-12.
 */
public class LocalAPI extends DataAPI {
    private static final double FAIL_RATE = 0.0;
    private static final double RESPONSE_DEVIATION = 500;
    private static final double AVG_REPONSE_TIME = 800;

    HashMap<String, HashMap<String, Object>> resources = new HashMap<>();

    public LocalAPI() {
        resources.put(POST, new HashMap<String, Object>());
        resources.put(COMMENT, new HashMap<String, Object>());

        ObjectMapper om = new ObjectMapper();

        try {
            Post[] posts = om.readValue(postJSON, Post[].class);
            for (Post p : posts)
                resources.get(POST).put("/" + p.id, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected <Type> Type getInternal(String endpoint, Class<Type> dataClass) throws APIException {
        simulateTraffic();

        if(endpoint.equals(POST)){
            HashMap<String, Object> postMap = resources.get(POST);
            Post[] posts = new Post[postMap.size()];
            postMap.values().toArray(posts);
            return (Type) posts;
        }

        return (Type) resources.get(endpoint);
    }

    /**
     * Here's where the fun with custom API implementation begins
     *
     * @throws APIException
     */
    private void simulateTraffic() throws APIException {
        try {
            Thread.sleep((long) ((Math.random() - 0.5) * RESPONSE_DEVIATION + AVG_REPONSE_TIME));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Math.random() < FAIL_RATE)
            throw new APIException("404");
    }


    String postJSON = "[\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 1,\n" +
            "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
            "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 2,\n" +
            "    \"title\": \"qui est esse\",\n" +
            "    \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 3,\n" +
            "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\n" +
            "    \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 4,\n" +
            "    \"title\": \"eum et est occaecati\",\n" +
            "    \"body\": \"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 5,\n" +
            "    \"title\": \"nesciunt quas odio\",\n" +
            "    \"body\": \"repudiandae veniam quaerat sunt sed\\nalias aut fugiat sit autem sed est\\nvoluptatem omnis possimus esse voluptatibus quis\\nest aut tenetur dolor neque\"\n" +
            "  }\n" +
            "]";

}
