package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsmContentResolverMethodReferenceRewriter extends AsmMethodReferenceRewriter {
    public AsmContentResolverMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }



    @Override
    public MethodReference rewrite(MethodReference value, ReferenceInstruction instruction) {
        return new AsmBroadcastRewrittenMethodReference(value, instruction);
    }

    public static boolean excludeMethod(String method) {
        return !(method.equals("query")
                || method.equals("getType")
                || method.equals("insert")
                || method.equals("bulkInsert")
                || method.equals("delete")
                || method.equals("update")
                || method.equals("openInputStream")
                || method.equals("openOutputStream")
                || method.equals("openFileDescriptor")
                || method.equals("registerContentObserver")
                || method.equals("acquireContentProviderClient")
                || method.equals("notifyChange")
                || method.equals("toCalledUri")
        );
    }



    public static boolean excludeProvider(String type) {
        return type.equals("Landroid/content/ContentResolver")||  type.equals("Lcom/qihoo360/replugin");
    }


    public static String replaceProvider(String name) {
        switch (name) {
            case "Landroid/content/ContentResolver;":
                return "Lcom/qihoo360/replugin/loader/p/PluginProviderClient;";

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
                return replaceProvider(super.getDefiningClass());
            }
            return super.getDefiningClass();
        }

        @Override
        public List<? extends CharSequence> getParameterTypes() {
            if (!excludeMethod(getName())) {
                List<CharSequence> param = new ArrayList<>();
                param.add(0, "Landroid/content/Context;");
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
