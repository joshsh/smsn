package net.fortytwo.smsn.brain;

public interface Params {
    enum Format{json, wiki}

    String
            CONFIGURATION = "configuration",
            DEFAULT_SOURCE = "defaultSource",
            DEFAULT_WEIGHT = "defaultWeight",
            FILTER = "filter",
            HEIGHT = "height",
            ID = "id",
            INCLUDE_TYPES = "includeTypes",
            MAX_RESULTS = "maxResults",
            MIN_SOURCE = "minSource",
            MIN_WEIGHT = "minWeight",
            NAME = "name",
            QUERY = "query",
            QUERY_TYPE = "queryType",
            REQUEST = "request",
            ROOT = "root",
            STYLE = "style",
            TITLE = "title",
            TITLE_CUTOFF = "titleCutoff",
            VIEW = "view",
            VIEW_FORMAT = "viewFormat",
            VIEW_TITLE = "title";
}
