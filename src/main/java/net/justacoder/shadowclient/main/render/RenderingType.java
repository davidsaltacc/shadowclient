package net.justacoder.shadowclient.main.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.mixin.BufferBuilderAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;

public class RenderingType {

    public static final Culling CULLING_DISABLED = Culling.create(RenderSystem::disableCull, RenderSystem::enableCull);
    public static final Culling CULLING_ENABLED = Culling.create(RenderSystem::enableCull, () -> {});
    public static final BlendFunction DEFAULT_BLEND_FUNCTION = BlendFunction.create(() -> {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }, RenderSystem::disableBlend);
    public static final BlendFunction TRANSPARENCY_BLEND_FUNCTION = BlendFunction.create(() -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    public static final DepthTest NO_DEPTH_TEST = DepthTest.create(RenderSystem::disableDepthTest, RenderSystem::enableDepthTest);
    public static final DepthTest NORMAL_DEPTH_TEST = DepthTest.create(() -> {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }, () -> {});
    public static final IntFunction<LineWidth> LINE_WIDTH = width -> LineWidth.create(() -> RenderSystem.lineWidth(width), () -> {});
    public static final LightMap LIGHT_MAP_ENABLED = LightMap.create(ShadowClientMain.mc.gameRenderer.getLightmapTextureManager()::enable, ShadowClientMain.mc.gameRenderer.getLightmapTextureManager()::disable);
    public static final LightMap LIGHT_MAP_DISABLED = LightMap.create(ShadowClientMain.mc.gameRenderer.getLightmapTextureManager()::disable, () -> {});

    private final String id;
    private final VertexFormat format;
    private final VertexFormat.DrawMode mode;
    private final List<Runnable> startActions;
    private final List<Runnable> endActions;
    private final BufferAllocator allocator;

    private static final List<RenderingType> allTypes = new ArrayList<>();

    private RenderingType(String id, VertexFormat format, VertexFormat.DrawMode mode, List<Runnable> startActions, List<Runnable> endActions) { // TODO priority to render before others
        this.id = id;
        this.format = format;
        this.mode = mode;
        this.startActions = startActions;
        this.endActions = endActions;
        this.allocator = new BufferAllocator(65536);
        allTypes.add(this);
    }

    public static RenderingType create(String id, VertexFormat format, VertexFormat.DrawMode mode, Settings settings) {
        return new RenderingType(id, format, mode, settings.getStartActions(), settings.getEndActions());
    }

    public static RenderingType create(String id, VertexFormat format, VertexFormat.DrawMode mode, Settings settings, Runnable customStartAction, Runnable customEndAction) {
        List<Runnable> startActions = settings.getStartActions();
        List<Runnable> endActions = settings.getEndActions();
        startActions.add(customStartAction);
        endActions.add(customEndAction);
        return new RenderingType(id, format, mode, startActions, endActions);
    }

    BufferBuilder _getBuffer() {
        return new BufferBuilder(allocator, mode, format);
    }

    void draw(BufferBuilder builder) {
        this.startActions.forEach(Runnable::run);
        BuiltBuffer buffer = builder.endNullable();
        if (((BufferBuilderAccessor) builder).getVertexCount() == 0) {
            ShadowClientMain.warn("BufferBuilder for Rendering Type " + id + " was empty!");
        } else if (buffer == null) {
            throw new RuntimeException("BufferBuilder for Rendering Type " + id + " did not finish properly even though not empty!");
        } else {
            BufferRenderer.drawWithGlobalProgram(buffer);
        }
        this.endActions.forEach(Runnable::run);
    }

    public static List<RenderingType> getAllTypes() {
        return allTypes;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() { // for the hashMap in the BufferBuilderProvider
        return Objects.hash(id, format, mode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        return this.hashCode() == obj.hashCode();
    }

    public abstract static class Setting {
        private final Runnable startAction;
        private final Runnable endAction;
        private Setting(Runnable startAction, Runnable endAction) {
            this.startAction = startAction;
            this.endAction = endAction;
        }
        public Runnable getStartAction() {
            return startAction;
        }
        public Runnable getEndAction() {
            return endAction;
        }
    }

    public static class Culling extends Setting {
        private Culling(Runnable startAction, Runnable endAction) {
            super(startAction, endAction);
        }
        private static Culling create(Runnable startAction, Runnable endAction) {
            return new Culling(startAction, endAction);
        }
    }

    public static class BlendFunction extends Setting {
        private BlendFunction(Runnable startAction, Runnable endAction) {
            super(startAction, endAction);
        }
        private static BlendFunction create(Runnable startAction, Runnable endAction) {
            return new BlendFunction(startAction, endAction);
        }
    }

    public static class DepthTest extends Setting {
        private DepthTest(Runnable startAction, Runnable endAction) {
            super(startAction, endAction);
        }
        private static DepthTest create(Runnable startAction, Runnable endAction) {
            return new DepthTest(startAction, endAction);
        }
    }

    public static class LineWidth extends Setting {
        private LineWidth(Runnable startAction, Runnable endAction) {
            super(startAction, endAction);
        }
        private static LineWidth create(Runnable startAction, Runnable endAction) {
            return new LineWidth(startAction, endAction);
        }
    }

    public static class LightMap extends Setting {
        private LightMap(Runnable startAction, Runnable endAction) {
            super(startAction, endAction);
        }
        private static LightMap create(Runnable startAction, Runnable endAction) {
            return new LightMap(startAction, endAction);
        }
    }

    public static class Texture extends Setting {
        public Texture(Identifier id, boolean bilinear) {
            super(() -> {
                TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
                AbstractTexture abstractTexture = textureManager.getTexture(id);
                abstractTexture.setFilter(bilinear, false);
                RenderSystem.setShaderTexture(0, abstractTexture.getGlId());
            }, () -> {});
        }
    }

    public static class Settings {

        private Settings() {
            startActions = new ArrayList<>();
            endActions = new ArrayList<>();
        }

        private final List<Runnable> startActions;
        private final List<Runnable> endActions;

        public List<Runnable> getStartActions() {
            return startActions;
        }

        public List<Runnable> getEndActions() {
            return endActions;
        }

        private void add(Setting setting) {
            startActions.add(setting.getStartAction());
            endActions.add(setting.getEndAction());
        }

        public static Builder builder() {
            return new Settings.Builder();
        }

        public static class Builder {

            private Settings settings = new Settings();
            private Builder() {}

            public Settings build() {
                return settings;
            }

            public Builder program(ShaderProgramKey key) {
                settings.add(new Setting(() -> RenderSystem.setShader(key), () -> {}){});
                return this;
            }

            public Builder culling(Culling cull) {
                settings.add(cull);
                return this;
            }

            public Builder blendFunc(BlendFunction function) {
                settings.add(function);
                return this;
            }

            public Builder depthTest(DepthTest depth) {
                settings.add(depth);
                return this;
            }

            public Builder lineWidth(LineWidth width) {
                settings.add(width);
                return this;
            }

            public Builder texture(Texture texture) {
                settings.add(texture);
                return this;
            }

            public Builder lightmap(LightMap lightmap) {
                settings.add(lightmap);
                return this;
            }

        }
    }

}
