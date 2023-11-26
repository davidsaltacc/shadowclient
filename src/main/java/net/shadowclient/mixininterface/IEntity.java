package net.shadowclient.mixininterface;

import java.util.concurrent.atomic.AtomicInteger;

public interface IEntity {

    void setOnGround(boolean onGround);
    AtomicInteger getCurrentId();
}
