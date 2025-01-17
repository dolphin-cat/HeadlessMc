package me.earth.headlessmc.launcher.instrumentation.log4j;

import lombok.CustomLog;
import me.earth.headlessmc.launcher.instrumentation.AbstractClassTransformer;
import me.earth.headlessmc.launcher.instrumentation.Target;
import org.objectweb.asm.tree.*;

import java.util.stream.Collectors;

import static org.objectweb.asm.Opcodes.ARETURN;

@CustomLog
public class Log4jPatcher extends AbstractClassTransformer {
    public Log4jPatcher(String className) {
        super(className);
    }

    @Override
    public boolean matches(Target target) {
        return target.getPath().contains("log4j");
    }

    @Override
    protected void transform(ClassNode cn) {
        // TODO: check desc of method for ARETURN compatible type
        for (MethodNode mn : cn.methods.stream()
                                       .filter(m -> "lookup".equals(m.name))
                                       .collect(Collectors.toList())) {
            log.info("Patching lookup in " + cn.name);
            InsnList insns = new InsnList();
            insns.add(new LdcInsnNode("HeadlessMc prevented Log4j lookup!"));
            insns.add(new InsnNode(ARETURN));
            mn.instructions = insns;
        }
    }

}
