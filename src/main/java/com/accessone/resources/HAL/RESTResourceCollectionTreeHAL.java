package com.accessone.resources.HAL;

import com.accessone.resources.Capability;
import com.accessone.resources.Component;
import com.accessone.resources.Range;
import com.theoryinpractise.halbuilder.api.Representation;

import java.util.Iterator;
import java.util.List;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class RESTResourceCollectionTreeHAL extends RESTResourceHAL {
    public RESTResourceCollectionTreeHAL(String id, String name) {
        super(id, name);
        this.setType("Tree");
    }

    public Representation getRepresentation() {
        Representation representation = super.getRepresentation();
        List objectCapabilities = this.capabilities.getValue();
        Iterator var3 = objectCapabilities.iterator();

        while(var3.hasNext()) {
            Capability o = (Capability)var3.next();
            representation.withLink(o.getLink(), this.generateURI(o.getPath()));
        }

        representation.withLink("root", this.generateURI("/items/root"));
        representation.withProperty("capabilities", this.capabilities);
        return representation;
    }

    public Representation getRepresentation(Component parent, List<Component> objects) {
        Representation childrenRepresentation = representationFactory.newRepresentation(this.generateURI("/children/" + parent.getId()));
        if(!objects.isEmpty()) {
            for(int range = 0; range < objects.size(); ++range) {
                Component child = (Component)objects.get(range);
                Representation childRepresenation = this.getRepresentation(child).withProperty("childPos", Integer.valueOf(range));
                if(child.getParent().isPresent()) {
                    childRepresenation.withLink("parent", this.generateURI("/items/" + parent.getId()));
                }

                if(child.getUi().isPresent()) {
                    childRepresenation.withProperty("uiId", child.getUi().get());
                }

                childrenRepresentation.withRepresentation("items", childRepresenation);
            }
        } else {
            Representation var7 = representationFactory.newRepresentation();
            childrenRepresentation.withRepresentation("items", var7);
        }

        Range var8 = new Range(-1, -1);
        childrenRepresentation.withProperty("range", var8);
        return childrenRepresentation;
    }

    public Representation getRepresentation(Component object) {
        Representation resourceRepresentation = representationFactory.newRepresentation().withProperty("childCount", Integer.valueOf(object.getChildCount())).withProperty("name", object.getName()).withProperty("id", object.getId()).withProperty("pbsType", "PBSComponent").withProperty("health", (Object)null).withLink("selft", this.generateURI("/items/" + object.getId())).withLink("children", this.generateURI("/children/" + object.getId())).withLink("tree", this.generateURI());
        if(object.getParent().isPresent()) {
            resourceRepresentation.withProperty("parentId", ((Component)object.getParent().get()).getId());
        }

        if(object.getUi().isPresent()) {
            resourceRepresentation.withLink("ui", this.generateURI("/hal/uis", "/items/" + (String)object.getUi().get()));
        }

        return resourceRepresentation;
    }
}
