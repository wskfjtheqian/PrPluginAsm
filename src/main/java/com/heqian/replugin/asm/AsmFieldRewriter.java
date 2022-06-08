package com.heqian.replugin.asm;

import org.jf.dexlib2.dexbacked.DexBackedField;
import org.jf.dexlib2.dexbacked.DexBackedMethod;
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


    protected class AsmRewrittenField extends RewrittenField {
        private final String whereClass;
        private final String fieldName;

        public AsmRewrittenField(Field field) {
            super(field);
            this.whereClass = ((DexBackedField) field).classDef.getType();
            this.fieldName = field.getName();
        }


        @Override
        public String getType() {
            String type = super.getType();
            if (!AsmInstructionRewriter.excludeBroadcast(whereClass)) {
                type = AsmInstructionRewriter.replaceBroadcast(type);
            }
//            if (!AsmInstructionRewriter.excludeActivity(whereClass)) {
//                type = AsmInstructionRewriter.replaceActivity(type);
//            }
            return type;
        }
    }

}
