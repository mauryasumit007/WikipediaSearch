package in.androidmate.mauryasumit.wikidata.network;


import java.util.Map;

import in.androidmate.mauryasumit.wikidata.models.Wikiresponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface NetworkInterface {


    @GET("api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=200&pilimit=50&wbptterms=description&gpslimit=50")

    Observable<Wikiresponse> getWikiResults(@Query("gpssearch") String query);


}
