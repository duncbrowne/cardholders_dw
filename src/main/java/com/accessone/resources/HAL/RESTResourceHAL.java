package com.accessone.resources.HAL;

import com.accessone.resources.Capabilities;
import com.accessone.resources.Capability;
import com.accessone.resources.RESTResource;
import com.accessone.resources.URIHelper;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class RESTResourceHAL extends RESTResource
{
    protected Capabilities capabilities = new Capabilities();
    private String type = "Resource";
    public static final RepresentationFactory representationFactory;

    public RESTResourceHAL(String id, String name) {
        super(id, name);
    }

    public Representation getRepresentation() {
        return representationFactory.newRepresentation().withLink("self", this.getSelf()).
                withProperty("id", this.getId()).withProperty("name", this.getName()).withProperty("type", this.getType());
    }

    public String getSelf() {
        return this.generateURI();
    }

    public void withBean(Class<?> entity, Object cardholder, Representation representation) {
        HALRepresentationHelper.addPropertiesFromPOJO(entity, cardholder, representation);
    }

    public Capabilities getCapabilities() {
        return this.capabilities;
    }

    public void addCapability(Capability capability) {
        this.capabilities.addCapability(capability);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String generateURI() {
        return this.generateURI("");
    }

    public String generateURI(String strPath) {
        return URIHelper.generateURI(UriBuilder.fromResource(this.getClass()).toString(), strPath);
    }

    public String generateURI(String strResourcePath, String strPath) {
        return URIHelper.generateURI(strResourcePath, strPath);
    }

    static {
        representationFactory = (new StandardRepresentationFactory()).withFlag(RepresentationFactory.COALESCE_ARRAYS);
    }
}
