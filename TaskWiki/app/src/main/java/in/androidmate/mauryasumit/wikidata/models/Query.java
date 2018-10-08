package in.androidmate.mauryasumit.wikidata.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("redirects")
    @Expose
    private List<Redirect> redirects = null;
    @SerializedName("pages")
    @Expose
    private List<Page> pages = null;

    public List<Redirect> getRedirects() {
        return redirects;
    }

    public void setRedirects(List<Redirect> redirects) {
        this.redirects = redirects;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

}