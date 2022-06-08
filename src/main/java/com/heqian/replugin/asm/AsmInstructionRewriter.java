package com.heqian.replugin.asm;

import com.heqian.replugin.asm.reference.AsmFieldReferenceRewriter;
import com.heqian.replugin.asm.reference.AsmMethodReferenceRewriter;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.ReferenceType;
import org.jf.dexlib2.dexbacked.DexBackedMethodImplementation;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.rewriter.InstructionRewriter;
import org.jf.dexlib2.rewriter.Rewriters;

import java.util.ArrayList;
import java.util.List;

import static org.jf.dexlib2.Format.Format35c;
import static org.jf.dexlib2.Format.Format3rc;

public class AsmInstructionRewriter extends InstructionRewriter {
    private final AsmMethodReferenceRewriter methodReferenceRewriter;
    private final AsmFieldReferenceRewriter fieldReferenceRewriter;

    public AsmInstructionRewriter(Rewriters rewriters) {
        super(rewriters);
        methodReferenceRewriter = new AsmMethodReferenceRewriter(rewriters);
        fieldReferenceRewriter = new AsmFieldReferenceRewriter(rewriters);
    }

    public static boolean excludeBroadcast(String type) {
        return type.startsWith("Landroid/support/v4/content/LocalBroadcastManager")
                || type.startsWith("Landroidx/localbroadcastmanager/content/LocalBroadcastManager")
                || type.startsWith("Lcom/qihoo360/replugin");
    }


    public static String replaceBroadcast(String name) {
        switch (name) {
            case "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;":
            case "Landroid/support/v4/content/LocalBroadcastManager;":
                return "Lcom/qihoo360/replugin/loader/b/PluginLocalBroadcastManager;";

        }
        return name;
    }

    public static boolean methodByBroadcast(String method) {
        return !(method.equals("getInstance")
                || method.equals("registerReceiver")
                || method.equals("unregisterReceiver")
                || method.equals("sendBroadcast")
                || method.equals("sendBroadcastSync")
        );
    }

    public static boolean excludeActivity(String type) {
        return type.startsWith("Landroid")
                || type.startsWith("Ljava")
                || type.startsWith("Lkotlin/")
                || type.startsWith("L$")
                || type.startsWith("Lcom/qihoo360/replugin/")
                || type.startsWith("Lcom/android/");
    }

    public static String replaceActivity(String name) {
        switch (name) {
            case "Landroid/app/Activity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginActivity;";
            case "Landroid/app/TabActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginTabActivity;";
            case "Landroid/app/ListActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginListActivity;";
            case "Landroid/app/ActivityGroup;":
                return "Lcom/qihoo360/replugin/loader/a/PluginActivityGroup;";
            case "Landroid/support/v4/app/FragmentActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginFragmentActivity;";
            case "Landroid/support/v7/app/AppCompatActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginAppCompatActivity;";
            case "Landroidx/fragment/app/FragmentActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginFragmentXActivity;";
            case "Landroidx/appcompat/app/AppCompatActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginAppCompatXActivity;";
            case "Landroid/preference/PreferenceActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginPreferenceActivity;";
            case "Landroid/app/ExpandableListActivity;":
                return "Lcom/qihoo360/replugin/loader/a/PluginExpandableListActivity;";
        }
        return name;
    }

    public static boolean methodByProvider(String method) {
        return method.equals("query")
                || method.equals("getType")
                || method.equals("insert")
                || method.equals("bulkInsert")
                || method.equals("delete")
                || method.equals("update")
                || method.equals("openInputStream")
                || method.equals("openOutputStream")
                || method.equals("openFileDescriptor")
                || method.equals("registerContentObserver")
                || method.equals("acquireContentProviderClient")
                || method.equals("notifyChange")
                || method.equals("toCalledUri")
                ;
    }


    public static boolean excludeProvider(String type) {
        return type.startsWith("Landroid/content/ContentResolver") || type.startsWith("Lcom/qihoo360/replugin");
    }


    public static String replaceProvider(String name) {
        switch (name) {
            case "Landroid/content/ContentResolver;":
                return "Lcom/qihoo360/replugin/loader/p/PluginProviderClient;";

        }
        return name;
    }

    public static boolean providerClientByMethod(String method) {
        return method.equals("query")
                || method.equals("update")
                ;
    }


    public static boolean excludeProviderClient(String type) {
        return type.startsWith("Landroid/content/ContentProviderClient") || type.startsWith("Lcom/qihoo360/replugin");
    }

    public static String replaceProviderClient(String name) {
        switch (name) {
            case "Landroid/content/ContentProviderClient;":
                return "Lcom/qihoo360/loader2/mgr/PluginProviderClient2";

        }
        return name;
    }


