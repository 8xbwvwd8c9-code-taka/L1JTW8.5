/*
 * Decompiled with CFR 0.152.
 */
package l1rpb;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import l1rpb.an;
import l1rpb.as;
import l1rpb.j;
import l1rpb.m;
import l1rpb.n;
import l1rpb.o;
import l1rpb.r;
import l1rpb.s;
import l1rpb.x;
import l1rpb.y;

public final class k {
    private static String b(g file, a parent, String name) {
        if (parent != null) {
            return parent.d() + '.' + name;
        }
        if (file.c().length() > 0) {
            return file.c() + '.' + name;
        }
        return name;
    }

    private static final class l1rpb.k$b {
        private final Set<g> b;
        private final Map<String, h> c = new HashMap<String, h>();
        private final Map<a, f> d = new HashMap<a, f>();
        private final Map<a, e> e = new HashMap<a, e>();

        l1rpb.k$b(g[] dependencies) {
            this.b = new HashSet<g>();
            for (int i2 = 0; i2 < dependencies.length; ++i2) {
                this.b.add(dependencies[i2]);
                this.a(dependencies[i2]);
            }
            for (g dependency : this.b) {
                try {
                    this.a(dependency.c(), dependency);
                }
                catch (l1rpb.k$c e2) {
                    assert (false);
                }
            }
        }

        private void a(g file) {
            for (g dependency : file.j()) {
                if (!this.b.add(dependency)) continue;
                this.a(dependency);
            }
        }

        h a(String fullName) {
            return this.a(fullName, c.c);
        }

        h a(String fullName, c filter) {
            h result = this.c.get(fullName);
            if (result != null && (filter == c.c || filter == c.a && this.a(result) || filter == c.b && this.b(result))) {
                return result;
            }
            for (g dependency : this.b) {
                result = ((g)dependency).h.c.get(fullName);
                if (result == null || filter != c.c && (filter != c.a || !this.a(result)) && (filter != c.b || !this.b(result))) continue;
                return result;
            }
            return null;
        }

        boolean a(h descriptor) {
            return descriptor instanceof l1rpb.k$a || descriptor instanceof d;
        }

        boolean b(h descriptor) {
            return descriptor instanceof l1rpb.k$a || descriptor instanceof d || descriptor instanceof b || descriptor instanceof j;
        }

        h a(String name, h relativeTo, c filter) throws l1rpb.k$c {
            h result;
            if (name.startsWith(".")) {
                result = this.a(name.substring(1), filter);
            } else {
                int firstPartLength = name.indexOf(46);
                String firstPart = firstPartLength == -1 ? name : name.substring(0, firstPartLength);
                StringBuilder scopeToTry = new StringBuilder(relativeTo.d());
                while (true) {
                    int dotpos;
                    if ((dotpos = scopeToTry.lastIndexOf(".")) == -1) {
                        result = this.a(name, filter);
                        break;
                    }
                    scopeToTry.setLength(dotpos + 1);
                    scopeToTry.append(firstPart);
                    result = this.a(scopeToTry.toString(), c.b);
                    if (result != null) {
                        if (firstPartLength == -1) break;
                        scopeToTry.setLength(dotpos + 1);
                        scopeToTry.append(name);
                        result = this.a(scopeToTry.toString(), filter);
                        break;
                    }
                    scopeToTry.setLength(dotpos);
                }
            }
            if (result == null) {
                throw new l1rpb.k$c(relativeTo, '\"' + name + "\" is not defined.");
            }
            return result;
        }

        void c(h descriptor) throws l1rpb.k$c {
            l1rpb.k$b.d(descriptor);
            String fullName = descriptor.d();
            int dotpos = fullName.lastIndexOf(46);
            h old = this.c.put(fullName, descriptor);
            if (old != null) {
                this.c.put(fullName, old);
                if (descriptor.e() == old.e()) {
                    if (dotpos == -1) {
                        throw new l1rpb.k$c(descriptor, '\"' + fullName + "\" is already defined.");
                    }
                    throw new l1rpb.k$c(descriptor, '\"' + fullName.substring(dotpos + 1) + "\" is already defined in \"" + fullName.substring(0, dotpos) + "\".");
                }
                throw new l1rpb.k$c(descriptor, '\"' + fullName + "\" is already defined in file \"" + old.e().b() + "\".");
            }
        }

