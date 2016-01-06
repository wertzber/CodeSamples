package couchbase;

import com.couchbase.client.java.document.RawJsonDocument;
import com.google.gson.Gson;

import java.util.Optional;

/**
 * Created by eladw on 3/1/2015.
 */
public class OptionalGet <V> {

    Gson gson;
    CouchbaseClientWrapper couchbaseClient = CouchbaseClientWrapper.getInstance().getInstance();

    public OptionalGet(){
        gson = new Gson();
    }


    public Optional<V> getUtil(String key, Class<V> clazz) {

        RawJsonDocument rawJsonDocument = couchbaseClient.getSyncBucket().get(key, RawJsonDocument.class);
        Optional<V> conversationDto = Optional.of(gson.fromJson(rawJsonDocument.content(), clazz));
        return (Optional<V>) conversationDto;
    }

}
