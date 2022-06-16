package com.aire.android.reflect;

import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class ReflectionTool {
    private static ReflectionTool mTool = new ReflectionTool();
    private AbstractMap<String, Field> fieldMap;
    private Method getClassMethod;
    private Method getDeclaredMethod;
    private Method getFieldMethod;
    private Method getFieldsMethod;
    public Method getMethodsMethod;
    private AbstractMap<String, Method> methodHashMap;
    private boolean useBreaker;

    /* loaded from: classes.dex */
    public interface IFieldGet {
        Object get();

        boolean set(Object obj);
    }

    /* loaded from: classes.dex */
    public interface IMethodCall {
        Object call(Object... objArr);
    }

    public ReflectionTool() {
        this.useBreaker = Build.VERSION.SDK_INT >= 28;
        this.methodHashMap = new ConcurrentHashMap();
        this.fieldMap = new ConcurrentHashMap();
        try {
            Method declaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            this.getDeclaredMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            Method method = Class.class.getMethod("getDeclaredField", String.class);
            this.getFieldMethod = method;
            method.setAccessible(true);
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
        try {
            Method method2 = Class.class.getMethod("forName", String.class);
            this.getClassMethod = method2;
            method2.setAccessible(true);
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        }
        if (this.useBreaker) {
            try {
                Method method3 = Class.class.getMethod("getDeclaredFields", new Class[0]);
                this.getFieldsMethod = method3;
                method3.setAccessible(true);
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            }
            try {
                Method method4 = Class.class.getMethod("getDeclaredMethods", new Class[0]);
                this.getMethodsMethod = method4;
                method4.setAccessible(true);
            } catch (NoSuchMethodException e5) {
                e5.printStackTrace();
            }
        }
    }

    public Class<?> findClass(String name) {
        Method method;
        if (this.useBreaker && (method = this.getClassMethod) != null) {
            try {
                return (Class) method.invoke(Class.class, name);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public Field[] getDeclaredFields(Class clz) {
        Method method;
        if (this.useBreaker && (method = this.getFieldsMethod) != null) {
            try {
                return (Field[]) method.invoke(clz, new Object[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        Log.d("diggo-dbg", " get df failed");
        return clz.getDeclaredFields();
    }

    public Method[] getDeclaredMethods(Class clz) {
        Method method;
        if (this.useBreaker && (method = this.getMethodsMethod) != null) {
            try {
                return (Method[]) method.invoke(clz, new Object[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        return clz.getDeclaredMethods();
    }

    public Method getDeclaredMethod(Class clz, String name, Class<?>... args) throws NoSuchMethodException {
        Method method;
        if (this.useBreaker && (method = this.getDeclaredMethod) != null) {
            try {
                return (Method) method.invoke(clz, name, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        return clz.getMethod(name, args);
    }

    public Field getDeclaredField(Class clz, String name) throws NoSuchFieldException {
        Method method;
        if (this.useBreaker && (method = this.getFieldMethod) != null) {
            try {
                return (Field) method.invoke(clz, name);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        return clz.getDeclaredField(name);
    }

    public static ReflectionTool get() {
        return mTool;
    }

    public Builder on(Object data) {
        return new Builder(this.methodHashMap, this.fieldMap, this.getDeclaredMethod, this.getFieldMethod, data);
    }

    public Builder onClass(String name) {
        Class<?> clz = findClass(name);
        return new Builder(this.methodHashMap, this.fieldMap, this.getDeclaredMethod, this.getFieldMethod, clz);
    }

    public Builder onClass(Class<?> clz) {
        return new Builder(this.methodHashMap, this.fieldMap, this.getDeclaredMethod, this.getFieldMethod, clz);
    }

    /* loaded from: classes.dex */
    public static class Builder implements IMethodCall, IFieldGet {
        AbstractMap<String, Field> fieldsHashMap;
        Method mFindFieldMethod;
        Method mFindMethod;
        Object mTarget;
        AbstractMap<String, Method> methodHashMap;
        Field targetField;
        Method targetMethod;
        int goSuperLevel = 0;
        boolean mResoleParent = true;

        Builder(AbstractMap<String, Method> map, AbstractMap<String, Field> fields, Method method, Method fieldMethod, Object data) {
            this.mTarget = data;
            this.mFindMethod = method;
            this.methodHashMap = map;
            this.fieldsHashMap = fields;
            this.mFindFieldMethod = fieldMethod;
        }

        public Builder resolveParent(boolean resolveParent) {
            this.mResoleParent = resolveParent;
            return this;
        }

        private String getMethodSignature(String name, Class<?>[] args) {
            if (this.mTarget != null) {
                StringBuilder builder = new StringBuilder();
                if (this.mTarget.getClass() == Class.class) {
                    builder.append(((Class) this.mTarget).getName());
                } else {
                    builder.append(this.mTarget.getClass().getName());
                }
                builder.append(';');
                builder.append(name);
                builder.append(';');
                if (args != null && args.length > 0) {
                    for (Class<?> cls : args) {
                        if (cls != null) {
                            builder.append(cls.getName());
                            builder.append(';');
                        }
                    }
                }
                return builder.toString();
            }
            return null;
        }

        public IMethodCall staticMethodSignature(String name, Class<?>... args) {
            return findMethod((Class) this.mTarget, name, args);
        }

        public IMethodCall methodSignature(String name, Class<?>... args) {
            return findMethod(this.mTarget.getClass(), name, args);
        }

        public IMethodCall findMethod(Class targetClass, String name, Class<?>... args) {
            String signature = getMethodSignature(name, args);
            if (signature == null) {
                return this;
            }
            Method method = this.methodHashMap.get(signature);
            this.targetMethod = method;
            if (method == null) {
                Class objectClass = targetClass;
                if (objectClass != Object.class) {
                    do {
                        int i = this.goSuperLevel;
                        if (i > 0) {
                            this.goSuperLevel = i - 1;
                        } else {
                            try {
                                Method method2 = (Method) this.mFindMethod.invoke(objectClass, name, args);
                                this.targetMethod = method2;
                                if (method2 == null) {
                                    return this;
                                }
                                method2.setAccessible(true);
                                this.methodHashMap.put(signature, this.targetMethod);
                                return this;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e2) {
                                e2.printStackTrace();
                            }
                        }
                        objectClass = objectClass.getSuperclass();
                        if (objectClass == null || !this.mResoleParent) {
                            break;
                        }
                    } while (objectClass != Object.class);
                } else {
                    return this;
                }
            }
            return this;
        }

        public Builder goSuper() {
            this.goSuperLevel++;
            return this;
        }

        @Override // com.lens.hook.utils.LensReflectionTool.IMethodCall
        public Object call(Object... args) {
            Method method = this.targetMethod;
            if (method != null) {
                try {
                    Object val = method.invoke(this.mTarget, args);
                    if (val == null) {
                        if (this.targetMethod.getReturnType() == Void.class) {
                            return "void";
                        }
                    }
                    return val;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return "IllegalAccessException: " + e.getMessage();
                } catch (InvocationTargetException e2) {
                    e2.printStackTrace();
                    Throwable throwable = e2.getTargetException();
                    if (throwable != null) {
                        return throwable.getClass().getSimpleName() + ":" + throwable.getMessage();
                    }
                    return "InvocationTargetException: " + e2.getMessage();
                } catch (Throwable e3) {
                    e3.printStackTrace();
                    return "Error : " + e3.getMessage();
                }
            }
            return "Error: targetMethod is null!";
        }

        private IFieldGet findField(Class<?> objectClass, String name) {
            String signature = getMethodSignature(name, null);
            if (signature == null) {
                return this;
            }
            Field field = this.fieldsHashMap.get(signature);
            this.targetField = field;
            if (field == null) {
                if (objectClass != Object.class) {
                    do {
                        int i = this.goSuperLevel;
                        if (i > 0) {
                            this.goSuperLevel = i - 1;
                        } else {
                            try {
                                Field field2 = (Field) this.mFindFieldMethod.invoke(objectClass, name);
                                this.targetField = field2;
                                if (field2 == null) {
                                    return this;
                                }
                                field2.setAccessible(true);
                                this.fieldsHashMap.put(signature, this.targetField);
                                return this;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e2) {
                                e2.printStackTrace();
                            }
                        }
                        objectClass = objectClass.getSuperclass();
                        if (objectClass == null || !this.mResoleParent) {
                            break;
                        }
                    } while (objectClass != Object.class);
                } else {
                    return this;
                }
            }
            return this;
        }

        public IFieldGet staticFieldName(String name) {
            return findField((Class) this.mTarget, name);
        }

        public IFieldGet fieldName(String name) {
            return findField(this.mTarget.getClass(), name);
        }

        public IFieldGet fieldName(String name, Class<?> ofClass) {
            this.goSuperLevel = 0;
            Class targetClass = this.mTarget.getClass();
            if (ofClass != null && ofClass.isAssignableFrom(targetClass)) {
                return findField(ofClass, name);
            }
            return findField(this.mTarget.getClass(), name);
        }

        @Override // com.lens.hook.utils.LensReflectionTool.IFieldGet
        public Object get() {
            Field field = this.targetField;
            if (field != null) {
                try {
                    return field.get(this.mTarget);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }

        @Override // com.lens.hook.utils.LensReflectionTool.IFieldGet
        public boolean set(Object value) {
            Field field = this.targetField;
            if (field != null) {
                int mod = field.getModifiers();
                if (Modifier.isFinal(mod)) {
                    int flag = ((Integer) ReflectionTool.get().on(this.targetField).fieldName("accessFlags").get()).intValue();
                    ReflectionTool.get().on(this.targetField).fieldName("accessFlags").set(Integer.valueOf(flag & (-17)));
                }
                try {
                    this.targetField.set(this.mTarget, value);
                    Log.d("Lens", " set value success " + this.targetField.getName());
                    return true;
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                    Log.e("Lens", "reflection set value fail ");
                    return false;
                }
            }
            return false;
        }
    }

    public static Object fieldChain(Object target, String[] names) {
        return fieldChain(get(), target, names, true);
    }

    public static Object fieldChain(ReflectionTool tool, Object target, String[] names) {
        return fieldChain(tool, target, names, true);
    }

    public static Object fieldChain(ReflectionTool tool, Object target, String[] names, boolean resolveParent) {
        if (tool == null) {
            tool = get();
        }
        if (target == null || names == null || names.length == 0) {
            return null;
        }
        for (String name : names) {
            if (target == null) {
                return null;
            }
            target = tool.on(target).resolveParent(resolveParent).fieldName(name).get();
        }
        return target;
    }
}