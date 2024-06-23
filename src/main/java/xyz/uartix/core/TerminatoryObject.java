package xyz.uartix.core;

public class TerminatoryObject extends TerminativeSignal {
    private final Object object;

    public TerminatoryObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return this.object;
    }
}
