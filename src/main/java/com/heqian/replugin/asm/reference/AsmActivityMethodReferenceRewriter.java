package com.heqian.replugin.asm.reference;

import com.heqian.replugin.asm.LoaderInjector;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.MethodReferenceRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.HashMap;
import java.util.Map;

public class AsmActivityMethodReferenceRewriter extends AsmMethodReferenceRewriter {
    public AsmActivityMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    protected Map<MethodReference, AsmRewrittenMethodReference> referenceMap = new HashMap<>();

    @Override
    public MethodReference rewrite(MethodReference value, ReferenceInstruction instruction) {
        return new AsmActivityRewrittenMethodReference(value, instruction);
    }

    protected class AsmActivityRewrittenMethodReference extends AsmRewrittenMethodReference {
        public AsmActivityRewrittenMethodReference(MethodReference methodReference, ReferenceInstruction instruction) {
            super(methodReference, instruction);
        }

        @Override
        public String getDefiningClass() {
            return LoaderInjector.replaceActivity(super.getDefiningClass());
        }

    }

}
