package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmIputObjectFieldReferenceRewriter extends AsmFieldReferenceRewriter {
    public AsmIputObjectFieldReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public Reference rewrite(FieldReference reference, ReferenceInstruction instruction) {
        return new AsmIputObjectRewrittenFieldReference(reference, instruction);
    }

    public static boolean excludeBroadcast(String type) {
        return type.equals("Landroid/support/v4/content/LocalBroadcastManager;")
                || type.equals("Landroidx/localbroadcastmanager/content/LocalBroadcastManager;")
                || type.startsWith("Lcom/qihoo360/replugin");
    }

    public static String replaceBroadcast(String name) {
        switch (name) {
            case "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;":
                return "Lcom/qihoo360/replugin/loader/b/PluginLocalBroadcastManager;";

        }
        return name;
    }

    protected class AsmIputObjectRewrittenFieldReference extends AsmRewrittenFieldReference {
        public AsmIputObjectRewrittenFieldReference(FieldReference methodReference, ReferenceInstruction instruction) {
            super(methodReference, instruction);
        }

        @Override
        public String getType() {
            return replaceBroadcast(super.getType());
        }
    }
}
