package com.heqian.replugin.asm.dex;

import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.rewriter.MethodImplementationRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.Iterator;

public class AsmMethodImplementationRewriter extends MethodImplementationRewriter {
    public AsmMethodImplementationRewriter(Rewriters rewriters) {
        super(rewriters);
    }


    @Override
    public MethodImplementation rewrite(MethodImplementation methodImplementation) {
        return new AsmRewrittenMethodImplementation(methodImplementation);
    }

    protected class AsmRewrittenMethodImplementation extends RewrittenMethodImplementation {
        public AsmRewrittenMethodImplementation(MethodImplementation methodImplementation) {
            super(methodImplementation);
        }


        @Override
        public Iterable<? extends Instruction> getInstructions() {
            return rewriteIterable(new AsmInstructionRewriter(rewriters), methodImplementation.getInstructions());
        }


        public <T> Iterable<T> rewriteIterable(AsmInstructionRewriter rewriter,
                                               final Iterable<? extends T> iterable) {
            return new Iterable<T>() {
                @Override
                public Iterator<T> iterator() {
                    final Iterator<? extends T> iterator = iterable.iterator();
                    return new Iterator<T>() {
                        @Override
                        public boolean hasNext() {
                            return iterator.hasNext();
                        }

                        @Override
                        public T next() {
                            return rewriteNullable(rewriter, iterator.next());
                        }

                        @Override
                        public void remove() {
                            iterator.remove();
                        }
                    };
                }
            };
        }

        public <T> T rewriteNullable(AsmInstructionRewriter rewriter, T value) {
            return value == null ? null : (T) rewriter.rewrite((Instruction) value, methodImplementation);
        }
    }

}
