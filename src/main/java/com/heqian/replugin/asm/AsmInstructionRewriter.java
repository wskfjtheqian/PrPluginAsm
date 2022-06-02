package com.heqian.replugin.asm;

import org.jf.dexlib2.ReferenceType;
import org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.formats.*;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.dexlib2.rewriter.InstructionRewriter;
import org.jf.dexlib2.rewriter.RewriterUtils;
import org.jf.dexlib2.rewriter.Rewriters;

import static com.heqian.replugin.asm.Main.rewriterModule;

public class AsmInstructionRewriter extends InstructionRewriter {
    public AsmInstructionRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public Instruction rewrite(Instruction instruction) {
        if (instruction instanceof ReferenceInstruction) {
            switch (instruction.getOpcode().format) {
                case Format20bc:
                    return new AsmRewrittenInstruction20bc((Instruction20bc) instruction);
                case Format21c:
                    return new AsmRewrittenInstruction21c((Instruction21c) instruction);
                case Format22c:
                    return new AsmRewrittenInstruction22c((Instruction22c) instruction);
                case Format31c:
                    return new AsmRewrittenInstruction31c((Instruction31c) instruction);
                case Format35c:
                    return new AsmRewrittenInstruction35c((Instruction35c) instruction);
                case Format3rc:
                    return new AsmRewrittenInstruction3rc((Instruction3rc) instruction);
            }
        }
        return instruction;
    }

    protected class AsmRewrittenInstruction20bc extends RewrittenInstruction20bc
            implements Instruction20bc {
        public AsmRewrittenInstruction20bc(Instruction20bc instruction) {
            super(instruction);
        }

        @Override
        public int getVerificationError() {
            return instruction.getVerificationError();
        }

        @Override
        public Reference getReference() {
            switch (instruction.getReferenceType()) {
                case ReferenceType.METHOD:
                    return rewriterModule.getAsmMethodReferenceRewriter(rewriters).rewrite((MethodReference) instruction.getReference());
                default:
                    return super.getReference();
            }
        }
    }

    protected class AsmRewrittenInstruction21c extends RewrittenInstruction21c {
        public AsmRewrittenInstruction21c(Instruction21c instruction) {
            super(instruction);
        }

        public int getRegisterA() {
            return instruction.getRegisterA();
        }

        @Override
        public Reference getReference() {
            switch (instruction.getReferenceType()) {
                case ReferenceType.METHOD:
                    return rewriterModule.getAsmMethodReferenceRewriter(rewriters).rewrite((MethodReference) instruction.getReference());
                default:
                    return super.getReference();
            }
        }
    }

    protected class AsmRewrittenInstruction22c extends RewrittenInstruction22c {
        public AsmRewrittenInstruction22c(Instruction22c instruction) {
            super(instruction);
        }

        public int getRegisterA() {
            return instruction.getRegisterA();
        }

        public int getRegisterB() {
            return instruction.getRegisterB();
        }

        @Override
        public Reference getReference() {
            switch (instruction.getReferenceType()) {
                case ReferenceType.METHOD:
                    return rewriterModule.getAsmMethodReferenceRewriter(rewriters).rewrite((MethodReference) instruction.getReference());
                default:
                    return super.getReference();
            }
        }
    }

    protected class AsmRewrittenInstruction31c extends RewrittenInstruction31c {
        public AsmRewrittenInstruction31c(Instruction31c instruction) {
            super(instruction);
        }

        public int getRegisterA() {
            return instruction.getRegisterA();
        }

        @Override
        public Reference getReference() {
            switch (instruction.getReferenceType()) {
                case ReferenceType.METHOD:
                    return rewriterModule.getAsmMethodReferenceRewriter(rewriters).rewrite((MethodReference) instruction.getReference());
                default:
                    return super.getReference();
            }
        }
    }

    protected class AsmRewrittenInstruction35c extends RewrittenInstruction35c {
        public AsmRewrittenInstruction35c(Instruction35c instruction) {
            super(instruction);
        }

        public int getRegisterC() {
            return instruction.getRegisterC();
        }

        public int getRegisterE() {
            return instruction.getRegisterE();
        }

        public int getRegisterG() {
            return instruction.getRegisterG();
        }

        public int getRegisterCount() {
            return instruction.getRegisterCount();
        }

        public int getRegisterD() {
            return instruction.getRegisterD();
        }

        public int getRegisterF() {
            return instruction.getRegisterF();
        }

        @Override
        public Reference getReference() {
            switch (instruction.getReferenceType()) {
                case ReferenceType.METHOD:
                    return rewriterModule.getAsmMethodReferenceRewriter(rewriters).rewrite((MethodReference) instruction.getReference());
                default:
                    return super.getReference();
            }
        }
    }

    protected class AsmRewrittenInstruction3rc extends RewrittenInstruction3rc {
        public AsmRewrittenInstruction3rc(Instruction3rc instruction) {
            super(instruction);
        }

        public int getStartRegister() {
            return instruction.getStartRegister();
        }

        public int getRegisterCount() {
            return instruction.getRegisterCount();
        }

        @Override
        public Reference getReference() {
            switch (instruction.getReferenceType()) {
                case ReferenceType.METHOD:
                    return rewriterModule.getAsmMethodReferenceRewriter(rewriters).rewrite((MethodReference) instruction.getReference());
                default:
                    return super.getReference();
            }
        }
    }
}
