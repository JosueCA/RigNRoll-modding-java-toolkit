/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization.nxs;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import rnrcore.CoreTime;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.nxs.AnnotatedSerializable;
import xmlserialization.nxs.DeserializationConstructor;
import xmlserialization.nxs.InvokeMethodStateLoader;
import xmlserialization.nxs.LoadFrom;
import xmlserialization.nxs.RemapSerializedArguments;
import xmlserialization.nxs.SaveTo;
import xmlserialization.nxs.SetFieldStateLoader;
import xmlserialization.nxs.StateRecordLoader;
import xmlutils.Node;
import xmlutils.NodeList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class SerializatorOfAnnotated {
    private static SerializatorOfAnnotated instance = new SerializatorOfAnnotated();
    private static final String CONSTRUCTOR_ARGUMENT_INDEX_ATTR = "constructor_argument_index";
    private final List<Pair<String, String>> nodeAttributesBuffer;
    private final Map<String, StateLoadDescription> classNamesResolveTable;
    private boolean wereErrorsDuringLastSerialization;

    public SerializatorOfAnnotated() {
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Integer.TYPE){

            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setInt(fieldHost, Integer.parseInt(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Double.TYPE){

            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setDouble(fieldHost, Double.parseDouble(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Float.TYPE){

            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setFloat(fieldHost, Float.parseFloat(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(Boolean.TYPE){

            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.setBoolean(fieldHost, Boolean.parseBoolean(data));
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(String.class){

            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.set(fieldHost, data);
            }
        });
        SetFieldStateLoader.addFieldSetter(new SetFieldStateLoader.FieldSetter(CoreTime.class){

            void set(Object fieldHost, Field field, String data) throws IllegalAccessException {
                field.set(fieldHost, new CoreTime(data));
            }
        });
        this.nodeAttributesBuffer = new ArrayList<Pair<String, String>>();
        this.classNamesResolveTable = new HashMap<String, StateLoadDescription>();
        this.wereErrorsDuringLastSerialization = false;
    }

    public static SerializatorOfAnnotated getInstance() {
        return instance;
    }

    public static SerializatorOfAnnotated resetInstance() {
        instance = new SerializatorOfAnnotated();
        return instance;
    }

    private static <T extends Annotation> void visitAllAnnotatedHierarchyFields(Class<T> desired, Class hierarchyLeaf, FieldVisitor<T> visitor) {
        assert (null != desired && null != hierarchyLeaf);
        ArrayList<Class> parents = new ArrayList<Class>();
        for (Class parent = hierarchyLeaf.getSuperclass(); null != parent; parent = parent.getSuperclass()) {
            parents.add(parent);
        }
        parents.add(hierarchyLeaf);
        for (Class classHierarchyElement : parents) {
            for (Field statePart : classHierarchyElement.getDeclaredFields()) {
                T stateAnnotation = statePart.getAnnotation(desired);
                if (null == stateAnnotation) continue;
                visitor.visit(statePart, stateAnnotation);
            }
        }
    }

    Map<String, StateLoadDescription> getStateLoadingDescriptions() {
        return Collections.unmodifiableMap(this.classNamesResolveTable);
    }

    public void register(String id, Class<? extends AnnotatedSerializable> clazz) {
        if (null != id && null != clazz && null == this.classNamesResolveTable.get(id)) {
            this.classNamesResolveTable.put(id, new StateLoadDescription(clazz));
        } else {
            Log.error("NXS-init: trying to register invalid class");
        }
    }

    private static String getTextContent(Node from) {
        return from.getNode().getTextContent();
    }

    private void loadStateFor(AnnotatedSerializable target, StateLoadDescription metaData, NodeList stateStore) {
        assert (null != target && null != metaData);
        if (null != stateStore) {
            Map<String, StateRecordLoader> stateLoaders = metaData.getStateLoaders();
            for (Node stateRecord : stateStore) {
                StateRecordLoader loader = stateLoaders.get(stateRecord.getName());
                if (null != loader) {
                    String content = SerializatorOfAnnotated.getTextContent(stateRecord);
                    loader.load(target, 0 < content.length() ? content : null);
                    continue;
                }
                Log.warning(String.format("NXS-load: no loader found for node '%s'", stateRecord.getName()));
            }
        }
    }

    public AnnotatedSerializable loadStateOrNull(Node from) {
        AnnotatedSerializable restored;
        block19: {
            restored = null;
            try {
                if (null == from) break block19;
                String classUid = from.getName();
                NodeList stateRecords = from.getChildren();
                assert (null != classUid && null != stateRecords);
                StateLoadDescription classInstanceRestoreDescription = this.classNamesResolveTable.get(classUid);
                if (null != classInstanceRestoreDescription) {
                    Constructor<AnnotatedSerializable> nonTrivialConstructor = classInstanceRestoreDescription.getNonTrivialConstructor();
                    if (null != nonTrivialConstructor) {
                        assert (0 < nonTrivialConstructor.getParameterTypes().length);
                        RemapSerializedArguments remappingDescription = nonTrivialConstructor.getAnnotation(RemapSerializedArguments.class);
                        Class<?>[] argumentsTypes = nonTrivialConstructor.getParameterTypes();
                        Set<Integer> argumentsToLoad = SerializatorOfAnnotated.getRecordsForConstructorIds(nonTrivialConstructor, remappingDescription);
                        TreeMap<Integer, String> constructorArgumentsData = new TreeMap<Integer, String>();
                        Iterator stateRecordIterator = stateRecords.iterator();
                        while (stateRecordIterator.hasNext()) {
                            Node stateRecord = (Node)stateRecordIterator.next();
                            int constructorArgumentIndex = Integer.parseInt(stateRecord.getAttribute(CONSTRUCTOR_ARGUMENT_INDEX_ATTR));
                            if (!argumentsToLoad.remove(constructorArgumentIndex)) continue;
                            constructorArgumentsData.put(constructorArgumentIndex, SerializatorOfAnnotated.getTextContent(stateRecord));
                            stateRecordIterator.remove();
                        }
                        Object[] constructorArguments = new Object[nonTrivialConstructor.getParameterTypes().length];
                        if (null != remappingDescription) {
                            assert (remappingDescription.newArguments().length == nonTrivialConstructor.getParameterTypes().length);
                            for (int i = 0; i < remappingDescription.newArguments().length; ++i) {
                                constructorArguments[i] = this.unmarshal(argumentsTypes[i], (String)constructorArgumentsData.get(remappingDescription.newArguments()[i]));
                            }
                        } else {
                            int argumentIndex = 0;
                            for (String argumentData : constructorArgumentsData.values()) {
                                constructorArguments[argumentIndex] = this.unmarshal(argumentsTypes[argumentIndex], argumentData);
                                ++argumentIndex;
                            }
                        }
                        nonTrivialConstructor.setAccessible(true);
                        restored = nonTrivialConstructor.newInstance(constructorArguments);
                    } else {
                        restored = classInstanceRestoreDescription.getRestoredClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    this.loadStateFor(restored, classInstanceRestoreDescription, stateRecords);
                    break block19;
                }
                Log.error(String.format("NXS-load: class for id %s was not registered in %s", classUid, this.getClass().getName()));
            }
            catch (NumberFormatException e) {
                Log.error(String.format("NXS-load: number data format error: %s", e.getMessage()));
            }
            catch (NullPointerException e) {
                Log.error(String.format("NXS-load: xml structure error: %s", e.getMessage()));
            }
            catch (InstantiationException e) {
                Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s", e.getMessage()));
            }
            catch (IllegalAccessException e) {
                Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s", e.getMessage()));
            }
            catch (InvocationTargetException e) {
                Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s", e.getMessage()));
            }
            catch (NoSuchMethodException e) {
                Log.error(String.format("NXS-load: AnnotatedSerializable implementation error: %s", e.getMessage()));
            }
        }
        if (null != restored) {
            restored.finalizeDeserialization();
        }
        return restored;
    }

    private Object unmarshal(Class to, String from) {
        assert (null != to && null != from);
        if (Integer.TYPE.equals(to)) {
            return Integer.parseInt(from);
        }
        if (Double.TYPE.equals(to)) {
            return Double.parseDouble(from);
        }
        if (Float.TYPE.equals(to)) {
            return Float.valueOf(Float.parseFloat(from));
        }
        if (Boolean.TYPE.equals(to)) {
            return Boolean.parseBoolean(from);
        }
        if (String.class.equals((Object)to)) {
            return from;
        }
        if (CoreTime.class.equals((Object)to)) {
            return new CoreTime(from);
        }
        Log.error(String.format("NXS-save: failed to unmarshal string '%s' into object of class '%s'", from, to.getName()));
        return null;
    }

    private static Set<Integer> getRecordsForConstructorIds(Constructor<AnnotatedSerializable> nonTrivialConstructor, RemapSerializedArguments remappingDescription) {
        assert (null != nonTrivialConstructor);
        HashSet<Integer> argumentsToLoad = new HashSet<Integer>();
        if (null != remappingDescription) {
            for (int i = 0; i < remappingDescription.newArguments().length; ++i) {
                argumentsToLoad.add(remappingDescription.newArguments()[i]);
            }
        } else {
            for (int i = 0; i < nonTrivialConstructor.getParameterTypes().length; ++i) {
                argumentsToLoad.add(i);
            }
        }
        return argumentsToLoad;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveState(PrintStream where, AnnotatedSerializable what) {
        assert (null != where && null != what);
        this.wereErrorsDuringLastSerialization = false;
        Helper.openNode(where, what.getId());
        try {
            this.saveMethods(where, what);
            this.saveFields(where, what);
        }
        finally {
            Helper.closeNode(where, what.getId());
        }
    }

    boolean wereErrorsDuringLastSerialization() {
        return this.wereErrorsDuringLastSerialization;
    }

    private void saveFields(final PrintStream where, final AnnotatedSerializable what) {
        assert (null != where && null != what);
        SerializatorOfAnnotated.visitAllAnnotatedHierarchyFields(SaveTo.class, what.getClass(), new FieldVisitor<SaveTo>(){

            @Override
            public void visit(Field target, SaveTo annotation) {
                String nodeName = annotation.destinationNodeName();
                int constructorArgumentIndex = annotation.constructorArgumentNumber();
                try {
                    SerializatorOfAnnotated.this.nodeAttributesBuffer.clear();
                    SerializatorOfAnnotated.this.nodeAttributesBuffer.add(new Pair<String, String>(SerializatorOfAnnotated.CONSTRUCTOR_ARGUMENT_INDEX_ATTR, Integer.toString(constructorArgumentIndex)));
                    target.setAccessible(true);
                    where.print('<' + Helper.printNodeWithAttributes(nodeName, SerializatorOfAnnotated.this.nodeAttributesBuffer) + '>');
                    Object toSave = target.get(what);
                    if (null != toSave) {
                        where.print(toSave.toString());
                    }
                    Helper.closeNode(where, nodeName);
                }
                catch (IllegalAccessException e) {
                    SerializatorOfAnnotated.this.wereErrorsDuringLastSerialization = true;
                    Log.error(String.format("NXS-save: failed to save field '%s' for instance of '%s': %s", what.getClass().getName(), target.getName(), e.getMessage()));
                }
            }
        });
    }

    private void saveMethods(PrintStream where, AnnotatedSerializable what) {
        assert (null != where && null != what);
        for (Method stateSaver : what.getClass().getMethods()) {
            SaveTo saveToAnnotation = stateSaver.getAnnotation(SaveTo.class);
            if (null == saveToAnnotation) continue;
            if (Void.TYPE.equals(stateSaver.getReturnType())) {
                this.wereErrorsDuringLastSerialization = true;
                Log.error(String.format("NXS-save: state getter method '%s' of class '%s' returns void!", stateSaver.getName(), what.getClass().getName()));
                continue;
            }
            if (0 < stateSaver.getParameterTypes().length) {
                this.wereErrorsDuringLastSerialization = true;
                Log.error(String.format("NXS-save: state getter method '%s' of class '%s' has arguments!", stateSaver.getName(), what.getClass().getName()));
                continue;
            }
            String nodeName = saveToAnnotation.destinationNodeName();
            int constructorArgumentIndex = saveToAnnotation.constructorArgumentNumber();
            try {
                Object toSave = stateSaver.invoke((Object)what, new Object[0]);
                this.nodeAttributesBuffer.clear();
                this.nodeAttributesBuffer.add(new Pair<String, String>(CONSTRUCTOR_ARGUMENT_INDEX_ATTR, Integer.toString(constructorArgumentIndex)));
                where.print('<' + Helper.printNodeWithAttributes(nodeName, this.nodeAttributesBuffer) + '>');
                if (null != toSave) {
                    where.print(toSave.toString());
                }
                Helper.closeNode(where, nodeName);
            }
            catch (IllegalAccessException e) {
                this.wereErrorsDuringLastSerialization = true;
                Log.error(String.format("NXS-save: failed to save method '%s' return for instance of '%s': %s", what.getClass().getName(), stateSaver.getName(), e.getMessage()));
            }
            catch (InvocationTargetException e) {
                this.wereErrorsDuringLastSerialization = true;
                Log.error(String.format("NXS-save: failed to save method '%s' return for instance of '%s': %s", what.getClass().getName(), stateSaver.getName(), e.getMessage()));
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static final class StateLoadDescription {
        private final Class<? extends AnnotatedSerializable> restoredClass;
        private final Map<String, StateRecordLoader> stateLoaders = new HashMap<String, StateRecordLoader>();
        private Constructor<AnnotatedSerializable> nonTrivialConstructor = null;

        private void findAnnotatedForLoadingFields() {
            SerializatorOfAnnotated.visitAllAnnotatedHierarchyFields(LoadFrom.class, this.restoredClass, new FieldVisitor<LoadFrom>(){

                @Override
                public void visit(Field target, LoadFrom annotation) {
                    StateLoadDescription.this.stateLoaders.put(annotation.sourceNodeName(), new SetFieldStateLoader(target));
                }
            });
        }

        private void finadAnnotatedForLoadingMethods() {
            for (Method stateLoader : this.restoredClass.getMethods()) {
                LoadFrom stateAnnotation = stateLoader.getAnnotation(LoadFrom.class);
                if (null == stateAnnotation) continue;
                if (1 != stateLoader.getParameterTypes().length || !String.class.equals(stateLoader.getParameterTypes()[0])) {
                    Log.warning(String.format("NXS-init: found annotated method with bad signature: class=%s method=%s", this.restoredClass.getName(), stateLoader.getName()));
                }
                this.stateLoaders.put(stateAnnotation.sourceNodeName(), new InvokeMethodStateLoader(stateLoader));
            }
        }

        private void findNonTrivialConstructor() {
            for (Constructor<?> stateInitiator : this.restoredClass.getDeclaredConstructors()) {
                if (null == stateInitiator.getAnnotation(DeserializationConstructor.class) || 0 == stateInitiator.getParameterTypes().length) continue;
                this.nonTrivialConstructor = stateInitiator;
                break;
            }
        }

        StateLoadDescription(Class<? extends AnnotatedSerializable> restoredClass) {
            assert (null != restoredClass);
            this.restoredClass = restoredClass;
            this.findAnnotatedForLoadingFields();
            this.finadAnnotatedForLoadingMethods();
            this.findNonTrivialConstructor();
        }

        Constructor<AnnotatedSerializable> getNonTrivialConstructor() {
            return this.nonTrivialConstructor;
        }

        Class<? extends AnnotatedSerializable> getRestoredClass() {
            return this.restoredClass;
        }

        Map<String, StateRecordLoader> getStateLoaders() {
            return this.stateLoaders;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static interface FieldVisitor<T extends Annotation> {
        public void visit(Field var1, T var2);
    }
}

