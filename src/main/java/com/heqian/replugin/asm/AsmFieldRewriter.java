package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.rewriter.FieldRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmFieldRewriter extends FieldRewriter {
    public AsmFieldRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public Field rewrite(Field field) {
        return new AsmRewrittenField(field);
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

    protected class AsmRewrittenField extends RewrittenField {
        public AsmRewrittenField(Field field) {
            super(field);
        }

        @Override
        public String getType() {
            return replaceBroadcast(super.getType());
        }
    }

}
