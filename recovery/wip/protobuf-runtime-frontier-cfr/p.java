/*
 * Decompiled with CFR 0.152.
 */
package l1rpb;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import l1rpb.a;
import l1rpb.aa;
import l1rpb.ab;
import l1rpb.ac;
import l1rpb.ap;
import l1rpb.as;
import l1rpb.b;
import l1rpb.i;
import l1rpb.k;
import l1rpb.l;
import l1rpb.n;
import l1rpb.o;
import l1rpb.q;
import l1rpb.t;
import l1rpb.x;
import l1rpb.y;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class p
extends l1rpb.a
implements Serializable {
    private static final long a = 1L;
    protected static boolean m = false;

    protected p() {
    }

    protected p(a<?> builder) {
    }

    @Override
    public ab<? extends x> m() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    static void ac() {
        m = true;
    }

    protected abstract g l();

    @Override
    public k.a J() {
        return this.l().a;
    }

    private Map<k.f, Object> h() {
        TreeMap<k.f, Object> result = new TreeMap<k.f, Object>();
        k.a descriptor = this.l().a;
        for (k.f field : descriptor.h()) {
            if (field.n()) {
                List value = (List)this.b(field);
                if (value.isEmpty()) continue;
                result.put(field, value);
                continue;
            }
            if (!this.a_(field)) continue;
            result.put(field, this.b(field));
        }
        return result;
    }

    @Override
    public boolean a() {
        for (k.f field : this.J().h()) {
            if (field.k() && !this.a_(field)) {
                return false;
            }
            if (field.g() != k.f.a.i) continue;
            if (field.n()) {
                List messageList = (List)this.b(field);
                for (x element : messageList) {
                    if (element.a()) continue;
                    return false;
                }
                continue;
            }
            if (!this.a_(field) || ((x)this.b(field)).a()) continue;
            return false;
        }
        return true;
    }

    @Override
    public Map<k.f, Object> a_() {
        return Collections.unmodifiableMap(this.h());
    }

    @Override
    public boolean a_(k.f field) {
        return this.l().a(field).b(this);
    }

    @Override
    public Object b(k.f field) {
        return this.l().a(field).a(this);
    }

    @Override
    public int c(k.f field) {
        return this.l().a(field).c(this);
    }

    @Override
    public Object a(k.f field, int index) {
        return this.l().a(field).a(this, index);
    }

    @Override
    public ap b_() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    protected boolean a(l1rpb.h input, ap.a unknownFields, n extensionRegistry, int tag) throws IOException {
        return unknownFields.a(tag, input);
    }

    protected void ad() {
    }

    protected abstract x.a b(b var1);

    public static <ContainingType extends x, Type> h<ContainingType, Type> a(final x scope, final int descriptorIndex, Class singularType, x defaultInstance) {
        return new h(new f(){

            public k.f a() {
                return scope.J().i().get(descriptorIndex);
            }
        }, singularType, defaultInstance);
    }

    public static <ContainingType extends x, Type> h<ContainingType, Type> a(Class singularType, x defaultInstance) {
        return new h(null, singularType, defaultInstance);
    }

    private static Method b(Class clazz, String name, Class ... params) {
        try {
            return clazz.getMethod(name, params);
        }
        catch (NoSuchMethodException e2) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e2);
        }
    }

    private static Object b(Method method, Object object, Object ... params) {
        try {
            return method.invoke(object, params);
        }
        catch (IllegalAccessException e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        }
        catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            if (cause instanceof Error) {
                throw (Error)cause;
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected Object I() throws ObjectStreamException {
        return new q.g(this);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class l1rpb.p$g {
        private final k.a a;
        private final a[] b;
        private String[] c;
        private volatile boolean d;

        public l1rpb.p$g(k.a descriptor, String[] camelCaseNames, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
            this(descriptor, camelCaseNames);
            this.a(messageClass, builderClass);
        }

        public l1rpb.p$g(k.a descriptor, String[] camelCaseNames) {
            this.a = descriptor;
            this.c = camelCaseNames;
            this.b = new a[descriptor.h().size()];
            this.d = false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public l1rpb.p$g a(Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
            if (this.d) {
                return this;
            }
            l1rpb.p$g g2 = this;
            synchronized (g2) {
                if (this.d) {
                    return this;
                }
                for (int i2 = 0; i2 < this.b.length; ++i2) {
                    k.f field = this.a.h().get(i2);
                    if (field.n()) {
                        if (field.g() == k.f.a.i) {
                            this.b[i2] = new d(field, this.c[i2], messageClass, builderClass);
                            continue;
                        }
                        if (field.g() == k.f.a.h) {
                            this.b[i2] = new b(field, this.c[i2], messageClass, builderClass);
                            continue;
                        }
                        this.b[i2] = new c(field, this.c[i2], messageClass, builderClass);
                        continue;
                    }
                    this.b[i2] = field.g() == k.f.a.i ? new g(field, this.c[i2], messageClass, builderClass) : (field.g() == k.f.a.h ? new e(field, this.c[i2], messageClass, builderClass) : new f(field, this.c[i2], messageClass, builderClass));
                }
                this.d = true;
                this.c = null;
                return this;
            }
        }

        private a a(k.f field) {
            if (field.u() != this.a) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
            if (field.t()) {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
            return this.b[field.a()];
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static final class d
        extends c {
            private final Method k;

            d(k.f descriptor, String camelCaseName, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.k = p.b(this.a, "newBuilder", new Class[0]);
            }

            private Object a(Object value) {
                if (this.a.isInstance(value)) {
                    return value;
                }
                return ((x.a)p.b(this.k, null, new Object[0])).c((x)value).ak();
            }

            @Override
            public void a(l1rpb.p$a builder, int index, Object value) {
                super.a(builder, index, this.a(value));
            }

            @Override
            public void b(l1rpb.p$a builder, Object value) {
                super.b(builder, this.a(value));
            }

            @Override
            public x.a a() {
                return (x.a)p.b(this.k, null, new Object[0]);
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static final class g
        extends f {
            private final Method h;
            private final Method i;

            g(k.f descriptor, String camelCaseName, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.h = p.b(this.a, "newBuilder", new Class[0]);
                this.i = p.b(builderClass, "get" + camelCaseName + "Builder", new Class[0]);
            }

            private Object a(Object value) {
                if (this.a.isInstance(value)) {
                    return value;
                }
                return ((x.a)p.b(this.h, null, new Object[0])).c((x)value).aj();
            }

            @Override
            public void a(l1rpb.p$a builder, Object value) {
                super.a(builder, this.a(value));
            }

            @Override
            public x.a a() {
                return (x.a)p.b(this.h, null, new Object[0]);
            }

            @Override
            public x.a e(l1rpb.p$a builder) {
                return (x.a)p.b(this.i, builder, new Object[0]);
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static final class b
        extends c {
            private final Method k;
            private final Method l;

            b(k.f descriptor, String camelCaseName, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.k = p.b(this.a, "valueOf", new Class[]{k.e.class});
                this.l = p.b(this.a, "getValueDescriptor", new Class[0]);
            }

            @Override
            public Object a(p message) {
                ArrayList<Object> newList = new ArrayList<Object>();
                for (Object element : (List)super.a(message)) {
                    newList.add(p.b(this.l, element, new Object[0]));
                }
                return Collections.unmodifiableList(newList);
            }

            @Override
            public Object a(l1rpb.p$a builder) {
                ArrayList<Object> newList = new ArrayList<Object>();
                for (Object element : (List)super.a(builder)) {
                    newList.add(p.b(this.l, element, new Object[0]));
                }
                return Collections.unmodifiableList(newList);
            }

            @Override
            public Object a(p message, int index) {
                return p.b(this.l, super.a(message, index), new Object[0]);
            }

            @Override
            public Object a(l1rpb.p$a builder, int index) {
                return p.b(this.l, super.a(builder, index), new Object[0]);
            }

            @Override
            public void a(l1rpb.p$a builder, int index, Object value) {
                super.a(builder, index, p.b(this.k, null, new Object[]{value}));
            }

            @Override
            public void b(l1rpb.p$a builder, Object value) {
                super.b(builder, p.b(this.k, null, new Object[]{value}));
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static final class e
        extends f {
            private Method h;
            private Method i;

            e(k.f descriptor, String camelCaseName, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
                this.h = p.b(this.a, "valueOf", new Class[]{k.e.class});
                this.i = p.b(this.a, "getValueDescriptor", new Class[0]);
            }

            @Override
            public Object a(p message) {
                return p.b(this.i, super.a(message), new Object[0]);
            }

            @Override
            public Object a(l1rpb.p$a builder) {
                return p.b(this.i, super.a(builder), new Object[0]);
            }

            @Override
            public void a(l1rpb.p$a builder, Object value) {
                super.a(builder, p.b(this.h, null, new Object[]{value}));
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static class c
        implements a {
            protected final Class a;
            protected final Method b;
            protected final Method c;
            protected final Method d;
            protected final Method e;
            protected final Method f;
            protected final Method g;
            protected final Method h;
            protected final Method i;
            protected final Method j;

            c(k.f descriptor, String camelCaseName, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
                this.b = p.b(messageClass, "get" + camelCaseName + "List", new Class[0]);
                this.c = p.b(builderClass, "get" + camelCaseName + "List", new Class[0]);
                this.d = p.b(messageClass, "get" + camelCaseName, new Class[]{Integer.TYPE});
                this.e = p.b(builderClass, "get" + camelCaseName, new Class[]{Integer.TYPE});
                this.a = this.d.getReturnType();
                this.f = p.b(builderClass, "set" + camelCaseName, new Class[]{Integer.TYPE, this.a});
                this.g = p.b(builderClass, "add" + camelCaseName, new Class[]{this.a});
                this.h = p.b(messageClass, "get" + camelCaseName + "Count", new Class[0]);
                this.i = p.b(builderClass, "get" + camelCaseName + "Count", new Class[0]);
                this.j = p.b(builderClass, "clear" + camelCaseName, new Class[0]);
            }

            @Override
            public Object a(p message) {
                return p.b(this.b, message, new Object[0]);
            }

            @Override
            public Object a(l1rpb.p$a builder) {
                return p.b(this.c, builder, new Object[0]);
            }

            @Override
            public void a(l1rpb.p$a builder, Object value) {
                this.d(builder);
                for (Object element : (List)value) {
                    this.b(builder, element);
                }
            }

            @Override
            public Object a(p message, int index) {
                return p.b(this.d, message, new Object[]{index});
            }

            @Override
            public Object a(l1rpb.p$a builder, int index) {
                return p.b(this.e, builder, new Object[]{index});
            }

            @Override
            public void a(l1rpb.p$a builder, int index, Object value) {
                p.b(this.f, builder, new Object[]{index, value});
            }

            @Override
            public void b(l1rpb.p$a builder, Object value) {
                p.b(this.g, builder, new Object[]{value});
            }

            @Override
            public boolean b(p message) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override
            public boolean b(l1rpb.p$a builder) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override
            public int c(p message) {
                return (Integer)p.b(this.h, message, new Object[0]);
            }

            @Override
            public int c(l1rpb.p$a builder) {
                return (Integer)p.b(this.i, builder, new Object[0]);
            }

            @Override
            public void d(l1rpb.p$a builder) {
                p.b(this.j, builder, new Object[0]);
            }

            @Override
            public x.a a() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override
            public x.a e(l1rpb.p$a builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static class f
        implements a {
            protected final Class<?> a;
            protected final Method b;
            protected final Method c;
            protected final Method d;
            protected final Method e;
            protected final Method f;
            protected final Method g;

            f(k.f descriptor, String camelCaseName, Class<? extends p> messageClass, Class<? extends l1rpb.p$a> builderClass) {
                this.b = p.b(messageClass, "get" + camelCaseName, new Class[0]);
                this.c = p.b(builderClass, "get" + camelCaseName, new Class[0]);
                this.a = this.b.getReturnType();
                this.d = p.b(builderClass, "set" + camelCaseName, new Class[]{this.a});
                this.e = p.b(messageClass, "has" + camelCaseName, new Class[0]);
                this.f = p.b(builderClass, "has" + camelCaseName, new Class[0]);
                this.g = p.b(builderClass, "clear" + camelCaseName, new Class[0]);
            }

            @Override
            public Object a(p message) {
                return p.b(this.b, message, new Object[0]);
            }

            @Override
            public Object a(l1rpb.p$a builder) {
                return p.b(this.c, builder, new Object[0]);
            }

            @Override
            public void a(l1rpb.p$a builder, Object value) {
                p.b(this.d, builder, new Object[]{value});
            }

            @Override
            public Object a(p message, int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override
            public Object a(l1rpb.p$a builder, int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override
            public void a(l1rpb.p$a builder, int index, Object value) {
                throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
            }

            @Override
            public void b(l1rpb.p$a builder, Object value) {
                throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
            }

            @Override
            public boolean b(p message) {
                return (Boolean)p.b(this.e, message, new Object[0]);
            }

            @Override
            public boolean b(l1rpb.p$a builder) {
                return (Boolean)p.b(this.f, builder, new Object[0]);
            }

            @Override
            public int c(p message) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override
            public int c(l1rpb.p$a builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override
            public void d(l1rpb.p$a builder) {
                p.b(this.g, builder, new Object[0]);
            }

            @Override
            public x.a a() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override
            public x.a e(l1rpb.p$a builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
        }

        private static interface a {
            public Object a(p var1);

            public Object a(l1rpb.p$a var1);

            public void a(l1rpb.p$a var1, Object var2);

            public Object a(p var1, int var2);

            public Object a(l1rpb.p$a var1, int var2);

            public void a(l1rpb.p$a var1, int var2, Object var3);

            public void b(l1rpb.p$a var1, Object var2);

            public boolean b(p var1);

            public boolean b(l1rpb.p$a var1);

            public int c(p var1);

            public int c(l1rpb.p$a var1);

            public void d(l1rpb.p$a var1);

            public x.a a();

            public x.a e(l1rpb.p$a var1);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class h<ContainingType extends x, Type> {
        private f a;
        private final Class b;
        private final x c;
        private final Method d;
        private final Method e;

        private h(f descriptorRetriever, Class singularType, x messageDefaultInstance) {
            if (x.class.isAssignableFrom(singularType) && !singularType.isInstance(messageDefaultInstance)) {
                throw new IllegalArgumentException("Bad messageDefaultInstance for " + singularType.getName());
            }
            this.a = descriptorRetriever;
            this.b = singularType;
            this.c = messageDefaultInstance;
            if (ac.class.isAssignableFrom(singularType)) {
                this.d = p.b(singularType, "valueOf", new Class[]{k.e.class});
                this.e = p.b(singularType, "getValueDescriptor", new Class[0]);
            } else {
                this.d = null;
                this.e = null;
            }
        }

        public void a(final k.f descriptor) {
            if (this.a != null) {
                throw new IllegalStateException("Already initialized.");
            }
            this.a = new f(){

                public k.f a() {
                    return descriptor;
                }
            };
        }

        public k.f a() {
            if (this.a == null) {
                throw new IllegalStateException("getDescriptor() called before internalInit()");
            }
            return this.a.a();
        }

        public x b() {
            return this.c;
        }

        private Object a(Object value) {
            k.f descriptor = this.a();
            if (descriptor.n()) {
                if (descriptor.g() == k.f.a.i || descriptor.g() == k.f.a.h) {
                    ArrayList<Object> result = new ArrayList<Object>();
                    for (Object element : (List)value) {
                        result.add(this.b(element));
                    }
                    return result;
                }
                return value;
            }
            return this.b(value);
        }

        private Object b(Object value) {
            k.f descriptor = this.a();
            switch (descriptor.g()) {
                case i: {
                    if (this.b.isInstance(value)) {
                        return value;
                    }
                    return this.c.N().c((x)value).ak();
                }
                case h: {
                    return p.b(this.d, null, new Object[]{(k.e)value});
                }
            }
            return value;
        }

        private Object c(Object value) {
            k.f descriptor = this.a();
            if (descriptor.n()) {
                if (descriptor.g() == k.f.a.h) {
                    ArrayList<Object> result = new ArrayList<Object>();
                    for (Object element : (List)value) {
                        result.add(this.d(element));
                    }
                    return result;
                }
                return value;
            }
            return this.d(value);
        }

        private Object d(Object value) {
            k.f descriptor = this.a();
            switch (descriptor.g()) {
                case h: {
                    return p.b(this.e, value, new Object[0]);
                }
            }
            return value;
        }
    }

    private static interface f {
        public k.f a();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class c<MessageType extends d, BuilderType extends c>
    extends a<BuilderType>
    implements e<MessageType> {
        private o<k.f> a = o.b();

        protected c() {
        }

        protected c(b parent) {
            super(parent);
        }

        public BuilderType B() {
            this.a = o.b();
            return (BuilderType)((c)super.ah());
        }

        public BuilderType A() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        private void k() {
            if (this.a.d()) {
                this.a = this.a.e();
            }
        }

        private void e(h<MessageType, ?> extension) {
            if (extension.a().u() != this.J()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.a().u().d() + "\" which does not match message type \"" + this.J().d() + "\".");
            }
        }

        @Override
        public final <Type> boolean a(h<MessageType, Type> extension) {
            this.e(extension);
            return this.a.a(extension.a());
        }

        @Override
        public final <Type> int b(h<MessageType, List<Type>> extension) {
            this.e(extension);
            k.f descriptor = extension.a();
            return this.a.d(descriptor);
        }

        @Override
        public final <Type> Type c(h<MessageType, Type> extension) {
            this.e(extension);
            k.f descriptor = extension.a();
            Object value = this.a.b(descriptor);
            if (value == null) {
                if (descriptor.n()) {
                    return (Type)Collections.emptyList();
                }
                if (descriptor.g() == k.f.a.i) {
                    return (Type)extension.b();
                }
                return (Type)((h)extension).a(descriptor.r());
            }
            return (Type)((h)extension).a(value);
        }

        @Override
        public final <Type> Type a(h<MessageType, List<Type>> extension, int index) {
            this.e(extension);
            k.f descriptor = extension.a();
            return (Type)((h)extension).b(this.a.a(descriptor, index));
        }

        public final <Type> BuilderType a(h<MessageType, Type> extension, Type value) {
            this.e(extension);
            this.k();
            k.f descriptor = extension.a();
            this.a.a(descriptor, ((h)extension).c(value));
            this.t_();
            return (BuilderType)this;
        }

        public final <Type> BuilderType a(h<MessageType, List<Type>> extension, int index, Type value) {
            this.e(extension);
            this.k();
            k.f descriptor = extension.a();
            this.a.a(descriptor, index, ((h)extension).d(value));
            this.t_();
            return (BuilderType)this;
        }

        public final <Type> BuilderType b(h<MessageType, List<Type>> extension, Type value) {
            this.e(extension);
            this.k();
            k.f descriptor = extension.a();
            this.a.b(descriptor, ((h)extension).d(value));
            this.t_();
            return (BuilderType)this;
        }

        public final <Type> BuilderType d(h<MessageType, ?> extension) {
            this.e(extension);
            this.k();
            this.a.c(extension.a());
            this.t_();
            return (BuilderType)this;
        }

        protected boolean af() {
            return this.a.i();
        }

        private o<k.f> m() {
            this.a.c();
            return this.a;
        }

        @Override
        public boolean a() {
            return super.a() && this.af();
        }

        @Override
        protected boolean a(l1rpb.h input, ap.a unknownFields, n extensionRegistry, int tag) throws IOException {
            return a.a.a(input, unknownFields, extensionRegistry, this.J(), this, null, tag);
        }

        @Override
        public Map<k.f, Object> a_() {
            Map result = ((a)this).k();
            result.putAll(this.a.g());
            return Collections.unmodifiableMap(result);
        }

        @Override
        public Object b(k.f field) {
            if (field.t()) {
                this.h(field);
                Object value = this.a.b(field);
                if (value == null) {
                    if (field.g() == k.f.a.i) {
                        return l.a(field.w());
                    }
                    return field.r();
                }
                return value;
            }
            return super.b(field);
        }

        @Override
        public int c(k.f field) {
            if (field.t()) {
                this.h(field);
                return this.a.d(field);
            }
            return super.c(field);
        }

        @Override
        public Object a(k.f field, int index) {
            if (field.t()) {
                this.h(field);
                return this.a.a(field, index);
            }
            return super.a(field, index);
        }

        @Override
        public boolean a_(k.f field) {
            if (field.t()) {
                this.h(field);
                return this.a.a(field);
            }
            return super.a_(field);
        }

        public BuilderType e(k.f field, Object value) {
            if (field.t()) {
                this.h(field);
                this.k();
                this.a.a(field, value);
                this.t_();
                return (BuilderType)this;
            }
            return (BuilderType)((c)super.a(field, value));
        }

        public BuilderType e(k.f field) {
            if (field.t()) {
                this.h(field);
                this.k();
                this.a.c(field);
                this.t_();
                return (BuilderType)this;
            }
            return (BuilderType)((c)super.d(field));
        }

        public BuilderType c(k.f field, int index, Object value) {
            if (field.t()) {
                this.h(field);
                this.k();
                this.a.a(field, index, value);
                this.t_();
                return (BuilderType)this;
            }
            return (BuilderType)((c)super.a(field, index, value));
        }

        public BuilderType f(k.f field, Object value) {
            if (field.t()) {
                this.h(field);
                this.k();
                this.a.b(field, value);
                this.t_();
                return (BuilderType)this;
            }
            return (BuilderType)((c)super.b(field, value));
        }

        protected final void a(d other) {
            this.k();
            this.a.a(other.a);
            this.t_();
        }

        private void h(k.f field) {
            if (field.u() != this.J()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }

        @Override
        public /* synthetic */ a b(k.f x0, Object x1) {
            return this.f(x0, x1);
        }

        @Override
        public /* synthetic */ a a(k.f x0, int x1, Object x2) {
            return this.c(x0, x1, x2);
        }

        @Override
        public /* synthetic */ a d(k.f x0) {
            return this.e(x0);
        }

        @Override
        public /* synthetic */ a a(k.f x0, Object x1) {
            return this.e(x0, x1);
        }

        @Override
        public /* synthetic */ a ah() {
            return this.B();
        }

        @Override
        public /* synthetic */ a ai() {
            return this.A();
        }

        @Override
        public /* synthetic */ a.a e() {
            return this.B();
        }

        @Override
        public /* synthetic */ a.a d() {
            return this.A();
        }

        @Override
        public /* synthetic */ x.a c(k.f x0, Object x1) {
            return this.f(x0, x1);
        }

        @Override
        public /* synthetic */ x.a b(k.f x0, int x1, Object x2) {
            return this.c(x0, x1, x2);
        }

        @Override
        public /* synthetic */ x.a f(k.f x0) {
            return this.e(x0);
        }

        @Override
        public /* synthetic */ x.a d(k.f x0, Object x1) {
            return this.e(x0, x1);
        }

        @Override
        public /* synthetic */ x.a i() {
            return this.A();
        }

        @Override
        public /* synthetic */ x.a j() {
            return this.B();
        }

        @Override
        public /* synthetic */ y.a g() {
            return this.A();
        }

        @Override
        public /* synthetic */ y.a h() {
            return this.B();
        }

        @Override
        public /* synthetic */ b.a f() {
            return this.A();
        }

        @Override
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return this.A();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class d<MessageType extends d>
    extends p
    implements e<MessageType> {
        private final o<k.f> a;

        protected d() {
            this.a = o.a();
        }

        protected d(c<MessageType, ?> builder) {
            super(builder);
            this.a = ((c)builder).m();
        }

        private void d(h<MessageType, ?> extension) {
            if (extension.a().u() != this.J()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.a().u().d() + "\" which does not match message type \"" + this.J().d() + "\".");
            }
        }

        @Override
        public final <Type> boolean a(h<MessageType, Type> extension) {
            this.d(extension);
            return this.a.a(extension.a());
        }

        @Override
        public final <Type> int b(h<MessageType, List<Type>> extension) {
            this.d(extension);
            k.f descriptor = extension.a();
            return this.a.d(descriptor);
        }

        @Override
        public final <Type> Type c(h<MessageType, Type> extension) {
            this.d(extension);
            k.f descriptor = extension.a();
            Object value = this.a.b(descriptor);
            if (value == null) {
                if (descriptor.n()) {
                    return (Type)Collections.emptyList();
                }
                if (descriptor.g() == k.f.a.i) {
                    return (Type)extension.b();
                }
                return (Type)((h)extension).a(descriptor.r());
            }
            return (Type)((h)extension).a(value);
        }

        @Override
        public final <Type> Type a(h<MessageType, List<Type>> extension, int index) {
            this.d(extension);
            k.f descriptor = extension.a();
            return (Type)((h)extension).b(this.a.a(descriptor, index));
        }

        protected boolean W() {
            return this.a.i();
        }

        @Override
        public boolean a() {
            return super.a() && this.W();
        }

        @Override
        protected boolean a(l1rpb.h input, ap.a unknownFields, n extensionRegistry, int tag) throws IOException {
            return a.a.a(input, unknownFields, extensionRegistry, this.J(), null, this.a, tag);
        }

        @Override
        protected void ad() {
            this.a.c();
        }

        protected a X() {
            return new a(false);
        }

        protected a Y() {
            return new a(true);
        }

        protected int Z() {
            return this.a.j();
        }

        protected int aa() {
            return this.a.k();
        }

        protected Map<k.f, Object> ab() {
            return this.a.g();
        }

        @Override
        public Map<k.f, Object> a_() {
            Map result = ((p)this).h();
            result.putAll(this.ab());
            return Collections.unmodifiableMap(result);
        }

        @Override
        public boolean a_(k.f field) {
            if (field.t()) {
                this.d(field);
                return this.a.a(field);
            }
            return super.a_(field);
        }

        @Override
        public Object b(k.f field) {
            if (field.t()) {
                this.d(field);
                Object value = this.a.b(field);
                if (value == null) {
                    if (field.g() == k.f.a.i) {
                        return l.a(field.w());
                    }
                    return field.r();
                }
                return value;
            }
            return super.b(field);
        }

        @Override
        public int c(k.f field) {
            if (field.t()) {
                this.d(field);
                return this.a.d(field);
            }
            return super.c(field);
        }

        @Override
        public Object a(k.f field, int index) {
            if (field.t()) {
                this.d(field);
                return this.a.a(field, index);
            }
            return super.a(field, index);
        }

        private void d(k.f field) {
            if (field.u() != this.J()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }

        protected class a {
            private final Iterator<Map.Entry<k.f, Object>> b;
            private Map.Entry<k.f, Object> c;
            private final boolean d;

            private a(boolean messageSetWireFormat) {
                this.b = d.this.a.h();
                if (this.b.hasNext()) {
                    this.c = this.b.next();
                }
                this.d = messageSetWireFormat;
            }

            public void a(int end, i output) throws IOException {
                while (this.c != null && this.c.getKey().f() < end) {
                    k.f descriptor = this.c.getKey();
                    if (this.d && descriptor.h() == as.b.i && !descriptor.n()) {
                        if (this.c instanceof t.a) {
                            output.b(descriptor.f(), ((t.a)this.c).a().c());
                        } else {
                            output.d(descriptor.f(), (x)this.c.getValue());
                        }
                    } else {
                        o.a(descriptor, this.c.getValue(), output);
                    }
                    if (this.b.hasNext()) {
                        this.c = this.b.next();
                        continue;
                    }
                    this.c = null;
                }
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static interface e<MessageType extends d>
    extends aa {
        public <Type> boolean a(h<MessageType, Type> var1);

        public <Type> int b(h<MessageType, List<Type>> var1);

        public <Type> Type c(h<MessageType, Type> var1);

        public <Type> Type a(h<MessageType, List<Type>> var1, int var2);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class l1rpb.p$a<BuilderType extends l1rpb.p$a>
    extends a.a<BuilderType> {
        private b a;
        private a b;
        private boolean c;
        private ap d = ap.c();

        protected l1rpb.p$a() {
            this(null);
        }

        protected l1rpb.p$a(b builderParent) {
            this.a = builderParent;
        }

        void aA() {
            this.a = null;
        }

        protected void q_() {
            if (this.a != null) {
                this.r_();
            }
        }

        protected void r_() {
            this.c = true;
        }

        protected boolean s_() {
            return this.c;
        }

        public BuilderType ai() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        public BuilderType ah() {
            this.d = ap.c();
            this.t_();
            return (BuilderType)this;
        }

        protected abstract g l();

        @Override
        public k.a J() {
            return this.l().a;
        }

        @Override
        public Map<k.f, Object> a_() {
            return Collections.unmodifiableMap(this.k());
        }

        private Map<k.f, Object> k() {
            TreeMap<k.f, Object> result = new TreeMap<k.f, Object>();
            k.a descriptor = this.l().a;
            for (k.f field : descriptor.h()) {
                if (field.n()) {
                    List value = (List)this.b(field);
                    if (value.isEmpty()) continue;
                    result.put(field, value);
                    continue;
                }
                if (!this.a_(field)) continue;
                result.put(field, this.b(field));
            }
            return result;
        }

        @Override
        public x.a g(k.f field) {
            return this.l().a(field).a();
        }

        @Override
        public x.a a(k.f field) {
            return this.l().a(field).e(this);
        }

        @Override
        public boolean a_(k.f field) {
            return this.l().a(field).b(this);
        }

        @Override
        public Object b(k.f field) {
            Object object = this.l().a(field).a(this);
            if (field.n()) {
                return Collections.unmodifiableList((List)object);
            }
            return object;
        }

        public BuilderType a(k.f field, Object value) {
            this.l().a(field).a(this, value);
            return (BuilderType)this;
        }

        public BuilderType d(k.f field) {
            this.l().a(field).d(this);
            return (BuilderType)this;
        }

        @Override
        public int c(k.f field) {
            return this.l().a(field).c(this);
        }

        @Override
        public Object a(k.f field, int index) {
            return this.l().a(field).a(this, index);
        }

        public BuilderType a(k.f field, int index, Object value) {
            this.l().a(field).a(this, index, value);
            return (BuilderType)this;
        }

        public BuilderType b(k.f field, Object value) {
            this.l().a(field).b(this, value);
            return (BuilderType)this;
        }

        public final BuilderType c(ap unknownFields) {
            this.d = unknownFields;
            this.t_();
            return (BuilderType)this;
        }

        public final BuilderType d(ap unknownFields) {
            this.d = ap.a(this.d).a(unknownFields).b();
            this.t_();
            return (BuilderType)this;
        }

        @Override
        public boolean a() {
            for (k.f field : this.J().h()) {
                if (field.k() && !this.a_(field)) {
                    return false;
                }
                if (field.g() != k.f.a.i) continue;
                if (field.n()) {
                    List messageList = (List)this.b(field);
                    for (x element : messageList) {
                        if (element.a()) continue;
                        return false;
                    }
                    continue;
                }
                if (!this.a_(field) || ((x)this.b(field)).a()) continue;
                return false;
            }
            return true;
        }

        @Override
        public final ap b_() {
            return this.d;
        }

        protected boolean a(l1rpb.h input, ap.a unknownFields, n extensionRegistry, int tag) throws IOException {
            return unknownFields.a(tag, input);
        }

        protected b aE() {
            if (this.b == null) {
                this.b = new a();
            }
            return this.b;
        }

        protected final void t_() {
            if (this.c && this.a != null) {
                this.a.a();
                this.c = false;
            }
        }

        @Override
        public /* synthetic */ a.a a(ap x0) {
            return this.d(x0);
        }

        @Override
        public /* synthetic */ a.a e() {
            return this.ah();
        }

        @Override
        public /* synthetic */ a.a d() {
            return this.ai();
        }

        @Override
        public /* synthetic */ x.a b(ap x0) {
            return this.d(x0);
        }

        @Override
        public /* synthetic */ x.a e(ap x0) {
            return this.c(x0);
        }

        @Override
        public /* synthetic */ x.a c(k.f x0, Object x1) {
            return this.b(x0, x1);
        }

        @Override
        public /* synthetic */ x.a b(k.f x0, int x1, Object x2) {
            return this.a(x0, x1, x2);
        }

        @Override
        public /* synthetic */ x.a f(k.f x0) {
            return this.d(x0);
        }

        @Override
        public /* synthetic */ x.a d(k.f x0, Object x1) {
            return this.a(x0, x1);
        }

        @Override
        public /* synthetic */ x.a i() {
            return this.ai();
        }

        @Override
        public /* synthetic */ x.a j() {
            return this.ah();
        }

        @Override
        public /* synthetic */ y.a g() {
            return this.ai();
        }

        @Override
        public /* synthetic */ y.a h() {
            return this.ah();
        }

        @Override
        public /* synthetic */ b.a f() {
            return this.ai();
        }

        @Override
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return this.ai();
        }

        private class a
        implements b {
            private a() {
            }

            public void a() {
                a.this.t_();
            }
        }
    }

    protected static interface b {
        public void a();
    }
}

