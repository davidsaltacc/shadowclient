package net.justacoder.shadowclient.main.annotations;

import net.justacoder.shadowclient.main.event.Event;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventListener {

    Class<? extends Event>[] value();

}
