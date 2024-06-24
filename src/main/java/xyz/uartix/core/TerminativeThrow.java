package xyz.uartix.core;

public class TerminativeThrow extends TerminativeSignal {
    private final Object object;

    public TerminativeThrow(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return this.object;
    }
}
