package com.accessone.resources;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
@JsonFormat(
        shape = JsonFormat.Shape.OBJECT
)
public enum Capability {
    OC_ID_LOOKUP("OC_ID_LOOKUP", "IdLookup", "id", "/{id}"),
    OC_POS_RANGE_LOOKUP("OC_POS_RANGE_LOOKUP", "PositionRangeLookup", "range", "{?from,to}"),
    OC_ID_RANGE_LOOKUP("OC_ID_RANGE_LOOKUP", "IdRangeLookup", "aa", "aa"),
    OC_SUBSCRIPTION("OC_SUBSCRIPTION", "Subscription", "subscribe", "/subscribe{?clientURL}"),
    OC_ALL_LOOKUP("OC_ALL_LOOKUP", "AllLookup", "all", ""),
    OC_FILTERED_COUNT("OC_FILTERED_COUNT", "FilteredCount", "?", "?"),
    OC_TOTAL_ITEM_COUNT("OC_TOTAL_ITEM_COUNT", "TotalItemCount", "?", "?"),
    OC_SUBSCRIPTION_REQUIRED("OC_SUBSCRIPTION_REQUIRED", "SubscriptionRequired", "?", "?"),
    OC_POS_LOOKUP("OC_POS_LOOKUP", "PositionLookup", "?", "?"),
    OC_ID_INSERT("OC_ID_INSERT", "IdInsert", "?", "?"),
    OC_ID_UPDATE("OC_ID_UPDATE", "IdUpdate", "?", "?"),
    OC_ID_DELETE("OC_ID_DELETE", "IdDelete", "?", "?"),
    OC_POS_INSERT("OC_POS_INSERT", "PosInsert", "?", "?"),
    OC_POS_UPDATE("OC_POS_UPDATE", "PosUpdate", "?", "?"),
    OC_POS_DELETE("OC_POS_DELETE", "PosDelete", "?", "?"),
    OC_DELETE_ALL("OC_DELETE_ALL", "DeleteAll", "?", "?");

    private String id;
    private String text;
    private String path;
    private String link;

    private Capability(String id, String text, String link, String path) {
        this.id = id;
        this.text = text;
        this.link = link;
        this.path = path;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
