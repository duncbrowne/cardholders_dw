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

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
        {
            return true;
        }
        else if(obj != null && this.getClass() == obj.getClass())
        {
            Range range = (Range)obj;
            if (this.isComplete != range.isComplete)
                return false;
            if (this.page != range.page)
                return false;
            if (!this.from.equals(range.from))
                return false;
            if (!this.to.equals(range.to))
                return false;
            if (!this.type.equals(range.type))
                return false;

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = this.from.hashCode();
        result = 31 * result + (this.isComplete?1:0);
        result = 31 * result + this.page;
        result = 31 * result + this.to.hashCode();
        result = 31 * result + this.type.hashCode();
        return result;
    }
}
