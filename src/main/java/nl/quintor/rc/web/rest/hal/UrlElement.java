package nl.quintor.rc.web.rest.hal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlElement {
    String href;


    String title;

    public UrlElement(String href, Optional<String> title) {
        this.title = title.isPresent() ? title.get() : null;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }
}