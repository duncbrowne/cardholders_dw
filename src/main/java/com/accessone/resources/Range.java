package com.accessone.resources;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class Range {
    private String from;
    private boolean isComplete;
    private int page;
    private String to;
    private String type;

    public Range() {
    }

    public Range(int from, int to) {
        if(to == -1 && from == -1) {
            this.to = "Last";
            this.from = "First";
            this.type = "all";
        } else {
            this.to = Integer.toString(to);
            this.from = Integer.toString(from);
            this.type = "range";
        }

        this.page = 1;
        this.isComplete = true;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            Range range = (Range)o;
            return this.isComplete != range.isComplete?false:(this.page != range.page?false:(!this.from.equals(range.from)?false:(!this.to.equals(range.to)?false:this.type.equals(range.type))));
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.from.hashCode();
        result = 31 * result + (this.isComplete?1:0);
        result = 31 * result + this.page;
        result = 31 * result + this.to.hashCode();
        result = 31 * result + this.type.hashCode();
        return result;
    }
}
