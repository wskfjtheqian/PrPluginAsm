package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.Rewriter;
import org.jf.dexlib2.rewriter.RewriterModule;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmRewriterModule extends RewriterModule {
    @Override
    public Rewriter<ClassDef> getClassDefRewriter(Rewriters rewriters) {
        return new AsmClassDefRewriter(rewriters);
    }

    @Override
    public Rewriter<MethodImplementation> getMethodImplementationRewriter(Rewriters rewriters) {
        return new AsmMethodImplementationRewriter(rewriters);
    }
}


