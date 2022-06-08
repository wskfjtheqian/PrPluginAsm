package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.MethodReferenceRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.List;

public class AsmMethodReferenceRewriter extends MethodReferenceRewriter {
    public AsmMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    public MethodReference rewrite(MethodReference reference, String definingClass, String name, List<? extends CharSequence> parameterTypes, String returnType) {
        return new AsmRewrittenMethodReference(reference, definingClass, name, parameterTypes, returnType);
    }

    protected class AsmRewrittenMethodReference extends RewrittenMethodReference {
        private final String definingClass;

        private final String name;

        private final List<? extends CharSequence> parameterTypes;

        private final String returnType;

        public AsmRewrittenMethodReference(MethodReference methodReference, String definingClass, String name, List<? extends CharSequence> parameterTypes, String returnType) {
            super(methodReference);
            this.definingClass = definingClass;
            this.name = name;
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
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
        public List<? extends CharSequence> getParameterTypes() {
            return parameterTypes;
        }

        @Override
        public String getReturnType() {
            return returnType;
        }
    }
}
