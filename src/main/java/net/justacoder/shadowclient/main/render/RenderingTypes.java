package net.justacoder.shadowclient.main.render;

import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public abstract class RenderingTypes { // this is kind of inspired by vanilla's RenderLayer, except that one caused multiple issues as well as the need for many accesswidener uses

    private static final RenderingType LINES = RenderingType.create(
            "lines",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.DEBUG_LINES,
            RenderingType.Settings.builder()
                    .program(ShaderProgramKeys.POSITION_COLOR)
                    .culling(RenderingType.CULLING_DISABLED)
                    .blendFunc(RenderingType.DEFAULT_BLEND_FUNCTION)
                    .depthTest(RenderingType.NORMAL_DEPTH_TEST)
                    .lineWidth(RenderingType.LINE_WIDTH.apply(1))
                    .build()
    );

    private static final RenderingType LINES_NO_DEPTH_TEST = RenderingType.create(
            "lines_no_depth_test",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.DEBUG_LINES,
            RenderingType.Settings.builder()
                    .program(ShaderProgramKeys.POSITION_COLOR)
                    .culling(RenderingType.CULLING_DISABLED)
                    .blendFunc(RenderingType.DEFAULT_BLEND_FUNCTION)
                    .depthTest(RenderingType.NO_DEPTH_TEST)
                    .lineWidth(RenderingType.LINE_WIDTH.apply(1))
                    .build()
    );

    private static final RenderingType LINES_STRIP = RenderingType.create(
            "lines_strip",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            RenderingType.Settings.builder()
                    .program(ShaderProgramKeys.POSITION_COLOR)
                    .culling(RenderingType.CULLING_DISABLED)
                    .blendFunc(RenderingType.DEFAULT_BLEND_FUNCTION)
                    .depthTest(RenderingType.NORMAL_DEPTH_TEST)
                    .lineWidth(RenderingType.LINE_WIDTH.apply(1))
                    .build()
    );

    private static final RenderingType LINES_STRIP_NO_DEPTH_TEST = RenderingType.create(
            "lines_strip_no_depth_test",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            RenderingType.Settings.builder()
                    .program(ShaderProgramKeys.POSITION_COLOR)
                    .culling(RenderingType.CULLING_DISABLED)
                    .blendFunc(RenderingType.DEFAULT_BLEND_FUNCTION)
                    .depthTest(RenderingType.NO_DEPTH_TEST)
                    .lineWidth(RenderingType.LINE_WIDTH.apply(1))
                    .build()
    );

    private static final Function<Identifier, RenderingType> TEXT = id -> RenderingType.create(
            "text_" + id.toString(),
            VertexFormats.POSITION_TEXTURE_COLOR,
            VertexFormat.DrawMode.QUADS,
            RenderingType.Settings.builder()
                    .program(ShaderProgramKeys.POSITION_TEX_COLOR)
                    .texture(new RenderingType.Texture(id, true))
                    .blendFunc(RenderingType.TRANSPARENCY_BLEND_FUNCTION)
                    .build()
    );

    private static final Function<Identifier, RenderingType> TEXT_NO_DEPTH_TEST = id -> RenderingType.create(
            "text_no_depth_test_" + id.toString(),
            VertexFormats.POSITION_TEXTURE_COLOR,
            VertexFormat.DrawMode.QUADS,
            RenderingType.Settings.builder()
                    .program(ShaderProgramKeys.POSITION_TEX_COLOR)
                    .texture(new RenderingType.Texture(id, true))
                    .blendFunc(RenderingType.TRANSPARENCY_BLEND_FUNCTION)
                    .depthTest(RenderingType.NO_DEPTH_TEST)
                    .build()
    );

    public static RenderingType getLines(boolean depthTest) {
        return depthTest ? LINES : LINES_NO_DEPTH_TEST;
    }

    public static RenderingType getLinesStrip(boolean depthTest) {
        return depthTest ? LINES_STRIP : LINES_STRIP_NO_DEPTH_TEST;
    }

    public static RenderingType getText(Identifier textureId, boolean depthTest) {
        return (depthTest ? TEXT : TEXT_NO_DEPTH_TEST).apply(textureId);
    }
}
