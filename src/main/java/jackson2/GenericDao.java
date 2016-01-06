package jackson2;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by dannyl on 2/2/2015.
 */
public interface GenericDao<K,V> {

    /**
     * Store data V according to key K
     * @param k
     * @param v
     */
    public void persist(K k, V v);

    /**
     * get single value according to one key
     * @param k key to use
     * @return Optional<V>
     */
    public Optional<V> get(K k);

    /**
     * Get a V by using two keys
     * @param k1 key 1
     * @param k2 key 2
     * @return Optional<V>
     */
    public default Optional<V> get(K k1, K k2){
        System.err.println("GenericDao get two keys use default empty");
        return Optional.empty();
    }

    /**
     * Delete bu key
     * @param k key to use for delete
     */
    public void delete(K k);

    /**
     * Get List<V> of multiple keys (kind of get bulk)
     * @param keys
     * @return
     */
    public default Optional<List<V>> getValuesByKeys(List<K> keys){
        System.err.println("GenericDao getValuesByKeys use default empty");
        return Optional.empty();
    }

    /**
     * Update a V according to key
     * @param k key to use for update
     * @param v value to update
     */
    public void update(K k, V v);


    /**
     * Get all entries
     * @return <Collection<V>>
     */
    public default Optional<Collection<V>> getAll(){
        System.err.println("GenericDao getAll use default empty");
        return Optional.empty();
    }

    /**
     * Get all values of a key ( for example when key has multiple values associated)
     * @param k key to use
     * @return Collection<V>
     */
    public default Optional<Collection<V>> getAll(K k) {
        System.err.println("GenericDao getAll(k) use default empty");
        return Optional.empty();
    }



}
