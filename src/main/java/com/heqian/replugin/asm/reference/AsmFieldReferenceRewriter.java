package com.heqian.replugin.asm.reference;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.FieldReferenceRewriter;
import org.jf.dexlib2.rewriter.MethodReferenceRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmFieldReferenceRewriter extends FieldReferenceRewriter {
    public AsmFieldReferenceRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    public Reference rewrite(FieldReference reference, ReferenceInstruction instruction) {
        return rewrite(reference);
    }

    protected class AsmRewrittenFieldReference extends RewrittenFieldReference implements Instruction {
        private final ReferenceInstruction instruction;

        public AsmRewrittenFieldReference(FieldReference methodReference, ReferenceInstruction instruction) {
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
