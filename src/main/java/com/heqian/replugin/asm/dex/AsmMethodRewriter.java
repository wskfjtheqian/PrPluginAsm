package com.heqian.replugin.asm.dex;

import org.jf.dexlib2.dexbacked.DexBackedMethod;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.rewriter.MethodRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.ArrayList;
import java.util.List;

public class AsmMethodRewriter extends MethodRewriter {
    public AsmMethodRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public Method rewrite(Method value) {
        return new AsmRewrittenMethod(value);
    }


    protected class AsmRewrittenMethod extends RewrittenMethod {
        private final String whereClass;
        private final String methodName;

        public AsmRewrittenMethod(Method method) {
            super(method);
            this.whereClass = ((DexBackedMethod) method).classDef.getType();
            this.methodName = method.getName();
        }

        @Override
        public List<? extends CharSequence> getParameterTypes() {
            List<CharSequence> parameter = new ArrayList<>();
            for (CharSequence item : super.getParameterTypes()) {
                String param = item.toString();
                if (!AsmInstructionRewriter.excludeBroadcast(whereClass)) {
                    param = AsmInstructionRewriter.replaceBroadcast(param);
                }
//                if (!AsmInstructionRewriter.excludeActivity(whereClass)) {
//                    param = AsmInstructionRewriter.replaceActivity(param);
//                }
                parameter.add(param);
            }
            return parameter;
        }

        @Override
        public String getReturnType() {
            String returnType = super.getReturnType();
            if (!AsmInstructionRewriter.excludeBroadcast(whereClass)) {
                returnType = AsmInstructionRewriter.replaceBroadcast(returnType);
            }
//            if (!AsmInstructionRewriter.excludeActivity(whereClass)) {
//                returnType = AsmInstructionRewriter.replaceActivity(returnType);
//            }
            return returnType;
        }
    }

}
