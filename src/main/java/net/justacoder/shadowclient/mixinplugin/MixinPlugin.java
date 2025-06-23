package net.justacoder.shadowclient.mixinplugin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.SharedConstants;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        boolean apply = true;

        try {

            Map<String, Object> moddedMixinAnnotationValues = getAnnotationValues(mixinClassName, "Lnet/justacoder/shadowclient/main/annotations/ModdedMixin;");
            if (!moddedMixinAnnotationValues.isEmpty()) {
                apply = apply && FabricLoader.getInstance().isModLoaded((String) moddedMixinAnnotationValues.get("modId"));
            }

            Map<String, Object> versionDependentMixinAnnotationValues = getAnnotationValues(mixinClassName, "Lnet/justacoder/shadowclient/main/annotations/VersionDependentMixin;");
            if (!versionDependentMixinAnnotationValues.isEmpty()) {
                VersionPredicate predicate = VersionPredicate.parse((String) versionDependentMixinAnnotationValues.get("mcVersionPredicate"));
                SharedConstants.createGameVersion(); // in case it's not there
                apply = apply && predicate.test(Version.parse(SharedConstants.getGameVersion().getName()));
            }

        } catch (VersionParsingException e) {
            throw new RuntimeException("Failed to load version-conditional mixin: " + e);
        }

        return apply;

    }

    @Override public void onLoad(String s) {}
    @Override public String getRefMapperConfig() { return null; }
    @Override public void acceptTargets(Set<String> set, Set<String> set1) {}
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}
    @Override public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    public static Map<String, Object> getAnnotationValues(String mixinClassName, String annotationDesc) {
        try (InputStream classStream = MixinPlugin.class.getClassLoader().getResourceAsStream(mixinClassName.replace(".", "/") + ".class")) {

            if (classStream == null) {
                return Collections.emptyMap();
            }

            ClassReader cr = new ClassReader(classStream);
            Map<String, Object> annotationValues = new HashMap<>();

            cr.accept(new ClassVisitor(Opcodes.ASM9) {

                @Override
                public AnnotationVisitor visitAnnotation(String desc, boolean visible) {

                    if (desc.equals(annotationDesc)) {
                        return new AnnotationVisitor(Opcodes.ASM9) {

                            @Override
                            public void visit(String name, Object value) {
                                annotationValues.put(name, value);
                            }

                        };
                    }

                    return super.visitAnnotation(desc, visible);
                }

            }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            return annotationValues;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load mixin class to inspect annotations: " + e);
        }
    }


}