        void a(String fullName, g file) throws l1rpb.k$c {
            String name;
            int dotpos = fullName.lastIndexOf(46);
            if (dotpos == -1) {
                name = fullName;
            } else {
                this.a(fullName.substring(0, dotpos), file);
                name = fullName.substring(dotpos + 1);
            }
            h old = this.c.put(fullName, new b(name, fullName, file));
            if (old != null) {
                this.c.put(fullName, old);
                if (!(old instanceof b)) {
                    throw new l1rpb.k$c(file, '\"' + name + "\" is already defined (as something other than a " + "package) in file \"" + old.e().b() + "\".");
                }
            }
        }

        void a(f field) throws l1rpb.k$c {
            a key = new a(field.u(), field.f());
            f old = this.d.put(key, field);
            if (old != null) {
                this.d.put(key, old);
                throw new l1rpb.k$c((h)field, "Field number " + field.f() + "has already been used in \"" + field.u().d() + "\" by field \"" + old.c() + "\".");
            }
        }

        void a(e value) {
            a key = new a(value.g(), value.a());
            e old = this.e.put(key, value);
            if (old != null) {
                this.e.put(key, old);
            }
        }

        static void d(h descriptor) throws l1rpb.k$c {
            String name = descriptor.c();
            if (name.length() == 0) {
                throw new l1rpb.k$c(descriptor, "Missing name.");
            }
            boolean valid = true;
            for (int i2 = 0; i2 < name.length(); ++i2) {
                char c2 = name.charAt(i2);
                if (c2 >= '\u0080') {
                    valid = false;
                }
                if (Character.isLetter(c2) || c2 == '_' || Character.isDigit(c2) && i2 > 0) continue;
                valid = false;
            }
            if (!valid) {
                throw new l1rpb.k$c(descriptor, '\"' + name + "\" is not a valid identifier.");
            }
        }

        private static final class a {
            private final h a;
            private final int b;

            a(h descriptor, int number) {
                this.a = descriptor;
                this.b = number;
            }

