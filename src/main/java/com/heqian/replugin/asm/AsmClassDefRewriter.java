package com.heqian.replugin.asm;

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
            if (type.startsWith("Landroid")
                    || type.startsWith("Ljava")
                    || type.startsWith("Lkotlin/")
                    || type.startsWith("L$")
                    || type.startsWith("Lcom/qihoo360/replugin/")
                    || type.startsWith("Lcom/android/")
            ) {
                return super.getSuperclass();
            }
            return LoaderInjector.activity(super.getSuperclass());
        }
    }
}
