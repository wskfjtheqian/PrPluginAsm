package com.heqian.replugin.asm;

import org.jf.dexlib2.base.reference.BaseMethodReference;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.dexbacked.DexBackedMethod;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.rewriter.MethodRewriter;
import org.jf.dexlib2.rewriter.Rewriter;
import org.jf.dexlib2.rewriter.RewriterUtils;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmMethodRewriter extends MethodRewriter {
    public AsmMethodRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public Method rewrite(Method value) {
        return new AsmRewrittenMethod(value);
    }

    protected class AsmRewrittenMethod extends RewrittenMethod {
        public AsmRewrittenMethod(Method method) {
            super(method);
        }

        public MethodImplementation getImplementation() {
            return RewriterUtils.rewriteNullable(rewriters.getMethodImplementationRewriter(),
                    method.getImplementation());
        }
    }

}
