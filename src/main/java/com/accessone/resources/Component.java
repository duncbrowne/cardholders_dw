package com.accessone.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class Component {
    String id;
    String name;
    @JsonIgnore
    Component parent;
    @JsonIgnore
    String ui;
    List<Component> children;

    public Component() {
        this.id = null;
        this.name = null;
        this.parent = null;
        this.children = new ArrayList();
        this.ui = null;
    }

    public Component(String id, String name, Component parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList();
        this.ui = null;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getUi() {
        return Optional.ofNullable(this.ui);
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public void addChild(Component component) {
        this.children.add(component);
    }

    public Optional<Component> getParent() {
        return Optional.ofNullable(this.parent);
    }

    @JsonIgnore
    public int getChildCount() {
        return this.children.size();
    }

    public List<Component> getChildren() {
        return this.children;
    }

    public void setChildren(List<Component> children) {
        this.children = children;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            Component component = (Component)o;
            if(!this.id.equals(component.id)) {
                return false;
            } else if(!this.name.equals(component.name)) {
                return false;
            } else {
                label38: {
                    if(this.parent != null) {
                        if(this.parent.equals(component.parent)) {
                            break label38;
                        }
                    } else if(component.parent == null) {
                        break label38;
                    }

                    return false;
                }

                if(this.ui != null) {
                    if(!this.ui.equals(component.ui)) {
                        return false;
                    }
                } else if(component.ui != null) {
                    return false;
                }

                return this.children.equals(component.children);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + this.name.hashCode();
        result = 31 * result + (this.parent != null?this.parent.hashCode():0);
        result = 31 * result + (this.ui != null?this.ui.hashCode():0);
        result = 31 * result + this.children.hashCode();
        return result;
    }
}
