package net.justacoder.shadowclient.main.render;

import net.minecraft.client.render.BufferBuilder;
import java.util.HashMap;
import java.util.Map;

public class BufferBuilderProvider {

    private BufferBuilderProvider() {}

    private static BufferBuilderProvider instance = null;

    public static BufferBuilderProvider getInstance() {
        if (instance == null) {
            instance = new BufferBuilderProvider();
        }
        return instance;
    }

    private Map<RenderingType, BufferBuilder> bufferBuilders = new HashMap<>();

    public BufferBuilder getBufferBuilder(RenderingType type) {
        return bufferBuilders.computeIfAbsent(type, t -> type._getBuffer());
    }

    public void draw() {
        bufferBuilders.forEach(RenderingType::draw);
        bufferBuilders.clear();
    }

}
