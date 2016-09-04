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

    public List<Capability> getValue() {
        return this.listCapabilities;
    }

    public int size() {
        return this.listCapabilities.size();
    }

    public Optional<Capability> getCapability(Capability capability) {
        Iterator iter = this.listCapabilities.iterator();

        Capability cap;
        do {
            if(!iter.hasNext()) {
                return Optional.empty();
            }

            cap = (Capability)iter.next();
        } while(cap.getId() != capability.getId());

        return Optional.of(cap);
    }

    public void addCapability(Capability capability) {
        this.listCapabilities.add(capability);
    }
}
