package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.rewriter.Rewriter;
import org.jf.dexlib2.rewriter.RewriterModule;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmRewriterModule extends RewriterModule {

    private AsmInstructionRewriter instructionRewriter = null;
    private AsmMethodReferenceRewriter referenceRewriter = null;

    public Rewriter<Instruction> getAsmInstructionRewriter(Rewriters rewriters) {
        if (null == instructionRewriter) {
            instructionRewriter = new AsmInstructionRewriter(rewriters);
        }
        return instructionRewriter;
    }


    public Rewriter<MethodReference> getAsmMethodReferenceRewriter(Rewriters rewriters) {
        if (null == referenceRewriter) {
            referenceRewriter = new AsmMethodReferenceRewriter(rewriters);
        }
        return referenceRewriter;
    }

    @Override
    public Rewriter<ClassDef> getClassDefRewriter(Rewriters rewriters) {
        return new AsmClassDefRewriter(rewriters);
    }

    @Override
    public Rewriter<MethodImplementation> getMethodImplementationRewriter(Rewriters rewriters) {
        return new AsmMethodImplementationRewriter(rewriters);
    }

    @Override
    public Rewriter<Method> getMethodRewriter(Rewriters rewriters) {
        return new AsmMethodRewriter(rewriters);
    }
}


