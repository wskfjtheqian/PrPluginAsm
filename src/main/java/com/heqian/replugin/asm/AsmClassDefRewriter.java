package com.heqian.replugin.asm;

import com.heqian.replugin.asm.reference.AsmActivityMethodReferenceRewriter;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.rewriter.ClassDefRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmClassDefRewriter extends ClassDefRewriter {
    public AsmClassDefRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public ClassDef rewrite(ClassDef classDef) {
        return new AsmRewrittenClassDef(classDef);
    }

    protected class AsmRewrittenClassDef extends RewrittenClassDef {
        public AsmRewrittenClassDef(ClassDef classdef) {
            super(classdef);
        }

        @Override
        public String getSuperclass() {
            String type = super.getType();
            if (AsmActivityMethodReferenceRewriter.excludeActivity(type)) {
                return super.getSuperclass();
            }
            return AsmActivityMethodReferenceRewriter.replaceActivity(super.getSuperclass());
        }
    }
}
