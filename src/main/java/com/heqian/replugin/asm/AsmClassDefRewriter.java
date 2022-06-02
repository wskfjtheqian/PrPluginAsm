package com.heqian.replugin.asm;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.rewriter.ClassDefRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

public class AsmClassDefRewriter extends ClassDefRewriter {
    public AsmClassDefRewriter(Rewriters rewriters) {
        super(rewriters);
    }

    @Override
    public ClassDef rewrite(ClassDef classDef) {
        return new AsmRewrittenClassDef(classDef);
    }

    protected class AsmRewrittenClassDef extends RewrittenClassDef {
        public AsmRewrittenClassDef(ClassDef classdef) {
            super(classdef);
        }

        @Override
        public String getSuperclass() {
            var superclass = super.getSuperclass();
            var type = getType();
            if (type.startsWith("Landroid")) {
                return superclass;
            } else if (type.startsWith("Lcom/qihoo360/replugin/")) {
                return superclass;
            }

            if ("Landroidx/appcompat/app/AppCompatActivity;".equals(superclass)) {
                System.out.println(type);
                return "Lcom/qihoo360/replugin/loader/a/PluginAppCompatXActivity;";
            }

            return superclass;
        }
    }
}
