package tk.zielony.codechallange.api;

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
    }

    @Override
    protected <Type> Type getInternal(String endpoint, Class<Type> dataClass) throws APIException {
        simulateTraffic();

        return (Type) resources.get(endpoint);
    }

    /**
     * Here's where the fun with custom API implementation begins
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

}
