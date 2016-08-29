package com.accessone.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class Capabilities {
    ArrayList<Capability> listCapabilities = new ArrayList();

    public String getEnumType() {
        return "ENUMTYPE_NPBSInternalAPI_tObjectCaps";
    }

    public String getPbsType() {
        return "Flag";
    }

    public List<Capability> getValue() {
        return this.listCapabilities;
    }

    public int size() {
        return this.listCapabilities.size();
    }

    public Capabilities() {
    }

    public Optional<Capability> getCapability(Capability capability) {
        Iterator var2 = this.listCapabilities.iterator();

        Capability o;
        do {
            if(!var2.hasNext()) {
                return Optional.empty();
            }

            o = (Capability)var2.next();
        } while(o.getId() != capability.getId());

        return Optional.of(o);
    }

    public void addCapability(Capability capability) {
        this.listCapabilities.add(capability);
    }
}
