package com.heqian.replugin.asm;

import com.heqian.replugin.asm.reference.AsmActivityMethodReferenceRewriter;
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
            if (AsmActivityMethodReferenceRewriter.excludeActivity(type)) {
                return super.getSuperclass();
            }
            return AsmActivityMethodReferenceRewriter.replaceActivity(super.getSuperclass());
        }

        @Override
        public Iterable<? extends Method> getDirectMethods() {
            if (!AsmMethodRewriter.excludeBroadcast(super.getType())) {
                return RewriterUtils.rewriteIterable(new AsmMethodRewriter(rewriters), classDef.getDirectMethods());
            }
            return super.getDirectMethods();
        }

        @Override
        public Iterable<? extends Method> getVirtualMethods() {
            if (!AsmMethodRewriter.excludeBroadcast(super.getType())) {
                return RewriterUtils.rewriteIterable(new AsmMethodRewriter(rewriters), classDef.getVirtualMethods());
            }
            return super.getVirtualMethods();
        }


        @Override
        public Iterable<? extends Field> getStaticFields() {
            if (!AsmFieldRewriter.excludeBroadcast(super.getType())) {
                return RewriterUtils.rewriteIterable(new AsmFieldRewriter(rewriters), classDef.getStaticFields());
            }
            return super.getStaticFields();
        }

        @Override
        public Iterable<? extends Field> getInstanceFields() {
            if (!AsmFieldRewriter.excludeBroadcast(super.getType())) {
                return RewriterUtils.rewriteIterable(new AsmFieldRewriter(rewriters), classDef.getInstanceFields());
            }
            return super.getInstanceFields();
        }
    }
}
