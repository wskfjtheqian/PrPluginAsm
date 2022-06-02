package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.MethodReferenceRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.HashMap;
import java.util.Map;

import static com.heqian.replugin.asm.Main.className;

public class AsmMethodReferenceRewriter extends MethodReferenceRewriter {
    public AsmMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    protected Map<MethodReference, AsmRewrittenMethodReference> referenceMap = new HashMap<>();

    @Override
    public MethodReference rewrite(MethodReference value) {
        return new AsmRewrittenMethodReference(value);
    }

    protected class AsmRewrittenMethodReference extends RewrittenMethodReference {
        public AsmRewrittenMethodReference(MethodReference methodReference) {
            super(methodReference);
        }

        @Override
        public String getDefiningClass() {
            return LoaderInjector.activity(super.getDefiningClass());
        }

    }
}
