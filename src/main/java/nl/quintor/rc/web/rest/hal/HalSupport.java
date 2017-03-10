package nl.quintor.rc.web.rest.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import java.util.*;

/**
 * Created by marcel on 16-9-2014.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class HalSupport {

    @JsonProperty("_links")
    private List<Map<String, List<UrlElement>>> links = new ArrayList<>();

    public void addLink(String key, String url, String title) {
        this.links.add(ImmutableMap.of(key, Collections.singletonList(new UrlElement(url, Optional.of(title)))));
    }

    public void addLink(String key, String url) {
        this.links.add(ImmutableMap.of(key, Collections.singletonList(new UrlElement(url, Optional.empty()))));
    }

    public void addLinks(String key, List<UrlElement> values) {
        this.links.add(ImmutableMap.of(key, values));
    }

    public List<Map<String, List<UrlElement>>> getLinks() {
        return links;
    }

}
