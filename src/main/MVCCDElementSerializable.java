package main;

import java.io.Serializable;

public abstract class MVCCDElementSerializable extends MVCCDElement implements Serializable {
    public MVCCDElementSerializable(MVCCDElement parent) {
        super(parent);
    }

    public MVCCDElementSerializable(MVCCDElement parent, String name) {
        super(parent, name);
    }
}
