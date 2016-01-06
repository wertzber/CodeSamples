package couchbase;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: eladw
 * Date: 12/18/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class CouchbaseClientWrapper {

    private static final Logger logger = LoggerFactory.getLogger(CouchbaseClientWrapper.class);

    private Cluster cluster;
    private AsyncBucket asyncBucket;
    private Bucket syncBucket;


    private static CouchbaseClientWrapper instance = null;

    public static CouchbaseClientWrapper getInstance(){
        if(instance == null){
            instance = new CouchbaseClientWrapper();
        }
        return instance;
    }

    private CouchbaseClientWrapper(){

        try {
            // connect to the localhost cluster -add list of nodes
            cluster = CouchbaseCluster.create("127.0.0.1");

            // connect to the default bucket
            asyncBucket = cluster.openBucket("msservice","elad").async();

            syncBucket = cluster.openBucket("msservice", "elad");



        }  catch (Exception e) {
            logger.error("[CB] Couchbase Client failed to connect to Couchbase server.");
        }
    }

    /**
     * Couchbase shutdown
     */
    public void shutdown(){
        logger.info("[CB] ShutDown Couchbase client");
        if(cluster!= null) cluster.disconnect();

    }


    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }


    public AsyncBucket getAsyncBucket() {
        return asyncBucket;
    }

    public void setAsyncBucket(AsyncBucket asyncBucket) {
        this.asyncBucket = asyncBucket;
    }

    public Bucket getSyncBucket() {
        return syncBucket;
    }

    public void setSyncBucket(Bucket syncBucket) {
        this.syncBucket = syncBucket;
    }
}