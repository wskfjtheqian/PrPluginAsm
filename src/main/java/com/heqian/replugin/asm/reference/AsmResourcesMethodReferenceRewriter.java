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
        return !(method.equalsIgnoreCase("getIdentifier")
        );
    }

    public static boolean excludeResources(String type) {
        return type.equalsIgnoreCase("Landroid/content/res/Resources;");
    }


    protected class AsmBroadcastRewrittenMethodReference extends AsmRewrittenMethodReference {

        public AsmBroadcastRewrittenMethodReference(MethodReference methodReference, ReferenceInstruction instruction) {
            super(methodReference, instruction);
        }

        @Override
        public String getDefiningClass() {
            if (!excludeMethod(getName())) {
                return super.getDefiningClass();
            }
            return super.getDefiningClass();
        }


    }

}
