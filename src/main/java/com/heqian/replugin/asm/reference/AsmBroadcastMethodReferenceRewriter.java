package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsmBroadcastMethodReferenceRewriter extends AsmMethodReferenceRewriter {
    public AsmBroadcastMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    protected Map<MethodReference, AsmRewrittenMethodReference> referenceMap = new HashMap<>();

    @Override
    public MethodReference rewrite(MethodReference value, ReferenceInstruction instruction) {
        return new AsmBroadcastRewrittenMethodReference(value, instruction);
    }

    public static boolean excludeMethod(String method) {
        return !(method.equals("getInstance")
                || method.equals("registerReceiver")
                || method.equals("unregisterReceiver")
                || method.equals("sendBroadcast")
                || method.equals("sendBroadcastSync")
        );
    }

    public static boolean excludeBroadcast(String type) {
        return type.equals("Landroid/support/v4/content/LocalBroadcastManager;")
                || type.equals("Landroidx/localbroadcastmanager/content/LocalBroadcastManager;")
                ||  type.startsWith("Lcom/qihoo360/replugin");
    }


    public static String replaceBroadcast(String name) {
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
                return replaceBroadcast(super.getDefiningClass());
            }
            return super.getDefiningClass();
        }

        @Override
        public List<? extends CharSequence> getParameterTypes() {
            if (!excludeMethod(getName()) && !"getInstance".equals(getName())) {
                List<CharSequence> param = new ArrayList<>();
                param.add(0, "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;");
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

        @Override
        public String getReturnType() {
            if (!excludeMethod(getName()) && "getInstance".equals(getName())) {
                return "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;";
            }
            return super.getReturnType();
        }
    }

}
