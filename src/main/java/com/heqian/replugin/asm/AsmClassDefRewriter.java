package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.rewriter.ClassDefRewriter;
import org.jf.dexlib2.rewriter.RewriterUtils;
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
            if (AsmInstructionRewriter.excludeActivity(type)) {
                return super.getSuperclass();
            }
            return AsmInstructionRewriter.replaceActivity(super.getSuperclass());
        }

        @Override
        public Iterable<? extends Method> getDirectMethods() {
                return RewriterUtils.rewriteIterable(new AsmMethodRewriter(rewriters), classDef.getDirectMethods());
        }

        @Override
        public Iterable<? extends Method> getVirtualMethods() {
                return RewriterUtils.rewriteIterable(new AsmMethodRewriter(rewriters), classDef.getVirtualMethods());
        }


        @Override
        public Iterable<? extends Field> getStaticFields() {
                return RewriterUtils.rewriteIterable(new AsmFieldRewriter(rewriters), classDef.getStaticFields());
        }

        @Override
        public Iterable<? extends Field> getInstanceFields() {
            return RewriterUtils.rewriteIterable(new AsmFieldRewriter(rewriters), classDef.getInstanceFields());
        }
    }
}
