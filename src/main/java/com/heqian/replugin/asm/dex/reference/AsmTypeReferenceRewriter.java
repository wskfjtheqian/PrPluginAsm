package com.heqian.replugin.asm.dex.reference;

import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.dexlib2.rewriter.Rewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmTypeReferenceRewriter implements Rewriter<TypeReference> {
    private final Rewriters rewriters;

    public AsmTypeReferenceRewriter(Rewriters rewriters) {
        this.rewriters = rewriters;
    }

    public BaseTypeReference rewrite(TypeReference reference, String type) {
        return new AsmRewrittenTypeReference(reference, type);
    }

    @Override
    public TypeReference rewrite(TypeReference reference) {
        return new AsmRewrittenTypeReference(reference, reference.getType());
    }

    protected class AsmRewrittenTypeReference extends BaseTypeReference {
        private final TypeReference reference;

        private final String type;

        public AsmRewrittenTypeReference(TypeReference reference, String type) {
            this.reference = reference;
            this.type = type;

        }

        @Override
        public String getType() {
            return type;
        }
    }
}
