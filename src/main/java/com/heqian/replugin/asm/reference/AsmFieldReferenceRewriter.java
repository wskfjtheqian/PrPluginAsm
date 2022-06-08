package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.FieldReferenceRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmFieldReferenceRewriter extends FieldReferenceRewriter {
    public AsmFieldReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    public Reference rewrite(FieldReference reference, String definingClass, String name, String type) {
        return new AsmRewrittenFieldReference(reference, definingClass, name, type);
    }

    protected class AsmRewrittenFieldReference extends RewrittenFieldReference {
        private final String definingClass;

        private final String name;

        private final String type;

        public AsmRewrittenFieldReference(FieldReference reference, String definingClass, String name, String type) {
            super(reference);
            this.definingClass = definingClass;
            this.name = name;
            this.type = type;
        }

        @Override
        public String getDefiningClass() {
            return definingClass;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getType() {
            return type;
        }
    }
}
