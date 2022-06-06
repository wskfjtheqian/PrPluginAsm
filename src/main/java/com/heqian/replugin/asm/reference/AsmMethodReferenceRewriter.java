package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.MethodReferenceRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmMethodReferenceRewriter extends MethodReferenceRewriter {
    public AsmMethodReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    public Reference rewrite(MethodReference reference, ReferenceInstruction instruction) {
        return rewrite(reference);
    }

    protected class AsmRewrittenMethodReference extends RewrittenMethodReference implements Instruction {
        private final ReferenceInstruction instruction;

        public AsmRewrittenMethodReference(MethodReference methodReference, ReferenceInstruction instruction) {
            super(methodReference);
            this.instruction = instruction;
        }

        @Override
        public Opcode getOpcode() {
            return instruction.getOpcode();
        }

        @Override
        public int getCodeUnits() {
            return instruction.getCodeUnits();
        }
    }
}
