package com.heqian.replugin.asm;

import com.heqian.replugin.asm.reference.*;
import com.heqian.replugin.asm.reference.AsmMethodReferenceRewriter;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.ReferenceType;
import org.jf.dexlib2.dexbacked.DexBackedMethodImplementation;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.InstructionRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmInstructionRewriter extends InstructionRewriter {
    public AsmInstructionRewriter(Rewriters rewriters) {
        super(rewriters);
    }


    public Instruction rewrite(Instruction instruction, MethodImplementation method) {
        if (instruction instanceof ReferenceInstruction) {
            switch (instruction.getOpcode().format) {
                case Format35c:  //"invoke-static",
                    return new AsmRewrittenInstruction35c((Instruction35c) instruction, method);
                case Format3rc:  //"invoke-static/range"
                    return new AsmRewrittenInstruction3rc((Instruction3rc) instruction, method);
            }
        }
        return super.rewrite(instruction);
    }

    protected class AsmRewrittenInstruction35c extends RewrittenInstruction35c {
        private final MethodImplementation method;

        private AsmMethodReferenceRewriter rewrite;

        public AsmRewrittenInstruction35c(Instruction35c instruction, MethodImplementation method) {
            super(instruction);
            this.method = method;
            if (ReferenceType.METHOD == instruction.getReferenceType()) {
                MethodReference reference = (MethodReference) instruction.getReference();
                String definingClass = reference.getDefiningClass();
                String type = ((DexBackedMethodImplementation) method).method.classDef.getType();

                if (!LoaderInjector.excludeActivity(type) && !definingClass.equals(LoaderInjector.replaceActivity(definingClass))) {
                    rewrite = new AsmActivityMethodReferenceRewriter(rewriters);
                }
                if (!AsmBroadcastMethodReferenceRewriter.excludeBroadcast(type) && !definingClass.equals(AsmBroadcastMethodReferenceRewriter.replaceBroadcast(definingClass))) {
                    rewrite = new AsmBroadcastMethodReferenceRewriter(rewriters);
                }
                if (!AsmContentResolverMethodReferenceRewriter.excludeProvider(type) && !definingClass.equals(AsmContentResolverMethodReferenceRewriter.replaceProvider(definingClass))) {
                    rewrite = new AsmContentResolverMethodReferenceRewriter(rewriters);
                }
                if (!AsmProviderClientMethodReferenceRewriter.excludeProvider(type) && !definingClass.equals(AsmProviderClientMethodReferenceRewriter.replaceProvider(definingClass))) {
                    rewrite = new AsmProviderClientMethodReferenceRewriter(rewriters);
                }
                if (!AsmResourcesMethodReferenceRewriter.excludeResources(type) && AsmResourcesMethodReferenceRewriter.excludeResources(definingClass)) {
                    rewrite = new AsmResourcesMethodReferenceRewriter(rewriters);
                }
            }
        }

        @Override
        public Reference getReference() {
            if (null != rewrite) {
                return rewrite.rewrite((MethodReference) instruction.getReference(), instruction);
            }
            return super.getReference();
        }

        @Override
        public Opcode getOpcode() {
            Reference reference = getReference();
            if (null != rewrite) {
                return ((Instruction) reference).getOpcode();
            }
            return super.getOpcode();
        }

        @Override
        public int getCodeUnits() {
            Reference reference = getReference();
            if (null != rewrite) {
                return ((Instruction) reference).getCodeUnits();
            }
            return super.getCodeUnits();
        }
    }

    protected class AsmRewrittenInstruction3rc extends RewrittenInstruction3rc {
        private final MethodImplementation method;

        private AsmMethodReferenceRewriter rewrite;

        public AsmRewrittenInstruction3rc(Instruction3rc instruction, MethodImplementation method) {
            super(instruction);
            this.method = method;
            if (ReferenceType.METHOD == instruction.getReferenceType()) {
                MethodReference reference = (MethodReference) instruction.getReference();
                String definingClass = reference.getDefiningClass();
                String type = ((DexBackedMethodImplementation) method).method.classDef.getType();

                if (!LoaderInjector.excludeActivity(type) && !definingClass.equals(LoaderInjector.replaceActivity(definingClass))) {
                    rewrite = new AsmActivityMethodReferenceRewriter(rewriters);
                }
                if (!AsmBroadcastMethodReferenceRewriter.excludeBroadcast(type) && !definingClass.equals(AsmBroadcastMethodReferenceRewriter.replaceBroadcast(definingClass))) {
                    rewrite = new AsmBroadcastMethodReferenceRewriter(rewriters);
                }
                if (!AsmContentResolverMethodReferenceRewriter.excludeProvider(type) && !definingClass.equals(AsmContentResolverMethodReferenceRewriter.replaceProvider(definingClass))) {
                    rewrite = new AsmContentResolverMethodReferenceRewriter(rewriters);
                }
                if (!AsmProviderClientMethodReferenceRewriter.excludeProvider(type) && !definingClass.equals(AsmProviderClientMethodReferenceRewriter.replaceProvider(definingClass))) {
                    rewrite = new AsmProviderClientMethodReferenceRewriter(rewriters);
                }
                if (!AsmResourcesMethodReferenceRewriter.excludeResources(definingClass)) {
                    rewrite = new AsmResourcesMethodReferenceRewriter(rewriters);
                }
            }
        }

        @Override
        public Reference getReference() {
            if (null != rewrite) {
                return rewrite.rewrite((MethodReference) instruction.getReference(), instruction);
            }
            return super.getReference();
        }

        @Override
        public Opcode getOpcode() {
            Reference reference = getReference();
            if (null != rewrite) {
                return ((Instruction) reference).getOpcode();
            }
            return super.getOpcode();
        }

        @Override
        public int getCodeUnits() {
            Reference reference = getReference();
            if (null != rewrite) {
                return ((Instruction) reference).getCodeUnits();
            }
            return super.getCodeUnits();
        }
    }

}
