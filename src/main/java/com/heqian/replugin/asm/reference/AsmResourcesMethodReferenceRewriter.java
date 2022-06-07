package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsmResourcesMethodReferenceRewriter extends AsmMethodReferenceRewriter {
    public AsmResourcesMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    protected Map<MethodReference, AsmRewrittenMethodReference> referenceMap = new HashMap<>();

    @Override
    public MethodReference rewrite(MethodReference value, ReferenceInstruction instruction) {
        return new AsmBroadcastRewrittenMethodReference(value, instruction);
    }

    public static boolean excludeMethod(String method) {
        return !(method.equals("getIdentifier")
        );
    }

    public static boolean excludeResource(String type) {
        return type.startsWith("Lcom/qihoo360/replugin");
    }


    public static String replaceResource(String name) {
        switch (name) {
            case "Landroid/content/res/Resources;":
                return "Lcom/qihoo360/replugin/loader/r/PluginResources;";
        }
        return name;
    }

    protected class AsmBroadcastRewrittenMethodReference extends AsmRewrittenMethodReference {

        public AsmBroadcastRewrittenMethodReference(MethodReference methodReference, ReferenceInstruction instruction) {
            super(methodReference, instruction);
        }

        @Override
        public String getDefiningClass() {
            if (!excludeMethod(getName())) {
                return replaceResource(super.getDefiningClass());
            }
            return super.getDefiningClass();
        }

        @Override
        public List<? extends CharSequence> getParameterTypes() {
            if (!excludeMethod(getName())) {
                List<CharSequence> param = new ArrayList<>();
                param.add(0, "Landroid/content/res/Resources;");
                for (CharSequence item : super.getParameterTypes()) {
                    param.add(item);
                }
                return param;
            }
            return super.getParameterTypes();
        }

        @Override
        public Opcode getOpcode() {
            Opcode code = super.getOpcode();
            if (!excludeMethod(getName())) {
                if (Opcode.INVOKE_VIRTUAL == code) {
                    return Opcode.INVOKE_STATIC;
                } else if (Opcode.INVOKE_VIRTUAL_RANGE == code) {
                    return Opcode.INVOKE_STATIC_RANGE;
                }
            }
            return code;
        }
    }

}
