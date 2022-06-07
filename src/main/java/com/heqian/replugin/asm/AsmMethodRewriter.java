package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.rewriter.MethodRewriter;
import org.jf.dexlib2.rewriter.RewriterUtils;
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


    public static boolean excludeBroadcast(String type) {
        return type.equals("Landroid/support/v4/content/LocalBroadcastManager;")
                || type.equals("Landroidx/localbroadcastmanager/content/LocalBroadcastManager;")
                || type.equals("Lcom/qihoo360/replugin");
    }


    public static String replaceBroadcast(String name) {
        switch (name.toString()) {
            case "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;":
                return "Lcom/qihoo360/replugin/loader/b/PluginLocalBroadcastManager;";

        }
        return name;
    }

    protected class AsmRewrittenMethod extends RewrittenMethod {
        public AsmRewrittenMethod(Method method) {
            super(method);
        }

        @Override
        public List<? extends CharSequence> getParameterTypes() {
            List<CharSequence> param = new ArrayList<>();
            for (CharSequence item : super.getParameterTypes()) {
                param.add(replaceBroadcast(item.toString()));
            }
            return param;
        }

        @Override
        public String getReturnType() {
            return replaceBroadcast(super.getReturnType());
        }
    }

}
