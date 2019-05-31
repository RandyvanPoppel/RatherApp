package models.hateoas;

public class Link {
    private String link;
    private String rel;
    private RequestMethod method;
    private String[] headerParams;
    private String[] queryParams;

    public Link(String link, String rel, RequestMethod method, String[] headerParams, String[] queryParams)
    {
        this.link = link;
        this.rel = rel;
        this.method = method;
        this.headerParams = headerParams;
        this.queryParams = queryParams;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String[] getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String[] queryParams) {
        this.queryParams = queryParams;
    }

    public String[] getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(String[] headerParams) {
        this.headerParams = headerParams;
    }
}