    public Instruction rewrite(Instruction instruction, MethodImplementation method) {
        if (instruction instanceof ReferenceInstruction) {
            String whereClass = ((DexBackedMethodImplementation) method).method.classDef.getType();
            String whereMethod = ((DexBackedMethodImplementation) method).method.getName();
            if (ReferenceType.METHOD == ((ReferenceInstruction) instruction).getReferenceType()) {

                MethodReference reference = (MethodReference) ((ReferenceInstruction) instruction).getReference();
                String definingClass = reference.getDefiningClass();
                String methodName = reference.getName();
                List<String> parameter = new ArrayList<>();
                String returnType = reference.getReturnType();
                boolean isStatic = false;

                if ("Landroid/content/res/Resources;".equals(definingClass) && "getIdentifier".equals(methodName)) {
                    if (!whereClass.startsWith("Lcom/qihoo360/replugin")) {
                        definingClass = "Lcom/qihoo360/replugin/loader/r/PluginResources;";
                        parameter.add("Landroid/content/res/Resources;");
                        isStatic = true;
                    }
                } else if (!definingClass.equals(replaceBroadcast(definingClass))) {
                    if (!excludeBroadcast(whereClass)) {
                        definingClass = replaceBroadcast(definingClass);
                    }
                } else if (!definingClass.equals(replaceActivity(definingClass))) {
                    if (!excludeActivity(whereClass)) {
                        definingClass = replaceActivity(definingClass);
                    }
                } else if (!definingClass.equals(replaceProvider(definingClass)) && methodByProvider(methodName)) {
                    if (!excludeProvider(whereClass)) {
                        definingClass = replaceProvider(definingClass);
                        parameter.add(0, "Landroid/content/ContentResolver;");
                        isStatic = true;
                    }
                } else if (!definingClass.equals(replaceProviderClient(definingClass)) && providerClientByMethod(methodName)) {
                    if (!excludeProviderClient(whereClass)) {
                        definingClass = replaceProviderClient(definingClass);
                        parameter.add(0, "Landroid/content/ContentProviderClient;");
                        isStatic = true;
                    }
                }

                for (CharSequence item : reference.getParameterTypes()) {
                    String param = item.toString();
                    if (!excludeBroadcast(whereClass)) {
                        param = replaceBroadcast(param);
                    }
                    if (!excludeActivity(whereClass)) {
                        param = replaceActivity(param);
                    }

                    parameter.add(param);
                }

                if (!excludeBroadcast(whereClass)) {
                    returnType = replaceBroadcast(returnType);
                }
                if (!excludeActivity(whereClass)) {
                    returnType = replaceActivity(returnType);
                }

                if (Format35c == instruction.getOpcode().format) {
                    return new AsmRewrittenInstruction35c(
                            (Instruction35c) instruction, isStatic ? Opcode.INVOKE_STATIC : instruction.getOpcode(),
                            methodReferenceRewriter.rewrite(reference, definingClass, methodName, parameter, returnType)
                    );
                } else if (Format3rc == instruction.getOpcode().format) {
                    return new AsmRewrittenInstruction3rc(
                            (Instruction3rc) instruction, isStatic ? Opcode.INVOKE_STATIC_RANGE : instruction.getOpcode(),
                            methodReferenceRewriter.rewrite(reference, definingClass, methodName, parameter, returnType)
                    );
                }

            } else if (ReferenceType.FIELD == ((ReferenceInstruction) instruction).getReferenceType()) {
                FieldReference reference = (FieldReference) ((ReferenceInstruction) instruction).getReference();
                String definingClass = reference.getDefiningClass();
                String fieldName = reference.getName();
                String fieldType = reference.getType();

                if (!definingClass.equals(replaceBroadcast(definingClass))) {
                    if (!excludeBroadcast(whereClass)) {
                        definingClass = replaceBroadcast(definingClass);
                    }
                }
                if (!excludeBroadcast(whereClass)) {
                    fieldType = replaceBroadcast(fieldType);
                }
                if (!excludeActivity(whereClass)) {
                    fieldType = replaceActivity(fieldType);
                }

                if (Opcode.IGET_OBJECT == instruction.getOpcode() || Opcode.IPUT_OBJECT == instruction.getOpcode()) {
                    return new AsmRewrittenInstruction22c(
                            (Instruction22c) instruction,
                            fieldReferenceRewriter.rewrite(reference, definingClass, fieldName, fieldType)
                    );
                }
            }

        }
        return super.rewrite(instruction);
    }

    protected class AsmRewrittenInstruction35c extends RewrittenInstruction35c {

        private final Opcode opcode;

        private final MethodReference reference;

        public AsmRewrittenInstruction35c(Instruction35c instruction, Opcode opcode, MethodReference reference) {
            super(instruction);
            this.opcode = opcode;
            this.reference = reference;
        }

        @Override
        public MethodReference getReference() {
            return reference;
        }

        @Override
        public Opcode getOpcode() {
            return opcode;
        }

        @Override
        public int getCodeUnits() {
            return getOpcode().format.size / 2;
        }
    }

    protected class AsmRewrittenInstruction3rc extends RewrittenInstruction3rc {

        private final Opcode opcode;

        private final MethodReference reference;

        public AsmRewrittenInstruction3rc(Instruction3rc instruction, Opcode opcode, MethodReference reference) {
            super(instruction);
            this.opcode = opcode;
            this.reference = reference;
        }

        @Override
        public MethodReference getReference() {
            return reference;
        }

        @Override
        public Opcode getOpcode() {
            return opcode;
        }

        @Override
        public int getCodeUnits() {
            return getOpcode().format.size / 2;
        }
    }


    private class AsmRewrittenInstruction22c extends RewrittenInstruction22c {
        private final Reference reference;

        public AsmRewrittenInstruction22c(Instruction22c instruction, Reference reference) {
            super(instruction);
            this.reference = reference;
        }

        @Override
        public Reference getReference() {
            return reference;
        }
    }
}
