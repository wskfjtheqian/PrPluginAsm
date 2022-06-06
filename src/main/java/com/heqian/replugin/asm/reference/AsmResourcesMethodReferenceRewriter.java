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

    public static boolean excludeResources(String type) {
        return type.equals("android.content.res.Resources")
                || type.equals("Landroidx/localbroadcastmanager/content/LocalBroadcastManager");
    }


    public static String replaceResources(String name) {
        switch (name) {
            case "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;":
                return "Lcom/qihoo360/replugin/loader/b/PluginLocalBroadcastManager;";

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
                return replaceResources(super.getDefiningClass());
            }
            return super.getDefiningClass();
        }

        @Override
        public List<? extends CharSequence> getParameterTypes() {
            if (!excludeMethod(getName()) && !"getInstance".equals(getName())) {
                List<CharSequence> param = new ArrayList<>();
                param.add(0, "Ljava/lang/Object;");
                for (CharSequence item : super.getParameterTypes()) {
                    param.add(item);
                }
                return param;
            }
            return super.getParameterTypes();
        }

        @Override
        public Opcode getOpcode() {
            if (!excludeMethod(getName())) {
                return Opcode.INVOKE_STATIC;
            }
            return super.getOpcode();
        }

        @Override
        public String getReturnType() {
            if (!excludeMethod(getName()) && "getInstance".equals(getName())) {
                return "Ljava/lang/Object;";
            }
            return super.getReturnType();
        }
    }

}