            public int hashCode() {
                return this.a.hashCode() * 65535 + this.b;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof a)) {
                    return false;
                }
                a other = (a)obj;
                return this.a == other.a && this.b == other.b;
            }
        }

        private static final class b
        implements h {
            private final String a;
            private final String b;
            private final g c;

            public x l() {
                return this.c.a();
            }

            public String c() {
                return this.a;
            }

            public String d() {
                return this.b;
            }

            public g e() {
                return this.c;
            }

            b(String name, String fullName, g file) {
                this.c = file;
                this.b = fullName;
                this.a = name;
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        static enum c {
            a,
            b,
            c;

        }
    }

    public static class c
    extends Exception {
        private static final long a = 5750205775490483148L;
        private final String b;
        private final x c;
        private final String d;

        public String a() {
            return this.b;
        }

        public x b() {
            return this.c;
        }

        public String c() {
            return this.d;
        }

        private c(h problemDescriptor, String description) {
            super(problemDescriptor.d() + ": " + description);
            this.b = problemDescriptor.d();
            this.c = problemDescriptor.l();
            this.d = description;
        }

        private c(h problemDescriptor, String description, Throwable cause) {
            this(problemDescriptor, description);
            this.initCause(cause);
        }

        private c(g problemDescriptor, String description) {
            super(problemDescriptor.b() + ": " + description);
            this.b = problemDescriptor.b();
            this.c = problemDescriptor.a();
            this.d = description;
        }
    }

    private static interface h {
        public x l();

        public String c();

        public String d();

        public g e();
    }

    public static final class i
    implements h {
        private final int a;
        private j.w b;
        private final String c;
        private final g d;
        private final j e;
        private a f;
        private a g;

        public int a() {
            return this.a;
        }

        public j.w b() {
            return this.b;
        }

        public String c() {
            return this.b.o();
        }

        public String d() {
            return this.c;
        }

        public g e() {
            return this.d;
        }

        public j f() {
            return this.e;
        }

        public a g() {
            return this.f;
        }

        public a h() {
            return this.g;
        }

        public j.y i() {
            return this.b.x();
        }

        private i(j.w proto, g file, j parent, int index) throws c {
            this.a = index;
            this.b = proto;
            this.d = file;
            this.e = parent;
            this.c = parent.d() + '.' + proto.o();
            file.h.c(this);
        }

        private void j() throws c {
            h input = this.d.h.a(this.b.r(), this, b.c.a);
            if (!(input instanceof a)) {
                throw new c((h)this, '\"' + this.b.r() + "\" is not a message type.");
            }
            this.f = (a)input;
            h output = this.d.h.a(this.b.u(), this, b.c.a);
            if (!(output instanceof a)) {
                throw new c((h)this, '\"' + this.b.u() + "\" is not a message type.");
            }
            this.g = (a)output;
        }

        private void a(j.w proto) {
            this.b = proto;
        }

        public /* synthetic */ x l() {
            return this.b();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class j
    implements h {
        private final int a;
        private j.aa b;
        private final String c;
        private final g d;
        private i[] e;

        public int a() {
            return this.a;
        }

        public j.aa b() {
            return this.b;
        }

        @Override
        public String c() {
            return this.b.o();
        }

        @Override
        public String d() {
            return this.c;
        }

        @Override
        public g e() {
            return this.d;
        }

        public j.ac f() {
            return this.b.u();
        }

        public List<i> g() {
            return Collections.unmodifiableList(Arrays.asList(this.e));
        }

        public i a(String name) {
            h result = this.d.h.a(this.c + '.' + name);
            if (result != null && result instanceof i) {
                return (i)result;
            }
            return null;
        }

        private j(j.aa proto, g file, int index) throws c {
            this.a = index;
            this.b = proto;
            this.c = k.b(file, null, proto.o());
            this.d = file;
            this.e = new i[proto.s()];
            for (int i2 = 0; i2 < proto.s(); ++i2) {
                this.e[i2] = new i(proto.a(i2), file, this, i2);
            }
            file.h.c(this);
        }

        private void h() throws c {
            for (i method : this.e) {
                method.j();
            }
        }

        private void a(j.aa proto) {
            this.b = proto;
            for (int i2 = 0; i2 < this.e.length; ++i2) {
                this.e[i2].a(proto.a(i2));
            }
        }

        @Override
        public /* synthetic */ x l() {
            return this.b();
        }
    }

    public static final class e
    implements h,
    r.a {
        private final int a;
        private j.g b;
        private final String c;
        private final g d;
        private final d e;

        public int b() {
            return this.a;
        }

        public j.g f() {
            return this.b;
        }

        public String c() {
            return this.b.o();
        }

        public int a() {
            return this.b.r();
        }

        public String d() {
            return this.c;
        }

        public g e() {
            return this.d;
        }

        public d g() {
            return this.e;
        }

        public j.i h() {
            return this.b.t();
        }

        private e(j.g proto, g file, d parent, int index) throws c {
            this.a = index;
            this.b = proto;
            this.d = file;
            this.e = parent;
            this.c = parent.d() + '.' + proto.o();
            file.h.c(this);
            file.h.a(this);
        }

        private void a(j.g proto) {
            this.b = proto;
        }

        public /* synthetic */ x l() {
            return this.f();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class d
    implements h,
    r.b<e> {
        private final int a;
        private j.c b;
        private final String c;
        private final g d;
        private final a e;
        private e[] f;

        public int a() {
            return this.a;
        }

        public j.c b() {
            return this.b;
        }

        @Override
        public String c() {
            return this.b.o();
        }

        @Override
        public String d() {
            return this.c;
        }

        @Override
        public g e() {
            return this.d;
        }

        public a f() {
            return this.e;
        }

        public j.e g() {
            return this.b.u();
        }

        public List<e> h() {
            return Collections.unmodifiableList(Arrays.asList(this.f));
        }

        public e a(String name) {
            h result = this.d.h.a(this.c + '.' + name);
            if (result != null && result instanceof e) {
                return (e)result;
            }
            return null;
        }

        public e a(int number) {
            return (e)this.d.h.e.get(new b.a(this, number));
        }

        private d(j.c proto, g file, a parent, int index) throws c {
            this.a = index;
            this.b = proto;
            this.c = k.b(file, parent, proto.o());
            this.d = file;
            this.e = parent;
            if (proto.s() == 0) {
                throw new c((h)this, "Enums must contain at least one value.");
            }
            this.f = new e[proto.s()];
            for (int i2 = 0; i2 < proto.s(); ++i2) {
                this.f[i2] = new e(proto.a(i2), file, this, i2);
            }
            file.h.c(this);
        }

        private void a(j.c proto) {
            this.b = proto;
            for (int i2 = 0; i2 < this.f.length; ++i2) {
                this.f[i2].a(proto.a(i2));
            }
        }

        @Override
        public /* synthetic */ x l() {
            return this.b();
        }

        @Override
        public /* synthetic */ r.a b(int x0) {
            return this.a(x0);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class f
    implements h,
    o.a<f>,
    Comparable<f> {
        private static final as.a[] a = as.a.values();
        private final int b;
        private j.k c;
        private final String d;
        private final g e;
        private final l1rpb.k$a f;
        private b g;
        private l1rpb.k$a h;
        private l1rpb.k$a i;
        private d j;
        private Object k;

        public int a() {
            return this.b;
        }

        public j.k b() {
            return this.c;
        }

        @Override
        public String c() {
            return this.c.o();
        }

        @Override
        public int f() {
            return this.c.r();
        }

        @Override
        public String d() {
            return this.d;
        }

        public a g() {
            return this.g.b();
        }

        @Override
        public as.b h() {
            return this.j().a();
        }

        @Override
        public g e() {
            return this.e;
        }

        public b i() {
            return this.g;
        }

        @Override
        public as.a j() {
            return a[this.g.ordinal()];
        }

        public boolean k() {
            return this.c.t() == j.k.b.b;
        }

        public boolean m() {
            return this.c.t() == j.k.b.a;
        }

        @Override
        public boolean n() {
            return this.c.t() == j.k.b.c;
        }

        @Override
        public boolean o() {
            return this.s().q();
        }

        public boolean p() {
            return this.n() && this.j().c();
        }

        public boolean q() {
            return this.c.C();
        }

        public Object r() {
            if (this.g() == a.i) {
                throw new UnsupportedOperationException("FieldDescriptor.getDefaultValue() called on an embedded message field.");
            }
            return this.k;
        }

        public j.m s() {
            return this.c.G();
        }

        public boolean t() {
            return this.c.z();
        }

        public l1rpb.k$a u() {
            return this.h;
        }

        public l1rpb.k$a v() {
            if (!this.t()) {
                throw new UnsupportedOperationException("This field is not an extension.");
            }
            return this.f;
        }

        public l1rpb.k$a w() {
            if (this.g() != a.i) {
                throw new UnsupportedOperationException("This field is not of message type.");
            }
            return this.i;
        }

        public d x() {
            if (this.g() != a.h) {
                throw new UnsupportedOperationException("This field is not of enum type.");
            }
            return this.j;
        }

        public int a(f other) {
            if (other.h != this.h) {
                throw new IllegalArgumentException("FieldDescriptors can only be compared to other FieldDescriptors for fields of the same message type.");
            }
            return this.f() - other.f();
        }

        private f(j.k proto, g file, l1rpb.k$a parent, int index, boolean isExtension) throws c {
            this.b = index;
            this.c = proto;
            this.d = l1rpb.k.b(file, parent, proto.o());
            this.e = file;
            if (proto.u()) {
                this.g = b.a(proto.v());
            }
            if (this.f() <= 0) {
                throw new c((h)this, "Field numbers must be positive integers.");
            }
            if (proto.G().q() && !this.p()) {
                throw new c((h)this, "[packed = true] can only be specified for repeated primitive fields.");
            }
            if (isExtension) {
                if (!proto.z()) {
                    throw new c((h)this, "FieldDescriptorProto.extendee not set for extension field.");
                }
                this.h = null;
                this.f = parent != null ? parent : null;
            } else {
                if (proto.z()) {
                    throw new c((h)this, "FieldDescriptorProto.extendee set for non-extension field.");
                }
                this.h = parent;
                this.f = null;
            }
            file.h.c(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void z() throws c {
            if (this.c.z()) {
                h extendee = this.e.h.a(this.c.A(), this, b.c.a);
                if (!(extendee instanceof l1rpb.k$a)) {
                    throw new c((h)this, '\"' + this.c.A() + "\" is not a message type.");
                }
                this.h = (l1rpb.k$a)extendee;
                if (!this.u().a(this.f())) {
                    throw new c((h)this, '\"' + this.u().d() + "\" does not declare " + this.f() + " as an extension number.");
                }
            }
            if (this.c.w()) {
                h typeDescriptor = this.e.h.a(this.c.x(), this, b.c.a);
                if (!this.c.u()) {
                    if (typeDescriptor instanceof l1rpb.k$a) {
                        this.g = b.k;
                    } else {
                        if (!(typeDescriptor instanceof d)) throw new c((h)this, '\"' + this.c.x() + "\" is not a type.");
                        this.g = b.n;
                    }
                }
                if (this.g() == a.i) {
                    if (!(typeDescriptor instanceof l1rpb.k$a)) {
                        throw new c((h)this, '\"' + this.c.x() + "\" is not a message type.");
                    }
                    this.i = (l1rpb.k$a)typeDescriptor;
                    if (this.c.C()) {
                        throw new c((h)this, "Messages can't have default values.");
                    }
                } else {
                    if (this.g() != a.h) throw new c((h)this, "Field with primitive type has type_name.");
                    if (!(typeDescriptor instanceof d)) {
                        throw new c((h)this, '\"' + this.c.x() + "\" is not an enum type.");
                    }
                    this.j = (d)typeDescriptor;
                }
            } else if (this.g() == a.i || this.g() == a.h) {
                throw new c((h)this, "Field with message or enum type missing type_name.");
            }
            if (this.c.C()) {
                if (this.n()) {
                    throw new c((h)this, "Repeated fields cannot have default values.");
                }
                try {
                    switch (this.i()) {
                        case e: 
                        case q: 
                        case o: {
                            this.k = an.c(this.c.D());
                            break;
                        }
                        case m: 
                        case g: {
                            this.k = an.d(this.c.D());
                            break;
                        }
                        case c: 
                        case r: 
                        case p: {
                            this.k = an.e(this.c.D());
                            break;
                        }
                        case d: 
                        case f: {
                            this.k = an.f(this.c.D());
                            break;
                        }
                        case b: {
                            if (this.c.D().equals("inf")) {
                                this.k = Float.valueOf(Float.POSITIVE_INFINITY);
                                break;
                            }
                            if (this.c.D().equals("-inf")) {
                                this.k = Float.valueOf(Float.NEGATIVE_INFINITY);
                                break;
                            }
                            if (this.c.D().equals("nan")) {
                                this.k = Float.valueOf(Float.NaN);
                                break;
                            }
                            this.k = Float.valueOf(this.c.D());
                            break;
                        }
                        case a: {
                            if (this.c.D().equals("inf")) {
                                this.k = Double.POSITIVE_INFINITY;
                                break;
                            }
                            if (this.c.D().equals("-inf")) {
                                this.k = Double.NEGATIVE_INFINITY;
                                break;
                            }
                            if (this.c.D().equals("nan")) {
                                this.k = Double.NaN;
                                break;
                            }
                            this.k = Double.valueOf(this.c.D());
                            break;
                        }
                        case h: {
                            this.k = Boolean.valueOf(this.c.D());
                            break;
                        }
                        case i: {
                            this.k = this.c.D();
                            break;
                        }
                        case l: {
                            try {
                                this.k = an.a((CharSequence)this.c.D());
                                break;
                            }
                            catch (an.a e2) {
                                throw new c(this, "Couldn't parse default value: " + e2.getMessage(), e2);
                            }
                        }
                        case n: {
                            this.k = this.j.a(this.c.D());
                            if (this.k != null) break;
                            throw new c((h)this, "Unknown enum default value: \"" + this.c.D() + '\"');
                        }
                        case k: 
                        case j: {
                            throw new c((h)this, "Message type had default value.");
                        }
                    }
                }
                catch (NumberFormatException e3) {
                    throw new c(this, "Could not parse default value: \"" + this.c.D() + '\"', e3);
                }
            } else if (this.n()) {
                this.k = Collections.emptyList();
            } else {
                switch (this.g()) {
                    case h: {
                        this.k = this.j.h().get(0);
                        break;
                    }
                    case i: {
                        this.k = null;
                        break;
                    }
                    default: {
                        this.k = this.g().j;
                    }
                }
            }
            if (!this.t()) {
                this.e.h.a(this);
            }
            if (this.h == null || !this.h.g().o()) return;
            if (!this.t()) throw new c((h)this, "MessageSets cannot have fields, only extensions.");
            if (this.m() && this.i() == b.k) return;
            throw new c((h)this, "Extensions of MessageSets must be optional messages.");
        }

        private void a(j.k proto) {
            this.c = proto;
        }

        @Override
        public y.a a(y.a to, y from) {
            return ((x.a)to).c((x)from);
        }

        @Override
        public /* synthetic */ x l() {
            return this.b();
        }

        @Override
        public /* synthetic */ int compareTo(Object x0) {
            return this.a((f)x0);
        }

        @Override
        public /* synthetic */ r.b y() {
            return this.x();
        }

        static {
            if (b.values().length != j.k.c.values().length) {
                throw new RuntimeException("descriptor.proto has a new declared type but Desrciptors.java wasn't updated.");
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        public static enum a {
            a(0),
            b(0L),
            c(Float.valueOf(0.0f)),
            d(0.0),
            e(false),
            f(""),
            g(l1rpb.g.d),
            h(null),
            i(null);

            private final Object j;

            private a(Object defaultDefault) {
                this.j = defaultDefault;
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        public static enum b {
            a(l1rpb.k$f$a.d),
            b(l1rpb.k$f$a.c),
            c(l1rpb.k$f$a.b),
            d(l1rpb.k$f$a.b),
            e(l1rpb.k$f$a.a),
            f(l1rpb.k$f$a.b),
            g(l1rpb.k$f$a.a),
            h(l1rpb.k$f$a.e),
            i(l1rpb.k$f$a.f),
            j(l1rpb.k$f$a.i),
            k(l1rpb.k$f$a.i),
            l(l1rpb.k$f$a.g),
            m(l1rpb.k$f$a.a),
            n(l1rpb.k$f$a.h),
            o(l1rpb.k$f$a.a),
            p(l1rpb.k$f$a.b),
            q(l1rpb.k$f$a.a),
            r(l1rpb.k$f$a.b);

            private a s;

            private b(a javaType) {
                this.s = javaType;
            }

            public j.k.c a() {
                return j.k.c.a(this.ordinal() + 1);
            }

            public a b() {
                return this.s;
            }

            public static b a(j.k.c type) {
                return l1rpb.k$f$b.values()[type.a() - 1];
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class a
    implements h {
        private final int a;
        private j.a b;
        private final String c;
        private final g d;
        private final a e;
        private final a[] f;
        private final d[] g;
        private final f[] h;
        private final f[] i;

        public int a() {
            return this.a;
        }

        public j.a b() {
            return this.b;
        }

        @Override
        public String c() {
            return this.b.o();
        }

        @Override
        public String d() {
            return this.c;
        }

        @Override
        public g e() {
            return this.d;
        }

        public a f() {
            return this.e;
        }

        public j.u g() {
            return this.b.G();
        }

        public List<f> h() {
            return Collections.unmodifiableList(Arrays.asList(this.h));
        }

        public List<f> i() {
            return Collections.unmodifiableList(Arrays.asList(this.i));
        }

        public List<a> j() {
            return Collections.unmodifiableList(Arrays.asList(this.f));
        }

        public List<d> k() {
            return Collections.unmodifiableList(Arrays.asList(this.g));
        }

        public boolean a(int number) {
            for (j.a.b range : this.b.C()) {
                if (range.o() > number || number >= range.q()) continue;
                return true;
            }
            return false;
        }

        public f a(String name) {
            h result = this.d.h.a(this.c + '.' + name);
            if (result != null && result instanceof f) {
                return (f)result;
            }
            return null;
        }

        public f b(int number) {
            return (f)this.d.h.d.get(new b.a(this, number));
        }

        public a b(String name) {
            h result = this.d.h.a(this.c + '.' + name);
            if (result != null && result instanceof a) {
                return (a)result;
            }
            return null;
        }

        public d c(String name) {
            h result = this.d.h.a(this.c + '.' + name);
            if (result != null && result instanceof d) {
                return (d)result;
            }
            return null;
        }

        private a(j.a proto, g file, a parent, int index) throws c {
            int i2;
            this.a = index;
            this.b = proto;
            this.c = k.b(file, parent, proto.o());
            this.d = file;
            this.e = parent;
            this.f = new a[proto.y()];
            for (i2 = 0; i2 < proto.y(); ++i2) {
                this.f[i2] = new a(proto.e(i2), file, this, i2);
            }
            this.g = new d[proto.B()];
            for (i2 = 0; i2 < proto.B(); ++i2) {
                this.g[i2] = new d(proto.g(i2), file, this, i2);
            }
            this.h = new f[proto.s()];
            for (i2 = 0; i2 < proto.s(); ++i2) {
                this.h[i2] = new f(proto.a(i2), file, this, i2, false);
            }
            this.i = new f[proto.v()];
            for (i2 = 0; i2 < proto.v(); ++i2) {
                this.i[i2] = new f(proto.c(i2), file, this, i2, true);
            }
            file.h.c(this);
        }

        private void m() throws c {
            for (a a2 : this.f) {
                a2.m();
            }
            for (h h2 : this.h) {
                ((f)h2).z();
            }
            for (h h3 : this.i) {
                ((f)h3).z();
            }
        }

        private void a(j.a proto) {
            int i2;
            this.b = proto;
            for (i2 = 0; i2 < this.f.length; ++i2) {
                this.f[i2].a(proto.e(i2));
            }
            for (i2 = 0; i2 < this.g.length; ++i2) {
                this.g[i2].a(proto.g(i2));
            }
            for (i2 = 0; i2 < this.h.length; ++i2) {
                this.h[i2].a(proto.a(i2));
            }
            for (i2 = 0; i2 < this.i.length; ++i2) {
                this.i[i2].a(proto.c(i2));
            }
        }

        @Override
        public /* synthetic */ x l() {
            return this.b();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class g {
        private j.o a;
        private final l1rpb.k$a[] b;
        private final d[] c;
        private final j[] d;
        private final f[] e;
        private final g[] f;
        private final g[] g;
        private final b h;

        public j.o a() {
            return this.a;
        }

        public String b() {
            return this.a.o();
        }

        public String c() {
            return this.a.r();
        }

        public j.s d() {
            return this.a.U();
        }

        public List<l1rpb.k$a> e() {
            return Collections.unmodifiableList(Arrays.asList(this.b));
        }

        public List<d> f() {
            return Collections.unmodifiableList(Arrays.asList(this.c));
        }

        public List<j> g() {
            return Collections.unmodifiableList(Arrays.asList(this.d));
        }

        public List<f> h() {
            return Collections.unmodifiableList(Arrays.asList(this.e));
        }

        public List<g> i() {
            return Collections.unmodifiableList(Arrays.asList(this.f));
        }

        public List<g> j() {
            return Collections.unmodifiableList(Arrays.asList(this.g));
        }

        public l1rpb.k$a a(String name) {
            h result;
            if (name.indexOf(46) != -1) {
                return null;
            }
            if (this.c().length() > 0) {
                name = this.c() + '.' + name;
            }
            if ((result = this.h.a(name)) != null && result instanceof l1rpb.k$a && result.e() == this) {
                return (l1rpb.k$a)result;
            }
            return null;
        }

        public d b(String name) {
            h result;
            if (name.indexOf(46) != -1) {
                return null;
            }
            if (this.c().length() > 0) {
                name = this.c() + '.' + name;
            }
            if ((result = this.h.a(name)) != null && result instanceof d && result.e() == this) {
                return (d)result;
            }
            return null;
        }

        public j c(String name) {
            h result;
            if (name.indexOf(46) != -1) {
                return null;
            }
            if (this.c().length() > 0) {
                name = this.c() + '.' + name;
            }
            if ((result = this.h.a(name)) != null && result instanceof j && result.e() == this) {
                return (j)result;
            }
            return null;
        }

        public f d(String name) {
            h result;
            if (name.indexOf(46) != -1) {
                return null;
            }
            if (this.c().length() > 0) {
                name = this.c() + '.' + name;
            }
            if ((result = this.h.a(name)) != null && result instanceof f && result.e() == this) {
                return (f)result;
            }
            return null;
        }

        public static g a(j.o proto, g[] dependencies) throws c {
            b pool = new b(dependencies);
            g result = new g(proto, dependencies, pool);
            if (dependencies.length != proto.u()) {
                throw new c(result, "Dependencies passed to FileDescriptor.buildFrom() don't match those listed in the FileDescriptorProto.");
            }
            for (int i2 = 0; i2 < proto.u(); ++i2) {
                if (dependencies[i2].b().equals(proto.a(i2))) continue;
                throw new c(result, "Dependencies passed to FileDescriptor.buildFrom() don't match those listed in the FileDescriptorProto.");
            }
            result.k();
            return result;
        }

        public static void a(String[] descriptorDataParts, g[] dependencies, a descriptorAssigner) {
            g result;
            j.o proto;
            byte[] descriptorBytes;
            StringBuilder descriptorData = new StringBuilder();
            for (String part : descriptorDataParts) {
                descriptorData.append(part);
            }
            try {
                descriptorBytes = descriptorData.toString().getBytes("ISO-8859-1");
            }
            catch (UnsupportedEncodingException e2) {
                throw new RuntimeException("Standard encoding ISO-8859-1 not supported by JVM.", e2);
            }
            try {
                proto = j.o.a(descriptorBytes);
            }
            catch (s e3) {
                throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e3);
            }
            try {
                result = l1rpb.k$g.a(proto, dependencies);
            }
            catch (c e4) {
                throw new IllegalArgumentException("Invalid embedded descriptor for \"" + proto.o() + "\".", e4);
            }
            m registry = descriptorAssigner.a(result);
            if (registry != null) {
                try {
                    proto = j.o.a(descriptorBytes, (n)registry);
                }
                catch (s e5) {
                    throw new IllegalArgumentException("Failed to parse protocol buffer descriptor for generated code.", e5);
                }
                result.a(proto);
            }
        }

        private g(j.o proto, g[] dependencies, b pool) throws c {
            int i2;
            this.h = pool;
            this.a = proto;
            this.f = (g[])dependencies.clone();
            this.g = new g[proto.w()];
            for (i2 = 0; i2 < proto.w(); ++i2) {
                int index = proto.c(i2);
                if (index < 0 || index >= this.f.length) {
                    throw new c(this, "Invalid public dependency index.");
                }
                this.g[i2] = this.f[proto.c(i2)];
            }
            pool.a(this.c(), this);
            this.b = new l1rpb.k$a[proto.B()];
            for (i2 = 0; i2 < proto.B(); ++i2) {
                this.b[i2] = new l1rpb.k$a(proto.e(i2), this, null, i2);
            }
            this.c = new d[proto.E()];
            for (i2 = 0; i2 < proto.E(); ++i2) {
                this.c[i2] = new d(proto.g(i2), this, null, i2);
            }
            this.d = new j[proto.H()];
            for (i2 = 0; i2 < proto.H(); ++i2) {
                this.d[i2] = new j(proto.i(i2), this, i2);
            }
            this.e = new f[proto.S()];
            for (i2 = 0; i2 < proto.S(); ++i2) {
                this.e[i2] = new f(proto.k(i2), this, null, i2, true);
            }
        }

        private void k() throws c {
            for (l1rpb.k$a a2 : this.b) {
                a2.m();
            }
            for (h h2 : this.d) {
                ((j)h2).h();
            }
            for (h h3 : this.e) {
                ((f)h3).z();
            }
        }

        private void a(j.o proto) {
            int i2;
            this.a = proto;
            for (i2 = 0; i2 < this.b.length; ++i2) {
                this.b[i2].a(proto.e(i2));
            }
            for (i2 = 0; i2 < this.c.length; ++i2) {
                this.c[i2].a(proto.g(i2));
            }
            for (i2 = 0; i2 < this.d.length; ++i2) {
                this.d[i2].a(proto.i(i2));
            }
            for (i2 = 0; i2 < this.e.length; ++i2) {
                this.e[i2].a(proto.k(i2));
            }
        }

        public static interface a {
            public m a(g var1);
        }
    }
}

