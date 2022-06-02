package com.heqian.replugin.asm;

import org.jf.dexlib2.dexbacked.DexBackedMethodImplementation;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.rewriter.MethodImplementationRewriter;
import org.jf.dexlib2.rewriter.Rewriter;
import org.jf.dexlib2.rewriter.RewriterUtils;
import org.jf.dexlib2.rewriter.Rewriters;

import static com.heqian.replugin.asm.Main.rewriterModule;

public class AsmMethodImplementationRewriter extends MethodImplementationRewriter {
    public AsmMethodImplementationRewriter(Rewriters rewriters) {
        super(rewriters);
    }


    @Override
    public MethodImplementation rewrite(MethodImplementation methodImplementation) {
        if (methodImplementation instanceof DexBackedMethodImplementation) {
            var type = ((DexBackedMethodImplementation) methodImplementation).method.classDef.getType();
            if (!(type.startsWith("Landroid")
                    || type.startsWith("Ljava")
                    || type.startsWith("Lkotlin/")
                    || type.startsWith("L$")
                    || type.startsWith("Lcom/qihoo360/replugin/")
                    || type.startsWith("Lcom/android/")
            )) {
                return new AsmRewrittenMethodImplementation(methodImplementation);
            }
        }
        return super.rewrite(methodImplementation);
    }

    protected class AsmRewrittenMethodImplementation extends RewrittenMethodImplementation {
        public AsmRewrittenMethodImplementation(MethodImplementation methodImplementation) {
            super(methodImplementation);
        }


        @Override
        public Iterable<? extends Instruction> getInstructions() {
            return RewriterUtils.rewriteIterable(rewriterModule.getAsmInstructionRewriter(rewriters), methodImplementation.getInstructions());
        }
    }
}
