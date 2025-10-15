package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private Map<String, List<String>> cashe=new HashMap<>();
    private BreedFetcher fetcher;
    public CachingBreedFetcher() {}
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        if(cashe.containsKey(breed))
            return cashe.get(breed);
        try{
            ArrayList<String> list = (ArrayList<String>) fetcher.getSubBreeds(breed);
            callsMade++;
            cashe.put(breed, list);
            return list;
        }catch (BreedNotFoundException e){
            System.out.println(e);
        }
        return null;
    }

    public int getCallsMade() {
        return callsMade;
    }
}