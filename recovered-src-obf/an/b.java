/*
 * Decompiled with CFR 0.152.
 */
package an;

import a.a;
import a.aa;
import a.ab;
import a.ap;
import a.k;
import a.m;
import a.n;
import a.p;
import a.s;
import a.x;
import a.y;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class b {
    private static k.a a;
    private static p.g b;
    private static k.a c;
    private static p.g d;
    private static k.a e;
    private static p.g f;
    private static k.a g;
    private static p.g h;
    private static k.a i;
    private static p.g j;
    private static k.g k;

    static {
        String[] descriptorData = new String[]{"\n\u0013PBMessageALL2.proto\u0012 l1j.server.server.datas.protobuf\"`\n\tLuckyDraw\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\"1\n\rLuckyDrawRead\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0003(\u0005\"\u00e9\u0004\n\ftypeCharPack\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\r\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007array_9\u0018\t \u0001(\f\u0012\u0010\n\barray_10\u0018\n \u0001(\f\u0012\u0010\n\bvalu", "e_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\bvalue_18\u0018\u0012 \u0001(\u0005\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\barray_20\u0018\u0014 \u0001(\f\u0012\u0010\n\barray_21\u0018\u0015 \u0001(\f\u0012\u0010\n\bvalue_22\u0018\u0016 \u0001(\u0005\u0012\u0010\n\bvalue_23\u0018\u0017 \u0001(\u0005\u0012\u0010\n\bvalue_24\u0018\u0018 \u0001(\u0005\u0012\u0010\n\barray_25\u0018\u0019 \u0001(\f\u0012\u0010\n\bvalue_26\u0018\u001a \u0001(\u0005\u0012\u0010\n\bvalue_27\u0018\u001b \u0001(\u0005\u0012\u0010\n\bvalue_28\u0018\u001c \u0001(\u0005\u0012\u0010\n\bvalue_29\u0018\u001d \u0001(\u0005\u0012\u0010\n\bvalue_30\u0018\u001e \u0001(\u0005\u0012\u0010\n\bvalue_31\u0018\u001f \u0001(\u0005\u0012\u0010\n\bvalue_32\u0018  \u0001(\u0005\u0012\u0010\n\bvalue_33", "\u0018! \u0001(\u0005\u0012\u0010\n\bvalue_34\u0018\" \u0001(\u0005\"\u00c5\u0004\n\ftypeItemInfo\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007array_4\u0018\u0004 \u0001(\f\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0003(\u0005\u0012\u0010\n\barray_11\u0018\u000b \u0003(\f\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\bvalue_18\u0018\u0012 \u0001(\u0005\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\bvalue_20\u0018\u0014 \u0001(\u0005\u0012\u0010\n\bvalu", "e_21\u0018\u0015 \u0001(\u0005\u0012\u0010\n\bvalue_22\u0018\u0016 \u0001(\u0005\u0012\u0010\n\bvalue_23\u0018\u0017 \u0001(\u0005\u0012\u0010\n\bvalue_24\u0018\u0018 \u0001(\u0005\u0012\u0010\n\bvalue_25\u0018\u0019 \u0001(\u0005\u0012\u0010\n\bvalue_26\u0018\u001a \u0001(\u0005\u0012\u0010\n\bvalue_27\u0018\u001b \u0001(\u0005\u0012\u0010\n\bvalue_28\u0018\u001c \u0001(\u0005\u0012\u0010\n\bvalue_29\u0018\u001d \u0001(\u0005\u0012\u0010\n\bvalue_30\u0018\u001e \u0001(\u0005\u0012\u0010\n\bvalue_31\u0018\u001f \u0001(\u0005\u0012\u0010\n\bvalue_32\u0018  \u0001(\u0005\"\u00fe\u0002\n\u000btypeNpcInfo\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0001(\f\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bv", "alue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\barray_18\u0018\u0012 \u0001(\f\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\u0012\u0010\n\bvalue_20\u0018\u0014 \u0001(\u0005\u0012\u0010\n\barray_21\u0018\u0015 \u0003(\fB1\n l1j.server.server.datas.protobufB\rPBMessageALL2"};
        k.g.a assigner = new k.g.a(){

            @Override
            public m a(k.g root) {
                k = root;
                a = an.b.a().e().get(0);
                b = new p.g(a, new String[]{"Value1", "Value2", "Value3", "Value4", "Array5"});
                c = an.b.a().e().get(1);
                d = new p.g(c, new String[]{"Value1", "Value2"});
                e = an.b.a().e().get(2);
                f = new p.g(e, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Array9", "Array10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19", "Array20", "Array21", "Value22", "Value23", "Value24", "Array25", "Value26", "Value27", "Value28", "Value29", "Value30", "Value31", "Value32", "Value33", "Value34"});
                g = an.b.a().e().get(3);
                h = new p.g(g, new String[]{"Value1", "Value2", "Value3", "Array4", "Array5", "Value6", "Value7", "Value8", "Value9", "Value10", "Array11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19", "Value20", "Value21", "Value22", "Value23", "Value24", "Value25", "Value26", "Value27", "Value28", "Value29", "Value30", "Value31", "Value32"});
                i = an.b.a().e().get(4);
                j = new p.g(i, new String[]{"Value1", "Value2", "Array3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Array18", "Value19", "Value20", "Array21"});
                return null;
            }
        };
        k.g.a(descriptorData, new k.g[0], assigner);
    }

    private b() {
    }

    public static void a(m registry) {
    }

    public static k.g a() {
        return k;
    }

    public static final class an.b$a
    extends p
    implements b {
        private static final an.b$a g;
        private final ap h;
        public static ab<an.b$a> a;
        private int i;
        public static final int b = 1;
        private int j;
        public static final int c = 2;
        private int k;
        public static final int d = 3;
        private int l;
        public static final int e = 4;
        private int n;
        public static final int f = 5;
        private a.g o;
        private byte p = (byte)-1;
        private int q = -1;
        private static final long r = 0L;

        static {
            a = new a.c<an.b$a>(){

                public an.b$a c(a.h input, n extensionRegistry) throws s {
                    return new an.b$a(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            g = new an.b$a(true);
            g.C();
        }

        private an.b$a(p.a<?> builder) {
            super(builder);
            this.h = builder.b_();
        }

        private an.b$a(boolean noInit) {
            this.h = ap.c();
        }

        public static an.b$a h() {
            return g;
        }

        public an.b$a k() {
            return g;
        }

        @Override
        public final ap b_() {
            return this.h;
        }

        private an.b$a(a.h input, n extensionRegistry) throws s {
            this.C();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block14: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block14;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.i |= 1;
                                this.j = input.g();
                                break;
                            }
                            case 16: {
                                this.i |= 2;
                                this.k = input.g();
                                break;
                            }
                            case 24: {
                                this.i |= 4;
                                this.l = input.g();
                                break;
                            }
                            case 32: {
                                this.i |= 8;
                                this.n = input.g();
                                break;
                            }
                            case 42: {
                                this.i |= 0x10;
                                this.o = input.l();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                this.h = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return a;
        }

        @Override
        protected p.g l() {
            return b.a(an.b$a.class, a.class);
        }

        public ab<an.b$a> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.i & 1) == 1;
        }

        @Override
        public int p() {
            return this.j;
        }

        @Override
        public boolean q() {
            return (this.i & 2) == 2;
        }

        @Override
        public int r() {
            return this.k;
        }

        @Override
        public boolean s() {
            return (this.i & 4) == 4;
        }

        @Override
        public int t() {
            return this.l;
        }

        @Override
        public boolean u() {
            return (this.i & 8) == 8;
        }

        @Override
        public int v() {
            return this.n;
        }

        @Override
        public boolean w() {
            return (this.i & 0x10) == 16;
        }

        @Override
        public a.g x() {
            return this.o;
        }

        private void C() {
            this.j = 0;
            this.k = 0;
            this.l = 0;
            this.n = 0;
            this.o = a.g.d;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.p;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.p = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.i & 1) == 1) {
                output.a(1, this.j);
            }
            if ((this.i & 2) == 2) {
                output.a(2, this.k);
            }
            if ((this.i & 4) == 4) {
                output.a(3, this.l);
            }
            if ((this.i & 8) == 8) {
                output.a(4, this.n);
            }
            if ((this.i & 0x10) == 16) {
                output.a(5, this.o);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.q;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.i & 1) == 1) {
                size += a.i.g(1, this.j);
            }
            if ((this.i & 2) == 2) {
                size += a.i.g(2, this.k);
            }
            if ((this.i & 4) == 4) {
                size += a.i.g(3, this.l);
            }
            if ((this.i & 8) == 8) {
                size += a.i.g(4, this.n);
            }
            if ((this.i & 0x10) == 16) {
                size += a.i.c(5, this.o);
            }
            this.q = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static an.b$a a(a.g data) throws s {
            return a.d(data);
        }

        public static an.b$a a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.b$a a(byte[] data) throws s {
            return a.d(data);
        }

        public static an.b$a a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.b$a a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static an.b$a a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static an.b$a b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static an.b$a b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static an.b$a a(a.h input) throws IOException {
            return a.d(input);
        }

        public static an.b$a a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a y() {
            return a.I();
        }

        public a z() {
            return an.b$a.y();
        }

        public static a a(an.b$a prototype) {
            return an.b$a.y().a(prototype);
        }

        public a A() {
            return an.b$a.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.z();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.z();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.A();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.A();
        }

        public static final class a
        extends p.a<a>
        implements b {
            private int a;
            private int b;
            private int c;
            private int d;
            private int e;
            private a.g f = a.g.d;

            public static final k.a k() {
                return a;
            }

            @Override
            protected p.g l() {
                return b.a(an.b$a.class, a.class);
            }

            private a() {
                this.H();
            }

            private a(p.b parent) {
                super(parent);
                this.H();
            }

            private void H() {
                m;
            }

            private static a I() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = a.g.d;
                this.a &= 0xFFFFFFEF;
                return this;
            }

            public a n() {
                return an.b$a$a.I().a(this.A());
            }

            @Override
            public k.a J() {
                return a;
            }

            public an.b$a y() {
                return an.b$a.h();
            }

            public an.b$a z() {
                an.b$a result = this.A();
                if (!result.a()) {
                    throw an.b$a$a.b(result);
                }
                return result;
            }

            public an.b$a A() {
                an.b$a result = new an.b$a(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.j = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.k = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.l = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.n = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.o = this.f;
                result.i = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof an.b$a) {
                    return this.a((an.b$a)other);
                }
                super.a(other);
                return this;
            }

            public a a(an.b$a other) {
                if (other == an.b$a.h()) {
                    return this;
                }
                if (other.o()) {
                    this.a(other.p());
                }
                if (other.q()) {
                    this.b(other.r());
                }
                if (other.s()) {
                    this.c(other.t());
                }
                if (other.u()) {
                    this.d(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                an.b$a parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (an.b$a)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a a(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a B() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.c;
            }

            public a b(int value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a C() {
                this.a &= 0xFFFFFFFD;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public int t() {
                return this.d;
            }

            public a c(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a D() {
                this.a &= 0xFFFFFFFB;
                this.d = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.e;
            }

            public a d(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a E() {
                this.a &= 0xFFFFFFF7;
                this.e = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public a.g x() {
                return this.f;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a F() {
                this.a &= 0xFFFFFFEF;
                this.f = an.b$a.h().x();
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.A();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.A();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.y();
            }

            @Override
            public /* synthetic */ x R() {
                return this.y();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.z();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.z();
            }
        }
    }

    public static interface b
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public int t();

        public boolean u();

        public int v();

        public boolean w();

        public a.g x();
    }

    public static final class c
    extends p
    implements d {
        private static final c d;
        private final ap e;
        public static ab<c> a;
        private int f;
        public static final int b = 1;
        private int g;
        public static final int c = 2;
        private List<Integer> h;
        private byte i = (byte)-1;
        private int j = -1;
        private static final long k = 0L;

        static {
            a = new a.c<c>(){

                public c c(a.h input, n extensionRegistry) throws s {
                    return new c(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            d = new c(true);
            d.w();
        }

        private c(p.a<?> builder) {
            super(builder);
            this.e = builder.b_();
        }

        private c(boolean noInit) {
            this.e = ap.c();
        }

        public static c h() {
            return d;
        }

        public c k() {
            return d;
        }

        @Override
        public final ap b_() {
            return this.e;
        }

        private c(a.h input, n extensionRegistry) throws s {
            this.w();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block12: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block12;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.f |= 1;
                                this.g = input.g();
                                break;
                            }
                            case 16: {
                                if ((mutable_bitField0_ & 2) != 2) {
                                    this.h = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 2;
                                }
                                this.h.add(input.g());
                                break;
                            }
                            case 18: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 2) != 2 && input.x() > 0) {
                                    this.h = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 2;
                                }
                                while (input.x() > 0) {
                                    this.h.add(input.g());
                                }
                                input.g(limit);
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.h = Collections.unmodifiableList(this.h);
                }
                this.e = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return c;
        }

        @Override
        protected p.g l() {
            return d.a(c.class, a.class);
        }

        public ab<c> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.f & 1) == 1;
        }

        @Override
        public int p() {
            return this.g;
        }

        @Override
        public List<Integer> q() {
            return this.h;
        }

        @Override
        public int r() {
            return this.h.size();
        }

        @Override
        public int a(int index) {
            return this.h.get(index);
        }

        private void w() {
            this.g = 0;
            this.h = Collections.emptyList();
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.i;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.i = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.f & 1) == 1) {
                output.a(1, this.g);
            }
            int i2 = 0;
            while (i2 < this.h.size()) {
                output.a(2, this.h.get(i2));
                ++i2;
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.j;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.f & 1) == 1) {
                size += a.i.g(1, this.g);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.h.size()) {
                dataSize += a.i.h(this.h.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.q().size();
            this.j = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static c a(a.g data) throws s {
            return a.d(data);
        }

        public static c a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static c a(byte[] data) throws s {
            return a.d(data);
        }

        public static c a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static c a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static c a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static c b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static c b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static c a(a.h input) throws IOException {
            return a.d(input);
        }

        public static c a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a s() {
            return a.z();
        }

        public a t() {
            return an.b$c.s();
        }

        public static a a(c prototype) {
            return an.b$c.s().a(prototype);
        }

        public a u() {
            return an.b$c.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.t();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.t();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.u();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.u();
        }

        public static final class a
        extends p.a<a>
        implements d {
            private int a;
            private int b;
            private List<Integer> c = Collections.emptyList();

            public static final k.a k() {
                return c;
            }

            @Override
            protected p.g l() {
                return d.a(c.class, a.class);
            }

            private a() {
                this.y();
            }

            private a(p.b parent) {
                super(parent);
                this.y();
            }

            private void y() {
                m;
            }

            private static a z() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = Collections.emptyList();
                this.a &= 0xFFFFFFFD;
                return this;
            }

            public a n() {
                return an.b$c$a.z().a(this.u());
            }

            @Override
            public k.a J() {
                return c;
            }

            public c s() {
                return an.b$c.h();
            }

            public c t() {
                c result = this.u();
                if (!result.a()) {
                    throw an.b$c$a.b(result);
                }
                return result;
            }

            public c u() {
                c result = new c(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.g = this.b;
                if ((this.a & 2) == 2) {
                    this.c = Collections.unmodifiableList(this.c);
                    this.a &= 0xFFFFFFFD;
                }
                result.h = this.c;
                result.f = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof c) {
                    return this.a((c)other);
                }
                super.a(other);
                return this;
            }

            public a a(c other) {
                if (other == an.b$c.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (!other.h.isEmpty()) {
                    if (this.c.isEmpty()) {
                        this.c = other.h;
                        this.a &= 0xFFFFFFFD;
                    } else {
                        this.A();
                        this.c.addAll(other.h);
                    }
                    this.t_();
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                c parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (c)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a b(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a v() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            private void A() {
                if ((this.a & 2) != 2) {
                    this.c = new ArrayList<Integer>(this.c);
                    this.a |= 2;
                }
            }

            @Override
            public List<Integer> q() {
                return Collections.unmodifiableList(this.c);
            }

            @Override
            public int r() {
                return this.c.size();
            }

            @Override
            public int a(int index) {
                return this.c.get(index);
            }

            public a a(int index, int value) {
                this.A();
                this.c.set(index, value);
                this.t_();
                return this;
            }

            public a c(int value) {
                this.A();
                this.c.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends Integer> values) {
                this.A();
                p.a.a(values, this.c);
                this.t_();
                return this;
            }

            public a w() {
                this.c = Collections.emptyList();
                this.a &= 0xFFFFFFFD;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y Q() {
                return this.s();
            }

            @Override
            public /* synthetic */ x R() {
                return this.s();
            }

            @Override
            public /* synthetic */ y al() {
                return this.u();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.u();
            }

            @Override
            public /* synthetic */ y am() {
                return this.t();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.t();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }
        }
    }

    public static interface d
    extends aa {
        public boolean o();

        public int p();

        public List<Integer> q();

        public int r();

        public int a(int var1);
    }

    public static final class e
    extends p
    implements f {
        private static final e K;
        private final ap L;
        public static ab<e> a;
        private int M;
        private int N;
        public static final int b = 1;
        private int O;
        public static final int c = 2;
        private int P;
        public static final int d = 3;
        private int Q;
        public static final int e = 4;
        private int R;
        public static final int f = 5;
        private int S;
        public static final int g = 6;
        private int T;
        public static final int h = 7;
        private int U;
        public static final int i = 8;
        private int V;
        public static final int j = 9;
        private a.g W;
        public static final int k = 10;
        private a.g X;
        public static final int l = 11;
        private int Y;
        public static final int n = 12;
        private int Z;
        public static final int o = 13;
        private int aa;
        public static final int p = 14;
        private int ab;
        public static final int q = 15;
        private int ac;
        public static final int r = 16;
        private int ad;
        public static final int s = 17;
        private int ae;
        public static final int t = 18;
        private int af;
        public static final int u = 19;
        private int ag;
        public static final int v = 20;
        private a.g ah;
        public static final int w = 21;
        private a.g ai;
        public static final int x = 22;
        private int aj;
        public static final int y = 23;
        private int ak;
        public static final int z = 24;
        private int al;
        public static final int A = 25;
        private a.g am;
        public static final int B = 26;
        private int an;
        public static final int C = 27;
        private int ao;
        public static final int D = 28;
        private int ap;
        public static final int E = 29;
        private int aq;
        public static final int F = 30;
        private int ar;
        public static final int G = 31;
        private int as;
        public static final int H = 32;
        private int at;
        public static final int I = 33;
        private int au;
        public static final int J = 34;
        private int av;
        private byte aw = (byte)-1;
        private int ax = -1;
        private static final long ay = 0L;

        static {
            a = new a.c<e>(){

                public e c(a.h input, n extensionRegistry) throws s {
                    return new e(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            K = new e(true);
            K.aS();
        }

        private e(p.a<?> builder) {
            super(builder);
            this.L = builder.b_();
        }

        private e(boolean noInit) {
            this.L = a.ap.c();
        }

        public static e h() {
            return K;
        }

        public e k() {
            return K;
        }

        @Override
        public final ap b_() {
            return this.L;
        }

        private e(a.h input, n extensionRegistry) throws s {
            this.aS();
            boolean mutable_bitField0_ = false;
            boolean mutable_bitField1_ = false;
            ap.a unknownFields = a.ap.b();
            try {
                try {
                    boolean done = false;
                    block43: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block43;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.M |= 1;
                                this.O = input.m();
                                break;
                            }
                            case 16: {
                                this.M |= 2;
                                this.P = input.g();
                                break;
                            }
                            case 24: {
                                this.M |= 4;
                                this.Q = input.g();
                                break;
                            }
                            case 32: {
                                this.M |= 8;
                                this.R = input.g();
                                break;
                            }
                            case 40: {
                                this.M |= 0x10;
                                this.S = input.g();
                                break;
                            }
                            case 48: {
                                this.M |= 0x20;
                                this.T = input.g();
                                break;
                            }
                            case 56: {
                                this.M |= 0x40;
                                this.U = input.g();
                                break;
                            }
                            case 64: {
                                this.M |= 0x80;
                                this.V = input.g();
                                break;
                            }
                            case 74: {
                                this.M |= 0x100;
                                this.W = input.l();
                                break;
                            }
                            case 82: {
                                this.M |= 0x200;
                                this.X = input.l();
                                break;
                            }
                            case 88: {
                                this.M |= 0x400;
                                this.Y = input.g();
                                break;
                            }
                            case 96: {
                                this.M |= 0x800;
                                this.Z = input.g();
                                break;
                            }
                            case 104: {
                                this.M |= 0x1000;
                                this.aa = input.g();
                                break;
                            }
                            case 112: {
                                this.M |= 0x2000;
                                this.ab = input.g();
                                break;
                            }
                            case 120: {
                                this.M |= 0x4000;
                                this.ac = input.g();
                                break;
                            }
                            case 128: {
                                this.M |= 0x8000;
                                this.ad = input.g();
                                break;
                            }
                            case 136: {
                                this.M |= 0x10000;
                                this.ae = input.g();
                                break;
                            }
                            case 144: {
                                this.M |= 0x20000;
                                this.af = input.g();
                                break;
                            }
                            case 152: {
                                this.M |= 0x40000;
                                this.ag = input.g();
                                break;
                            }
                            case 162: {
                                this.M |= 0x80000;
                                this.ah = input.l();
                                break;
                            }
                            case 170: {
                                this.M |= 0x100000;
                                this.ai = input.l();
                                break;
                            }
                            case 176: {
                                this.M |= 0x200000;
                                this.aj = input.g();
                                break;
                            }
                            case 184: {
                                this.M |= 0x400000;
                                this.ak = input.g();
                                break;
                            }
                            case 192: {
                                this.M |= 0x800000;
                                this.al = input.g();
                                break;
                            }
                            case 202: {
                                this.M |= 0x1000000;
                                this.am = input.l();
                                break;
                            }
                            case 208: {
                                this.M |= 0x2000000;
                                this.an = input.g();
                                break;
                            }
                            case 216: {
                                this.M |= 0x4000000;
                                this.ao = input.g();
                                break;
                            }
                            case 224: {
                                this.M |= 0x8000000;
                                this.ap = input.g();
                                break;
                            }
                            case 232: {
                                this.M |= 0x10000000;
                                this.aq = input.g();
                                break;
                            }
                            case 240: {
                                this.M |= 0x20000000;
                                this.ar = input.g();
                                break;
                            }
                            case 248: {
                                this.M |= 0x40000000;
                                this.as = input.g();
                                break;
                            }
                            case 256: {
                                this.M |= Integer.MIN_VALUE;
                                this.at = input.g();
                                break;
                            }
                            case 264: {
                                this.N |= 1;
                                this.au = input.g();
                                break;
                            }
                            case 272: {
                                this.N |= 2;
                                this.av = input.g();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                this.L = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return e;
        }

        @Override
        protected p.g l() {
            return f.a(e.class, a.class);
        }

        public ab<e> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.M & 1) == 1;
        }

        @Override
        public int p() {
            return this.O;
        }

        @Override
        public boolean q() {
            return (this.M & 2) == 2;
        }

        @Override
        public int r() {
            return this.P;
        }

        @Override
        public boolean s() {
            return (this.M & 4) == 4;
        }

        @Override
        public int t() {
            return this.Q;
        }

        @Override
        public boolean u() {
            return (this.M & 8) == 8;
        }

        @Override
        public int v() {
            return this.R;
        }

        @Override
        public boolean w() {
            return (this.M & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.S;
        }

        @Override
        public boolean y() {
            return (this.M & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.T;
        }

        @Override
        public boolean A() {
            return (this.M & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.U;
        }

        @Override
        public boolean C() {
            return (this.M & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.V;
        }

        @Override
        public boolean E() {
            return (this.M & 0x100) == 256;
        }

        @Override
        public a.g F() {
            return this.W;
        }

        @Override
        public boolean G() {
            return (this.M & 0x200) == 512;
        }

        @Override
        public a.g H() {
            return this.X;
        }

        @Override
        public boolean K() {
            return (this.M & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.Y;
        }

        @Override
        public boolean S() {
            return (this.M & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.Z;
        }

        @Override
        public boolean U() {
            return (this.M & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.aa;
        }

        @Override
        public boolean W() {
            return (this.M & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.ab;
        }

        @Override
        public boolean Y() {
            return (this.M & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.ac;
        }

        @Override
        public boolean aa() {
            return (this.M & 0x8000) == 32768;
        }

        @Override
        public int ab() {
            return this.ad;
        }

        @Override
        public boolean ae() {
            return (this.M & 0x10000) == 65536;
        }

        @Override
        public int af() {
            return this.ae;
        }

        @Override
        public boolean ag() {
            return (this.M & 0x20000) == 131072;
        }

        @Override
        public int i_() {
            return this.af;
        }

        @Override
        public boolean j_() {
            return (this.M & 0x40000) == 262144;
        }

        @Override
        public int k_() {
            return this.ag;
        }

        @Override
        public boolean l_() {
            return (this.M & 0x80000) == 524288;
        }

        @Override
        public a.g m_() {
            return this.ah;
        }

        @Override
        public boolean n_() {
            return (this.M & 0x100000) == 0x100000;
        }

        @Override
        public a.g an() {
            return this.ai;
        }

        @Override
        public boolean ao() {
            return (this.M & 0x200000) == 0x200000;
        }

        @Override
        public int ap() {
            return this.aj;
        }

        @Override
        public boolean aq() {
            return (this.M & 0x400000) == 0x400000;
        }

        @Override
        public int ar() {
            return this.ak;
        }

        @Override
        public boolean as() {
            return (this.M & 0x800000) == 0x800000;
        }

        @Override
        public int at() {
            return this.al;
        }

        @Override
        public boolean au() {
            return (this.M & 0x1000000) == 0x1000000;
        }

        @Override
        public a.g av() {
            return this.am;
        }

        @Override
        public boolean aw() {
            return (this.M & 0x2000000) == 0x2000000;
        }

        @Override
        public int ax() {
            return this.an;
        }

        @Override
        public boolean ay() {
            return (this.M & 0x4000000) == 0x4000000;
        }

        @Override
        public int az() {
            return this.ao;
        }

        @Override
        public boolean o_() {
            return (this.M & 0x8000000) == 0x8000000;
        }

        @Override
        public int aB() {
            return this.ap;
        }

        @Override
        public boolean aC() {
            return (this.M & 0x10000000) == 0x10000000;
        }

        @Override
        public int aD() {
            return this.aq;
        }

        @Override
        public boolean p_() {
            return (this.M & 0x20000000) == 0x20000000;
        }

        @Override
        public int aF() {
            return this.ar;
        }

        @Override
        public boolean aG() {
            return (this.M & 0x40000000) == 0x40000000;
        }

        @Override
        public int aH() {
            return this.as;
        }

        @Override
        public boolean aI() {
            return (this.M & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        }

        @Override
        public int aJ() {
            return this.at;
        }

        @Override
        public boolean aK() {
            return (this.N & 1) == 1;
        }

        @Override
        public int aL() {
            return this.au;
        }

        @Override
        public boolean aM() {
            return (this.N & 2) == 2;
        }

        @Override
        public int aN() {
            return this.av;
        }

        private void aS() {
            this.O = 0;
            this.P = 0;
            this.Q = 0;
            this.R = 0;
            this.S = 0;
            this.T = 0;
            this.U = 0;
            this.V = 0;
            this.W = a.g.d;
            this.X = a.g.d;
            this.Y = 0;
            this.Z = 0;
            this.aa = 0;
            this.ab = 0;
            this.ac = 0;
            this.ad = 0;
            this.ae = 0;
            this.af = 0;
            this.ag = 0;
            this.ah = a.g.d;
            this.ai = a.g.d;
            this.aj = 0;
            this.ak = 0;
            this.al = 0;
            this.am = a.g.d;
            this.an = 0;
            this.ao = 0;
            this.ap = 0;
            this.aq = 0;
            this.ar = 0;
            this.as = 0;
            this.at = 0;
            this.au = 0;
            this.av = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.aw;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.aw = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.M & 1) == 1) {
                output.c(1, this.O);
            }
            if ((this.M & 2) == 2) {
                output.a(2, this.P);
            }
            if ((this.M & 4) == 4) {
                output.a(3, this.Q);
            }
            if ((this.M & 8) == 8) {
                output.a(4, this.R);
            }
            if ((this.M & 0x10) == 16) {
                output.a(5, this.S);
            }
            if ((this.M & 0x20) == 32) {
                output.a(6, this.T);
            }
            if ((this.M & 0x40) == 64) {
                output.a(7, this.U);
            }
            if ((this.M & 0x80) == 128) {
                output.a(8, this.V);
            }
            if ((this.M & 0x100) == 256) {
                output.a(9, this.W);
            }
            if ((this.M & 0x200) == 512) {
                output.a(10, this.X);
            }
            if ((this.M & 0x400) == 1024) {
                output.a(11, this.Y);
            }
            if ((this.M & 0x800) == 2048) {
                output.a(12, this.Z);
            }
            if ((this.M & 0x1000) == 4096) {
                output.a(13, this.aa);
            }
            if ((this.M & 0x2000) == 8192) {
                output.a(14, this.ab);
            }
            if ((this.M & 0x4000) == 16384) {
                output.a(15, this.ac);
            }
            if ((this.M & 0x8000) == 32768) {
                output.a(16, this.ad);
            }
            if ((this.M & 0x10000) == 65536) {
                output.a(17, this.ae);
            }
            if ((this.M & 0x20000) == 131072) {
                output.a(18, this.af);
            }
            if ((this.M & 0x40000) == 262144) {
                output.a(19, this.ag);
            }
            if ((this.M & 0x80000) == 524288) {
                output.a(20, this.ah);
            }
            if ((this.M & 0x100000) == 0x100000) {
                output.a(21, this.ai);
            }
            if ((this.M & 0x200000) == 0x200000) {
                output.a(22, this.aj);
            }
            if ((this.M & 0x400000) == 0x400000) {
                output.a(23, this.ak);
            }
            if ((this.M & 0x800000) == 0x800000) {
                output.a(24, this.al);
            }
            if ((this.M & 0x1000000) == 0x1000000) {
                output.a(25, this.am);
            }
            if ((this.M & 0x2000000) == 0x2000000) {
                output.a(26, this.an);
            }
            if ((this.M & 0x4000000) == 0x4000000) {
                output.a(27, this.ao);
            }
            if ((this.M & 0x8000000) == 0x8000000) {
                output.a(28, this.ap);
            }
            if ((this.M & 0x10000000) == 0x10000000) {
                output.a(29, this.aq);
            }
            if ((this.M & 0x20000000) == 0x20000000) {
                output.a(30, this.ar);
            }
            if ((this.M & 0x40000000) == 0x40000000) {
                output.a(31, this.as);
            }
            if ((this.M & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
                output.a(32, this.at);
            }
            if ((this.N & 1) == 1) {
                output.a(33, this.au);
            }
            if ((this.N & 2) == 2) {
                output.a(34, this.av);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.ax;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.M & 1) == 1) {
                size += a.i.i(1, this.O);
            }
            if ((this.M & 2) == 2) {
                size += a.i.g(2, this.P);
            }
            if ((this.M & 4) == 4) {
                size += a.i.g(3, this.Q);
            }
            if ((this.M & 8) == 8) {
                size += a.i.g(4, this.R);
            }
            if ((this.M & 0x10) == 16) {
                size += a.i.g(5, this.S);
            }
            if ((this.M & 0x20) == 32) {
                size += a.i.g(6, this.T);
            }
            if ((this.M & 0x40) == 64) {
                size += a.i.g(7, this.U);
            }
            if ((this.M & 0x80) == 128) {
                size += a.i.g(8, this.V);
            }
            if ((this.M & 0x100) == 256) {
                size += a.i.c(9, this.W);
            }
            if ((this.M & 0x200) == 512) {
                size += a.i.c(10, this.X);
            }
            if ((this.M & 0x400) == 1024) {
                size += a.i.g(11, this.Y);
            }
            if ((this.M & 0x800) == 2048) {
                size += a.i.g(12, this.Z);
            }
            if ((this.M & 0x1000) == 4096) {
                size += a.i.g(13, this.aa);
            }
            if ((this.M & 0x2000) == 8192) {
                size += a.i.g(14, this.ab);
            }
            if ((this.M & 0x4000) == 16384) {
                size += a.i.g(15, this.ac);
            }
            if ((this.M & 0x8000) == 32768) {
                size += a.i.g(16, this.ad);
            }
            if ((this.M & 0x10000) == 65536) {
                size += a.i.g(17, this.ae);
            }
            if ((this.M & 0x20000) == 131072) {
                size += a.i.g(18, this.af);
            }
            if ((this.M & 0x40000) == 262144) {
                size += a.i.g(19, this.ag);
            }
            if ((this.M & 0x80000) == 524288) {
                size += a.i.c(20, this.ah);
            }
            if ((this.M & 0x100000) == 0x100000) {
                size += a.i.c(21, this.ai);
            }
            if ((this.M & 0x200000) == 0x200000) {
                size += a.i.g(22, this.aj);
            }
            if ((this.M & 0x400000) == 0x400000) {
                size += a.i.g(23, this.ak);
            }
            if ((this.M & 0x800000) == 0x800000) {
                size += a.i.g(24, this.al);
            }
            if ((this.M & 0x1000000) == 0x1000000) {
                size += a.i.c(25, this.am);
            }
            if ((this.M & 0x2000000) == 0x2000000) {
                size += a.i.g(26, this.an);
            }
            if ((this.M & 0x4000000) == 0x4000000) {
                size += a.i.g(27, this.ao);
            }
            if ((this.M & 0x8000000) == 0x8000000) {
                size += a.i.g(28, this.ap);
            }
            if ((this.M & 0x10000000) == 0x10000000) {
                size += a.i.g(29, this.aq);
            }
            if ((this.M & 0x20000000) == 0x20000000) {
                size += a.i.g(30, this.ar);
            }
            if ((this.M & 0x40000000) == 0x40000000) {
                size += a.i.g(31, this.as);
            }
            if ((this.M & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
                size += a.i.g(32, this.at);
            }
            if ((this.N & 1) == 1) {
                size += a.i.g(33, this.au);
            }
            if ((this.N & 2) == 2) {
                size += a.i.g(34, this.av);
            }
            this.ax = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static e a(a.g data) throws s {
            return a.d(data);
        }

        public static e a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static e a(byte[] data) throws s {
            return a.d(data);
        }

        public static e a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static e a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static e a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static e b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static e b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static e a(a.h input) throws IOException {
            return a.d(input);
        }

        public static e a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aO() {
            return a.bu();
        }

        public a aP() {
            return an.b$e.aO();
        }

        public static a a(e prototype) {
            return an.b$e.aO().a(prototype);
        }

        public a aQ() {
            return an.b$e.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.aQ();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.aQ();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.aP();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.aP();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        public static final class a
        extends p.a<a>
        implements f {
            private int a;
            private int b;
            private int c;
            private int d;
            private int e;
            private int f;
            private int g;
            private int h;
            private int i;
            private int j;
            private a.g k = a.g.d;
            private a.g l = a.g.d;
            private int m;
            private int n;
            private int o;
            private int p;
            private int q;
            private int r;
            private int s;
            private int t;
            private int u;
            private a.g v = a.g.d;
            private a.g w = a.g.d;
            private int x;
            private int y;
            private int z;
            private a.g A = a.g.d;
            private int B;
            private int C;
            private int D;
            private int E;
            private int F;
            private int G;
            private int H;
            private int I;
            private int J;

            public static final k.a k() {
                return e;
            }

            @Override
            protected p.g l() {
                return f.a(e.class, a.class);
            }

            private a() {
                this.bt();
            }

            private a(p.b parent) {
                super(parent);
                this.bt();
            }

            private void bt() {
                m;
            }

            private static a bu() {
                return new a();
            }

            public a m() {
                super.ah();
                this.c = 0;
                this.a &= 0xFFFFFFFE;
                this.d = 0;
                this.a &= 0xFFFFFFFD;
                this.e = 0;
                this.a &= 0xFFFFFFFB;
                this.f = 0;
                this.a &= 0xFFFFFFF7;
                this.g = 0;
                this.a &= 0xFFFFFFEF;
                this.h = 0;
                this.a &= 0xFFFFFFDF;
                this.i = 0;
                this.a &= 0xFFFFFFBF;
                this.j = 0;
                this.a &= 0xFFFFFF7F;
                this.k = a.g.d;
                this.a &= 0xFFFFFEFF;
                this.l = a.g.d;
                this.a &= 0xFFFFFDFF;
                this.m = 0;
                this.a &= 0xFFFFFBFF;
                this.n = 0;
                this.a &= 0xFFFFF7FF;
                this.o = 0;
                this.a &= 0xFFFFEFFF;
                this.p = 0;
                this.a &= 0xFFFFDFFF;
                this.q = 0;
                this.a &= 0xFFFFBFFF;
                this.r = 0;
                this.a &= 0xFFFF7FFF;
                this.s = 0;
                this.a &= 0xFFFEFFFF;
                this.t = 0;
                this.a &= 0xFFFDFFFF;
                this.u = 0;
                this.a &= 0xFFFBFFFF;
                this.v = a.g.d;
                this.a &= 0xFFF7FFFF;
                this.w = a.g.d;
                this.a &= 0xFFEFFFFF;
                this.x = 0;
                this.a &= 0xFFDFFFFF;
                this.y = 0;
                this.a &= 0xFFBFFFFF;
                this.z = 0;
                this.a &= 0xFF7FFFFF;
                this.A = a.g.d;
                this.a &= 0xFEFFFFFF;
                this.B = 0;
                this.a &= 0xFDFFFFFF;
                this.C = 0;
                this.a &= 0xFBFFFFFF;
                this.D = 0;
                this.a &= 0xF7FFFFFF;
                this.E = 0;
                this.a &= 0xEFFFFFFF;
                this.F = 0;
                this.a &= 0xDFFFFFFF;
                this.G = 0;
                this.a &= 0xBFFFFFFF;
                this.H = 0;
                this.a &= Integer.MAX_VALUE;
                this.I = 0;
                this.b &= 0xFFFFFFFE;
                this.J = 0;
                this.b &= 0xFFFFFFFD;
                return this;
            }

            public a n() {
                return an.b$e$a.bu().a(this.N());
            }

            @Override
            public k.a J() {
                return e;
            }

            public e I() {
                return an.b$e.h();
            }

            public e M() {
                e result = this.N();
                if (!result.a()) {
                    throw an.b$e$a.b(result);
                }
                return result;
            }

            public e N() {
                e result = new e(this);
                int from_bitField0_ = this.a;
                int from_bitField1_ = this.b;
                int to_bitField0_ = 0;
                int to_bitField1_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.O = this.c;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.P = this.d;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.Q = this.e;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.R = this.f;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.S = this.g;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.T = this.h;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.U = this.i;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.V = this.j;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.W = this.k;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.X = this.l;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.Y = this.m;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.Z = this.n;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.aa = this.o;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.ab = this.p;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.ac = this.q;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x8000;
                }
                result.ad = this.r;
                if ((from_bitField0_ & 0x10000) == 65536) {
                    to_bitField0_ |= 0x10000;
                }
                result.ae = this.s;
                if ((from_bitField0_ & 0x20000) == 131072) {
                    to_bitField0_ |= 0x20000;
                }
                result.af = this.t;
                if ((from_bitField0_ & 0x40000) == 262144) {
                    to_bitField0_ |= 0x40000;
                }
                result.ag = this.u;
                if ((from_bitField0_ & 0x80000) == 524288) {
                    to_bitField0_ |= 0x80000;
                }
                result.ah = this.v;
                if ((from_bitField0_ & 0x100000) == 0x100000) {
                    to_bitField0_ |= 0x100000;
                }
                result.ai = this.w;
                if ((from_bitField0_ & 0x200000) == 0x200000) {
                    to_bitField0_ |= 0x200000;
                }
                result.aj = this.x;
                if ((from_bitField0_ & 0x400000) == 0x400000) {
                    to_bitField0_ |= 0x400000;
                }
                result.ak = this.y;
                if ((from_bitField0_ & 0x800000) == 0x800000) {
                    to_bitField0_ |= 0x800000;
                }
                result.al = this.z;
                if ((from_bitField0_ & 0x1000000) == 0x1000000) {
                    to_bitField0_ |= 0x1000000;
                }
                result.am = this.A;
                if ((from_bitField0_ & 0x2000000) == 0x2000000) {
                    to_bitField0_ |= 0x2000000;
                }
                result.an = this.B;
                if ((from_bitField0_ & 0x4000000) == 0x4000000) {
                    to_bitField0_ |= 0x4000000;
                }
                result.ao = this.C;
                if ((from_bitField0_ & 0x8000000) == 0x8000000) {
                    to_bitField0_ |= 0x8000000;
                }
                result.ap = this.D;
                if ((from_bitField0_ & 0x10000000) == 0x10000000) {
                    to_bitField0_ |= 0x10000000;
                }
                result.aq = this.E;
                if ((from_bitField0_ & 0x20000000) == 0x20000000) {
                    to_bitField0_ |= 0x20000000;
                }
                result.ar = this.F;
                if ((from_bitField0_ & 0x40000000) == 0x40000000) {
                    to_bitField0_ |= 0x40000000;
                }
                result.as = this.G;
                if ((from_bitField0_ & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
                    to_bitField0_ |= Integer.MIN_VALUE;
                }
                result.at = this.H;
                if ((from_bitField1_ & 1) == 1) {
                    to_bitField1_ |= 1;
                }
                result.au = this.I;
                if ((from_bitField1_ & 2) == 2) {
                    to_bitField1_ |= 2;
                }
                result.av = this.J;
                result.M = to_bitField0_;
                result.N = to_bitField1_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof e) {
                    return this.a((e)other);
                }
                super.a(other);
                return this;
            }

            public a a(e other) {
                if (other == an.b$e.h()) {
                    return this;
                }
                if (other.o()) {
                    this.a(other.p());
                }
                if (other.q()) {
                    this.b(other.r());
                }
                if (other.s()) {
                    this.c(other.t());
                }
                if (other.u()) {
                    this.d(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
                }
                if (other.y()) {
                    this.f(other.z());
                }
                if (other.A()) {
                    this.g(other.B());
                }
                if (other.C()) {
                    this.h(other.D());
                }
                if (other.E()) {
                    this.e(other.F());
                }
                if (other.G()) {
                    this.f(other.H());
                }
                if (other.K()) {
                    this.i(other.L());
                }
                if (other.S()) {
                    this.j(other.T());
                }
                if (other.U()) {
                    this.k(other.V());
                }
                if (other.W()) {
                    this.l(other.X());
                }
                if (other.Y()) {
                    this.m(other.Z());
                }
                if (other.aa()) {
                    this.n(other.ab());
                }
                if (other.ae()) {
                    this.o(other.af());
                }
                if (other.ag()) {
                    this.p(other.i_());
                }
                if (other.j_()) {
                    this.q(other.k_());
                }
                if (other.l_()) {
                    this.g(other.m_());
                }
                if (other.n_()) {
                    this.h(other.an());
                }
                if (other.ao()) {
                    this.r(other.ap());
                }
                if (other.aq()) {
                    this.s(other.ar());
                }
                if (other.as()) {
                    this.t(other.at());
                }
                if (other.au()) {
                    this.i(other.av());
                }
                if (other.aw()) {
                    this.u(other.ax());
                }
                if (other.ay()) {
                    this.v(other.az());
                }
                if (other.o_()) {
                    this.w(other.aB());
                }
                if (other.aC()) {
                    this.x(other.aD());
                }
                if (other.p_()) {
                    this.y(other.aF());
                }
                if (other.aG()) {
                    this.z(other.aH());
                }
                if (other.aI()) {
                    this.A(other.aJ());
                }
                if (other.aK()) {
                    this.B(other.aL());
                }
                if (other.aM()) {
                    this.C(other.aN());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                e parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (e)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.c;
            }

            public a a(int value) {
                this.a |= 1;
                this.c = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.d;
            }

            public a b(int value) {
                this.a |= 2;
                this.d = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public int t() {
                return this.e;
            }

            public a c(int value) {
                this.a |= 4;
                this.e = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.f;
            }

            public a d(int value) {
                this.a |= 8;
                this.f = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public int x() {
                return this.g;
            }

            public a e(int value) {
                this.a |= 0x10;
                this.g = value;
                this.t_();
                return this;
            }

            public a aO() {
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.h;
            }

            public a f(int value) {
                this.a |= 0x20;
                this.h = value;
                this.t_();
                return this;
            }

            public a aP() {
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.i;
            }

            public a g(int value) {
                this.a |= 0x40;
                this.i = value;
                this.t_();
                return this;
            }

            public a aQ() {
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.j;
            }

            public a h(int value) {
                this.a |= 0x80;
                this.j = value;
                this.t_();
                return this;
            }

            public a aR() {
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public a.g F() {
                return this.k;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x100;
                this.k = value;
                this.t_();
                return this;
            }

            public a aS() {
                this.a &= 0xFFFFFEFF;
                this.k = an.b$e.h().F();
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public a.g H() {
                return this.l;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x200;
                this.l = value;
                this.t_();
                return this;
            }

            public a aT() {
                this.a &= 0xFFFFFDFF;
                this.l = an.b$e.h().H();
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.m;
            }

            public a i(int value) {
                this.a |= 0x400;
                this.m = value;
                this.t_();
                return this;
            }

            public a aU() {
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.n;
            }

            public a j(int value) {
                this.a |= 0x800;
                this.n = value;
                this.t_();
                return this;
            }

            public a aV() {
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.o;
            }

            public a k(int value) {
                this.a |= 0x1000;
                this.o = value;
                this.t_();
                return this;
            }

            public a aW() {
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.p;
            }

            public a l(int value) {
                this.a |= 0x2000;
                this.p = value;
                this.t_();
                return this;
            }

            public a aX() {
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.q;
            }

            public a m(int value) {
                this.a |= 0x4000;
                this.q = value;
                this.t_();
                return this;
            }

            public a aY() {
                this.a &= 0xFFFFBFFF;
                this.q = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aa() {
                return (this.a & 0x8000) == 32768;
            }

            @Override
            public int ab() {
                return this.r;
            }

            public a n(int value) {
                this.a |= 0x8000;
                this.r = value;
                this.t_();
                return this;
            }

            public a aZ() {
                this.a &= 0xFFFF7FFF;
                this.r = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ae() {
                return (this.a & 0x10000) == 65536;
            }

            @Override
            public int af() {
                return this.s;
            }

            public a o(int value) {
                this.a |= 0x10000;
                this.s = value;
                this.t_();
                return this;
            }

            public a ba() {
                this.a &= 0xFFFEFFFF;
                this.s = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ag() {
                return (this.a & 0x20000) == 131072;
            }

            @Override
            public int i_() {
                return this.t;
            }

            public a p(int value) {
                this.a |= 0x20000;
                this.t = value;
                this.t_();
                return this;
            }

            public a bb() {
                this.a &= 0xFFFDFFFF;
                this.t = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean j_() {
                return (this.a & 0x40000) == 262144;
            }

            @Override
            public int k_() {
                return this.u;
            }

            public a q(int value) {
                this.a |= 0x40000;
                this.u = value;
                this.t_();
                return this;
            }

            public a bc() {
                this.a &= 0xFFFBFFFF;
                this.u = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean l_() {
                return (this.a & 0x80000) == 524288;
            }

            @Override
            public a.g m_() {
                return this.v;
            }

            public a g(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x80000;
                this.v = value;
                this.t_();
                return this;
            }

            public a bd() {
                this.a &= 0xFFF7FFFF;
                this.v = an.b$e.h().m_();
                this.t_();
                return this;
            }

            @Override
            public boolean n_() {
                return (this.a & 0x100000) == 0x100000;
            }

            @Override
            public a.g an() {
                return this.w;
            }

            public a h(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x100000;
                this.w = value;
                this.t_();
                return this;
            }

            public a be() {
                this.a &= 0xFFEFFFFF;
                this.w = an.b$e.h().an();
                this.t_();
                return this;
            }

            @Override
            public boolean ao() {
                return (this.a & 0x200000) == 0x200000;
            }

            @Override
            public int ap() {
                return this.x;
            }

            public a r(int value) {
                this.a |= 0x200000;
                this.x = value;
                this.t_();
                return this;
            }

            public a bf() {
                this.a &= 0xFFDFFFFF;
                this.x = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aq() {
                return (this.a & 0x400000) == 0x400000;
            }

            @Override
            public int ar() {
                return this.y;
            }

            public a s(int value) {
                this.a |= 0x400000;
                this.y = value;
                this.t_();
                return this;
            }

            public a bg() {
                this.a &= 0xFFBFFFFF;
                this.y = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean as() {
                return (this.a & 0x800000) == 0x800000;
            }

            @Override
            public int at() {
                return this.z;
            }

            public a t(int value) {
                this.a |= 0x800000;
                this.z = value;
                this.t_();
                return this;
            }

            public a bh() {
                this.a &= 0xFF7FFFFF;
                this.z = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean au() {
                return (this.a & 0x1000000) == 0x1000000;
            }

            @Override
            public a.g av() {
                return this.A;
            }

            public a i(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x1000000;
                this.A = value;
                this.t_();
                return this;
            }

            public a bi() {
                this.a &= 0xFEFFFFFF;
                this.A = an.b$e.h().av();
                this.t_();
                return this;
            }

            @Override
            public boolean aw() {
                return (this.a & 0x2000000) == 0x2000000;
            }

            @Override
            public int ax() {
                return this.B;
            }

            public a u(int value) {
                this.a |= 0x2000000;
                this.B = value;
                this.t_();
                return this;
            }

            public a bj() {
                this.a &= 0xFDFFFFFF;
                this.B = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ay() {
                return (this.a & 0x4000000) == 0x4000000;
            }

            @Override
            public int az() {
                return this.C;
            }

            public a v(int value) {
                this.a |= 0x4000000;
                this.C = value;
                this.t_();
                return this;
            }

            public a bk() {
                this.a &= 0xFBFFFFFF;
                this.C = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean o_() {
                return (this.a & 0x8000000) == 0x8000000;
            }

            @Override
            public int aB() {
                return this.D;
            }

            public a w(int value) {
                this.a |= 0x8000000;
                this.D = value;
                this.t_();
                return this;
            }

            public a bl() {
                this.a &= 0xF7FFFFFF;
                this.D = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aC() {
                return (this.a & 0x10000000) == 0x10000000;
            }

            @Override
            public int aD() {
                return this.E;
            }

            public a x(int value) {
                this.a |= 0x10000000;
                this.E = value;
                this.t_();
                return this;
            }

            public a bm() {
                this.a &= 0xEFFFFFFF;
                this.E = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean p_() {
                return (this.a & 0x20000000) == 0x20000000;
            }

            @Override
            public int aF() {
                return this.F;
            }

            public a y(int value) {
                this.a |= 0x20000000;
                this.F = value;
                this.t_();
                return this;
            }

            public a bn() {
                this.a &= 0xDFFFFFFF;
                this.F = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aG() {
                return (this.a & 0x40000000) == 0x40000000;
            }

            @Override
            public int aH() {
                return this.G;
            }

            public a z(int value) {
                this.a |= 0x40000000;
                this.G = value;
                this.t_();
                return this;
            }

            public a bo() {
                this.a &= 0xBFFFFFFF;
                this.G = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aI() {
                return (this.a & Integer.MIN_VALUE) == Integer.MIN_VALUE;
            }

            @Override
            public int aJ() {
                return this.H;
            }

            public a A(int value) {
                this.a |= Integer.MIN_VALUE;
                this.H = value;
                this.t_();
                return this;
            }

            public a bp() {
                this.a &= Integer.MAX_VALUE;
                this.H = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aK() {
                return (this.b & 1) == 1;
            }

            @Override
            public int aL() {
                return this.I;
            }

            public a B(int value) {
                this.b |= 1;
                this.I = value;
                this.t_();
                return this;
            }

            public a bq() {
                this.b &= 0xFFFFFFFE;
                this.I = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aM() {
                return (this.b & 2) == 2;
            }

            @Override
            public int aN() {
                return this.J;
            }

            public a C(int value) {
                this.b |= 2;
                this.J = value;
                this.t_();
                return this;
            }

            public a br() {
                this.b &= 0xFFFFFFFD;
                this.J = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }
        }
    }

    public static interface f
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public int t();

        public boolean u();

        public int v();

        public boolean w();

        public int x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public a.g F();

        public boolean G();

        public a.g H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();

        public boolean aa();

        public int ab();

        public boolean ae();

        public int af();

        public boolean ag();

        public int i_();

        public boolean j_();

        public int k_();

        public boolean l_();

        public a.g m_();

        public boolean n_();

        public a.g an();

        public boolean ao();

        public int ap();

        public boolean aq();

        public int ar();

        public boolean as();

        public int at();

        public boolean au();

        public a.g av();

        public boolean aw();

        public int ax();

        public boolean ay();

        public int az();

        public boolean o_();

        public int aB();

        public boolean aC();

        public int aD();

        public boolean p_();

        public int aF();

        public boolean aG();

        public int aH();

        public boolean aI();

        public int aJ();

        public boolean aK();

        public int aL();

        public boolean aM();

        public int aN();
    }

    public static final class g
    extends p
    implements h {
        private static final g I;
        private final ap J;
        public static ab<g> a;
        private int K;
        public static final int b = 1;
        private int L;
        public static final int c = 2;
        private int M;
        public static final int d = 3;
        private int N;
        public static final int e = 4;
        private a.g O;
        public static final int f = 5;
        private a.g P;
        public static final int g = 6;
        private int Q;
        public static final int h = 7;
        private int R;
        public static final int i = 8;
        private int S;
        public static final int j = 9;
        private int T;
        public static final int k = 10;
        private List<Integer> U;
        public static final int l = 11;
        private List<a.g> V;
        public static final int n = 12;
        private int W;
        public static final int o = 13;
        private int X;
        public static final int p = 14;
        private int Y;
        public static final int q = 15;
        private int Z;
        public static final int r = 16;
        private int aa;
        public static final int s = 17;
        private int ab;
        public static final int t = 18;
        private int ac;
        public static final int u = 19;
        private int ad;
        public static final int v = 20;
        private int ae;
        public static final int w = 21;
        private int af;
        public static final int x = 22;
        private int ag;
        public static final int y = 23;
        private int ah;
        public static final int z = 24;
        private int ai;
        public static final int A = 25;
        private int aj;
        public static final int B = 26;
        private int ak;
        public static final int C = 27;
        private int al;
        public static final int D = 28;
        private int am;
        public static final int E = 29;
        private int an;
        public static final int F = 30;
        private int ao;
        public static final int G = 31;
        private int ap;
        public static final int H = 32;
        private int aq;
        private byte ar = (byte)-1;
        private int as = -1;
        private static final long at = 0L;

        static {
            a = new a.c<g>(){

                public g c(a.h input, n extensionRegistry) throws s {
                    return new g(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            I = new g(true);
            I.aO();
        }

        private g(p.a<?> builder) {
            super(builder);
            this.J = builder.b_();
        }

        private g(boolean noInit) {
            this.J = a.ap.c();
        }

        public static g h() {
            return I;
        }

        public g k() {
            return I;
        }

        @Override
        public final ap b_() {
            return this.J;
        }

        private g(a.h input, n extensionRegistry) throws s {
            this.aO();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = a.ap.b();
            try {
                try {
                    boolean done = false;
                    block42: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block42;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.K |= 1;
                                this.L = input.g();
                                break;
                            }
                            case 16: {
                                this.K |= 2;
                                this.M = input.g();
                                break;
                            }
                            case 24: {
                                this.K |= 4;
                                this.N = input.g();
                                break;
                            }
                            case 34: {
                                this.K |= 8;
                                this.O = input.l();
                                break;
                            }
                            case 42: {
                                this.K |= 0x10;
                                this.P = input.l();
                                break;
                            }
                            case 48: {
                                this.K |= 0x20;
                                this.Q = input.g();
                                break;
                            }
                            case 56: {
                                this.K |= 0x40;
                                this.R = input.g();
                                break;
                            }
                            case 64: {
                                this.K |= 0x80;
                                this.S = input.g();
                                break;
                            }
                            case 72: {
                                this.K |= 0x100;
                                this.T = input.g();
                                break;
                            }
                            case 80: {
                                if ((mutable_bitField0_ & 0x200) != 512) {
                                    this.U = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x200;
                                }
                                this.U.add(input.g());
                                break;
                            }
                            case 82: {
                                int length = input.s();
                                int limit = input.f(length);
                                if ((mutable_bitField0_ & 0x200) != 512 && input.x() > 0) {
                                    this.U = new ArrayList<Integer>();
                                    mutable_bitField0_ |= 0x200;
                                }
                                while (input.x() > 0) {
                                    this.U.add(input.g());
                                }
                                input.g(limit);
                                break;
                            }
                            case 90: {
                                if ((mutable_bitField0_ & 0x400) != 1024) {
                                    this.V = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 0x400;
                                }
                                this.V.add(input.l());
                                break;
                            }
                            case 96: {
                                this.K |= 0x200;
                                this.W = input.g();
                                break;
                            }
                            case 104: {
                                this.K |= 0x400;
                                this.X = input.g();
                                break;
                            }
                            case 112: {
                                this.K |= 0x800;
                                this.Y = input.g();
                                break;
                            }
                            case 120: {
                                this.K |= 0x1000;
                                this.Z = input.g();
                                break;
                            }
                            case 128: {
                                this.K |= 0x2000;
                                this.aa = input.g();
                                break;
                            }
                            case 136: {
                                this.K |= 0x4000;
                                this.ab = input.g();
                                break;
                            }
                            case 144: {
                                this.K |= 0x8000;
                                this.ac = input.g();
                                break;
                            }
                            case 152: {
                                this.K |= 0x10000;
                                this.ad = input.g();
                                break;
                            }
                            case 160: {
                                this.K |= 0x20000;
                                this.ae = input.g();
                                break;
                            }
                            case 168: {
                                this.K |= 0x40000;
                                this.af = input.g();
                                break;
                            }
                            case 176: {
                                this.K |= 0x80000;
                                this.ag = input.g();
                                break;
                            }
                            case 184: {
                                this.K |= 0x100000;
                                this.ah = input.g();
                                break;
                            }
                            case 192: {
                                this.K |= 0x200000;
                                this.ai = input.g();
                                break;
                            }
                            case 200: {
                                this.K |= 0x400000;
                                this.aj = input.g();
                                break;
                            }
                            case 208: {
                                this.K |= 0x800000;
                                this.ak = input.g();
                                break;
                            }
                            case 216: {
                                this.K |= 0x1000000;
                                this.al = input.g();
                                break;
                            }
                            case 224: {
                                this.K |= 0x2000000;
                                this.am = input.g();
                                break;
                            }
                            case 232: {
                                this.K |= 0x4000000;
                                this.an = input.g();
                                break;
                            }
                            case 240: {
                                this.K |= 0x8000000;
                                this.ao = input.g();
                                break;
                            }
                            case 248: {
                                this.K |= 0x10000000;
                                this.ap = input.g();
                                break;
                            }
                            case 256: {
                                this.K |= 0x20000000;
                                this.aq = input.g();
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 0x200) == 512) {
                    this.U = Collections.unmodifiableList(this.U);
                }
                if ((mutable_bitField0_ & 0x400) == 1024) {
                    this.V = Collections.unmodifiableList(this.V);
                }
                this.J = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return g;
        }

        @Override
        protected p.g l() {
            return h.a(g.class, a.class);
        }

        public ab<g> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.K & 1) == 1;
        }

        @Override
        public int p() {
            return this.L;
        }

        @Override
        public boolean q() {
            return (this.K & 2) == 2;
        }

        @Override
        public int r() {
            return this.M;
        }

        @Override
        public boolean s() {
            return (this.K & 4) == 4;
        }

        @Override
        public int t() {
            return this.N;
        }

        @Override
        public boolean u() {
            return (this.K & 8) == 8;
        }

        @Override
        public a.g v() {
            return this.O;
        }

        @Override
        public boolean w() {
            return (this.K & 0x10) == 16;
        }

        @Override
        public a.g x() {
            return this.P;
        }

        @Override
        public boolean y() {
            return (this.K & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.Q;
        }

        @Override
        public boolean A() {
            return (this.K & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.R;
        }

        @Override
        public boolean C() {
            return (this.K & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.S;
        }

        @Override
        public boolean E() {
            return (this.K & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.T;
        }

        @Override
        public List<Integer> G() {
            return this.U;
        }

        @Override
        public int H() {
            return this.U.size();
        }

        @Override
        public int a(int index) {
            return this.U.get(index);
        }

        @Override
        public List<a.g> K() {
            return this.V;
        }

        @Override
        public int L() {
            return this.V.size();
        }

        @Override
        public a.g b(int index) {
            return this.V.get(index);
        }

        @Override
        public boolean S() {
            return (this.K & 0x200) == 512;
        }

        @Override
        public int T() {
            return this.W;
        }

        @Override
        public boolean U() {
            return (this.K & 0x400) == 1024;
        }

        @Override
        public int V() {
            return this.X;
        }

        @Override
        public boolean W() {
            return (this.K & 0x800) == 2048;
        }

        @Override
        public int X() {
            return this.Y;
        }

        @Override
        public boolean Y() {
            return (this.K & 0x1000) == 4096;
        }

        @Override
        public int Z() {
            return this.Z;
        }

        @Override
        public boolean aa() {
            return (this.K & 0x2000) == 8192;
        }

        @Override
        public int ab() {
            return this.aa;
        }

        @Override
        public boolean ae() {
            return (this.K & 0x4000) == 16384;
        }

        @Override
        public int af() {
            return this.ab;
        }

        @Override
        public boolean ag() {
            return (this.K & 0x8000) == 32768;
        }

        @Override
        public int u_() {
            return this.ac;
        }

        @Override
        public boolean v_() {
            return (this.K & 0x10000) == 65536;
        }

        @Override
        public int w_() {
            return this.ad;
        }

        @Override
        public boolean x_() {
            return (this.K & 0x20000) == 131072;
        }

        @Override
        public int y_() {
            return this.ae;
        }

        @Override
        public boolean z_() {
            return (this.K & 0x40000) == 262144;
        }

        @Override
        public int an() {
            return this.af;
        }

        @Override
        public boolean ao() {
            return (this.K & 0x80000) == 524288;
        }

        @Override
        public int ap() {
            return this.ag;
        }

        @Override
        public boolean aq() {
            return (this.K & 0x100000) == 0x100000;
        }

        @Override
        public int ar() {
            return this.ah;
        }

        @Override
        public boolean as() {
            return (this.K & 0x200000) == 0x200000;
        }

        @Override
        public int at() {
            return this.ai;
        }

        @Override
        public boolean au() {
            return (this.K & 0x400000) == 0x400000;
        }

        @Override
        public int av() {
            return this.aj;
        }

        @Override
        public boolean aw() {
            return (this.K & 0x800000) == 0x800000;
        }

        @Override
        public int ax() {
            return this.ak;
        }

        @Override
        public boolean ay() {
            return (this.K & 0x1000000) == 0x1000000;
        }

        @Override
        public int az() {
            return this.al;
        }

        @Override
        public boolean A_() {
            return (this.K & 0x2000000) == 0x2000000;
        }

        @Override
        public int aB() {
            return this.am;
        }

        @Override
        public boolean aC() {
            return (this.K & 0x4000000) == 0x4000000;
        }

        @Override
        public int aD() {
            return this.an;
        }

        @Override
        public boolean B_() {
            return (this.K & 0x8000000) == 0x8000000;
        }

        @Override
        public int aF() {
            return this.ao;
        }

        @Override
        public boolean aG() {
            return (this.K & 0x10000000) == 0x10000000;
        }

        @Override
        public int aH() {
            return this.ap;
        }

        @Override
        public boolean aI() {
            return (this.K & 0x20000000) == 0x20000000;
        }

        @Override
        public int aJ() {
            return this.aq;
        }

        private void aO() {
            this.L = 0;
            this.M = 0;
            this.N = 0;
            this.O = a.g.d;
            this.P = a.g.d;
            this.Q = 0;
            this.R = 0;
            this.S = 0;
            this.T = 0;
            this.U = Collections.emptyList();
            this.V = Collections.emptyList();
            this.W = 0;
            this.X = 0;
            this.Y = 0;
            this.Z = 0;
            this.aa = 0;
            this.ab = 0;
            this.ac = 0;
            this.ad = 0;
            this.ae = 0;
            this.af = 0;
            this.ag = 0;
            this.ah = 0;
            this.ai = 0;
            this.aj = 0;
            this.ak = 0;
            this.al = 0;
            this.am = 0;
            this.an = 0;
            this.ao = 0;
            this.ap = 0;
            this.aq = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.ar;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.ar = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.K & 1) == 1) {
                output.a(1, this.L);
            }
            if ((this.K & 2) == 2) {
                output.a(2, this.M);
            }
            if ((this.K & 4) == 4) {
                output.a(3, this.N);
            }
            if ((this.K & 8) == 8) {
                output.a(4, this.O);
            }
            if ((this.K & 0x10) == 16) {
                output.a(5, this.P);
            }
            if ((this.K & 0x20) == 32) {
                output.a(6, this.Q);
            }
            if ((this.K & 0x40) == 64) {
                output.a(7, this.R);
            }
            if ((this.K & 0x80) == 128) {
                output.a(8, this.S);
            }
            if ((this.K & 0x100) == 256) {
                output.a(9, this.T);
            }
            int i2 = 0;
            while (i2 < this.U.size()) {
                output.a(10, this.U.get(i2));
                ++i2;
            }
            i2 = 0;
            while (i2 < this.V.size()) {
                output.a(11, this.V.get(i2));
                ++i2;
            }
            if ((this.K & 0x200) == 512) {
                output.a(12, this.W);
            }
            if ((this.K & 0x400) == 1024) {
                output.a(13, this.X);
            }
            if ((this.K & 0x800) == 2048) {
                output.a(14, this.Y);
            }
            if ((this.K & 0x1000) == 4096) {
                output.a(15, this.Z);
            }
            if ((this.K & 0x2000) == 8192) {
                output.a(16, this.aa);
            }
            if ((this.K & 0x4000) == 16384) {
                output.a(17, this.ab);
            }
            if ((this.K & 0x8000) == 32768) {
                output.a(18, this.ac);
            }
            if ((this.K & 0x10000) == 65536) {
                output.a(19, this.ad);
            }
            if ((this.K & 0x20000) == 131072) {
                output.a(20, this.ae);
            }
            if ((this.K & 0x40000) == 262144) {
                output.a(21, this.af);
            }
            if ((this.K & 0x80000) == 524288) {
                output.a(22, this.ag);
            }
            if ((this.K & 0x100000) == 0x100000) {
                output.a(23, this.ah);
            }
            if ((this.K & 0x200000) == 0x200000) {
                output.a(24, this.ai);
            }
            if ((this.K & 0x400000) == 0x400000) {
                output.a(25, this.aj);
            }
            if ((this.K & 0x800000) == 0x800000) {
                output.a(26, this.ak);
            }
            if ((this.K & 0x1000000) == 0x1000000) {
                output.a(27, this.al);
            }
            if ((this.K & 0x2000000) == 0x2000000) {
                output.a(28, this.am);
            }
            if ((this.K & 0x4000000) == 0x4000000) {
                output.a(29, this.an);
            }
            if ((this.K & 0x8000000) == 0x8000000) {
                output.a(30, this.ao);
            }
            if ((this.K & 0x10000000) == 0x10000000) {
                output.a(31, this.ap);
            }
            if ((this.K & 0x20000000) == 0x20000000) {
                output.a(32, this.aq);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.as;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.K & 1) == 1) {
                size += a.i.g(1, this.L);
            }
            if ((this.K & 2) == 2) {
                size += a.i.g(2, this.M);
            }
            if ((this.K & 4) == 4) {
                size += a.i.g(3, this.N);
            }
            if ((this.K & 8) == 8) {
                size += a.i.c(4, this.O);
            }
            if ((this.K & 0x10) == 16) {
                size += a.i.c(5, this.P);
            }
            if ((this.K & 0x20) == 32) {
                size += a.i.g(6, this.Q);
            }
            if ((this.K & 0x40) == 64) {
                size += a.i.g(7, this.R);
            }
            if ((this.K & 0x80) == 128) {
                size += a.i.g(8, this.S);
            }
            if ((this.K & 0x100) == 256) {
                size += a.i.g(9, this.T);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.U.size()) {
                dataSize += a.i.h(this.U.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.G().size();
            dataSize = 0;
            i2 = 0;
            while (i2 < this.V.size()) {
                dataSize += a.i.b(this.V.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.K().size();
            if ((this.K & 0x200) == 512) {
                size += a.i.g(12, this.W);
            }
            if ((this.K & 0x400) == 1024) {
                size += a.i.g(13, this.X);
            }
            if ((this.K & 0x800) == 2048) {
                size += a.i.g(14, this.Y);
            }
            if ((this.K & 0x1000) == 4096) {
                size += a.i.g(15, this.Z);
            }
            if ((this.K & 0x2000) == 8192) {
                size += a.i.g(16, this.aa);
            }
            if ((this.K & 0x4000) == 16384) {
                size += a.i.g(17, this.ab);
            }
            if ((this.K & 0x8000) == 32768) {
                size += a.i.g(18, this.ac);
            }
            if ((this.K & 0x10000) == 65536) {
                size += a.i.g(19, this.ad);
            }
            if ((this.K & 0x20000) == 131072) {
                size += a.i.g(20, this.ae);
            }
            if ((this.K & 0x40000) == 262144) {
                size += a.i.g(21, this.af);
            }
            if ((this.K & 0x80000) == 524288) {
                size += a.i.g(22, this.ag);
            }
            if ((this.K & 0x100000) == 0x100000) {
                size += a.i.g(23, this.ah);
            }
            if ((this.K & 0x200000) == 0x200000) {
                size += a.i.g(24, this.ai);
            }
            if ((this.K & 0x400000) == 0x400000) {
                size += a.i.g(25, this.aj);
            }
            if ((this.K & 0x800000) == 0x800000) {
                size += a.i.g(26, this.ak);
            }
            if ((this.K & 0x1000000) == 0x1000000) {
                size += a.i.g(27, this.al);
            }
            if ((this.K & 0x2000000) == 0x2000000) {
                size += a.i.g(28, this.am);
            }
            if ((this.K & 0x4000000) == 0x4000000) {
                size += a.i.g(29, this.an);
            }
            if ((this.K & 0x8000000) == 0x8000000) {
                size += a.i.g(30, this.ao);
            }
            if ((this.K & 0x10000000) == 0x10000000) {
                size += a.i.g(31, this.ap);
            }
            if ((this.K & 0x20000000) == 0x20000000) {
                size += a.i.g(32, this.aq);
            }
            this.as = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static g a(a.g data) throws s {
            return a.d(data);
        }

        public static g a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static g a(byte[] data) throws s {
            return a.d(data);
        }

        public static g a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static g a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static g a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static g b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static g b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static g a(a.h input) throws IOException {
            return a.d(input);
        }

        public static g a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aK() {
            return a.bo();
        }

        public a aL() {
            return an.b$g.aK();
        }

        public static a a(g prototype) {
            return an.b$g.aK().a(prototype);
        }

        public a aM() {
            return an.b$g.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.aM();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.aM();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.aL();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.aL();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        public static final class a
        extends p.a<a>
        implements h {
            private int a;
            private int b;
            private int c;
            private int d;
            private a.g e = a.g.d;
            private a.g f = a.g.d;
            private int g;
            private int h;
            private int i;
            private int j;
            private List<Integer> k = Collections.emptyList();
            private List<a.g> l = Collections.emptyList();
            private int m;
            private int n;
            private int o;
            private int p;
            private int q;
            private int r;
            private int s;
            private int t;
            private int u;
            private int v;
            private int w;
            private int x;
            private int y;
            private int z;
            private int A;
            private int B;
            private int C;
            private int D;
            private int E;
            private int F;
            private int G;

            public static final k.a k() {
                return g;
            }

            @Override
            protected p.g l() {
                return h.a(g.class, a.class);
            }

            private a() {
                this.bn();
            }

            private a(p.b parent) {
                super(parent);
                this.bn();
            }

            private void bn() {
                m;
            }

            private static a bo() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.a &= 0xFFFFFFFB;
                this.e = a.g.d;
                this.a &= 0xFFFFFFF7;
                this.f = a.g.d;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = Collections.emptyList();
                this.a &= 0xFFFFFDFF;
                this.l = Collections.emptyList();
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                this.q = 0;
                this.a &= 0xFFFF7FFF;
                this.r = 0;
                this.a &= 0xFFFEFFFF;
                this.s = 0;
                this.a &= 0xFFFDFFFF;
                this.t = 0;
                this.a &= 0xFFFBFFFF;
                this.u = 0;
                this.a &= 0xFFF7FFFF;
                this.v = 0;
                this.a &= 0xFFEFFFFF;
                this.w = 0;
                this.a &= 0xFFDFFFFF;
                this.x = 0;
                this.a &= 0xFFBFFFFF;
                this.y = 0;
                this.a &= 0xFF7FFFFF;
                this.z = 0;
                this.a &= 0xFEFFFFFF;
                this.A = 0;
                this.a &= 0xFDFFFFFF;
                this.B = 0;
                this.a &= 0xFBFFFFFF;
                this.C = 0;
                this.a &= 0xF7FFFFFF;
                this.D = 0;
                this.a &= 0xEFFFFFFF;
                this.E = 0;
                this.a &= 0xDFFFFFFF;
                this.F = 0;
                this.a &= 0xBFFFFFFF;
                this.G = 0;
                this.a &= Integer.MAX_VALUE;
                return this;
            }

            public a n() {
                return an.b$g$a.bo().a(this.N());
            }

            @Override
            public k.a J() {
                return g;
            }

            public g I() {
                return an.b$g.h();
            }

            public g M() {
                g result = this.N();
                if (!result.a()) {
                    throw an.b$g$a.b(result);
                }
                return result;
            }

            public g N() {
                g result = new g(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.L = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.M = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.N = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.O = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.P = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.Q = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.R = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.S = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.T = this.j;
                if ((this.a & 0x200) == 512) {
                    this.k = Collections.unmodifiableList(this.k);
                    this.a &= 0xFFFFFDFF;
                }
                result.U = this.k;
                if ((this.a & 0x400) == 1024) {
                    this.l = Collections.unmodifiableList(this.l);
                    this.a &= 0xFFFFFBFF;
                }
                result.V = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x200;
                }
                result.W = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x400;
                }
                result.X = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x800;
                }
                result.Y = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x1000;
                }
                result.Z = this.p;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x2000;
                }
                result.aa = this.q;
                if ((from_bitField0_ & 0x10000) == 65536) {
                    to_bitField0_ |= 0x4000;
                }
                result.ab = this.r;
                if ((from_bitField0_ & 0x20000) == 131072) {
                    to_bitField0_ |= 0x8000;
                }
                result.ac = this.s;
                if ((from_bitField0_ & 0x40000) == 262144) {
                    to_bitField0_ |= 0x10000;
                }
                result.ad = this.t;
                if ((from_bitField0_ & 0x80000) == 524288) {
                    to_bitField0_ |= 0x20000;
                }
                result.ae = this.u;
                if ((from_bitField0_ & 0x100000) == 0x100000) {
                    to_bitField0_ |= 0x40000;
                }
                result.af = this.v;
                if ((from_bitField0_ & 0x200000) == 0x200000) {
                    to_bitField0_ |= 0x80000;
                }
                result.ag = this.w;
                if ((from_bitField0_ & 0x400000) == 0x400000) {
                    to_bitField0_ |= 0x100000;
                }
                result.ah = this.x;
                if ((from_bitField0_ & 0x800000) == 0x800000) {
                    to_bitField0_ |= 0x200000;
                }
                result.ai = this.y;
                if ((from_bitField0_ & 0x1000000) == 0x1000000) {
                    to_bitField0_ |= 0x400000;
                }
                result.aj = this.z;
                if ((from_bitField0_ & 0x2000000) == 0x2000000) {
                    to_bitField0_ |= 0x800000;
                }
                result.ak = this.A;
                if ((from_bitField0_ & 0x4000000) == 0x4000000) {
                    to_bitField0_ |= 0x1000000;
                }
                result.al = this.B;
                if ((from_bitField0_ & 0x8000000) == 0x8000000) {
                    to_bitField0_ |= 0x2000000;
                }
                result.am = this.C;
                if ((from_bitField0_ & 0x10000000) == 0x10000000) {
                    to_bitField0_ |= 0x4000000;
                }
                result.an = this.D;
                if ((from_bitField0_ & 0x20000000) == 0x20000000) {
                    to_bitField0_ |= 0x8000000;
                }
                result.ao = this.E;
                if ((from_bitField0_ & 0x40000000) == 0x40000000) {
                    to_bitField0_ |= 0x10000000;
                }
                result.ap = this.F;
                if ((from_bitField0_ & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
                    to_bitField0_ |= 0x20000000;
                }
                result.aq = this.G;
                result.K = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof g) {
                    return this.a((g)other);
                }
                super.a(other);
                return this;
            }

            public a a(g other) {
                if (other == an.b$g.h()) {
                    return this;
                }
                if (other.o()) {
                    this.c(other.p());
                }
                if (other.q()) {
                    this.d(other.r());
                }
                if (other.s()) {
                    this.e(other.t());
                }
                if (other.u()) {
                    this.e(other.v());
                }
                if (other.w()) {
                    this.f(other.x());
                }
                if (other.y()) {
                    this.f(other.z());
                }
                if (other.A()) {
                    this.g(other.B());
                }
                if (other.C()) {
                    this.h(other.D());
                }
                if (other.E()) {
                    this.i(other.F());
                }
                if (!other.U.isEmpty()) {
                    if (this.k.isEmpty()) {
                        this.k = other.U;
                        this.a &= 0xFFFFFDFF;
                    } else {
                        this.bp();
                        this.k.addAll(other.U);
                    }
                    this.t_();
                }
                if (!other.V.isEmpty()) {
                    if (this.l.isEmpty()) {
                        this.l = other.V;
                        this.a &= 0xFFFFFBFF;
                    } else {
                        this.bq();
                        this.l.addAll(other.V);
                    }
                    this.t_();
                }
                if (other.S()) {
                    this.k(other.T());
                }
                if (other.U()) {
                    this.l(other.V());
                }
                if (other.W()) {
                    this.m(other.X());
                }
                if (other.Y()) {
                    this.n(other.Z());
                }
                if (other.aa()) {
                    this.o(other.ab());
                }
                if (other.ae()) {
                    this.p(other.af());
                }
                if (other.ag()) {
                    this.q(other.u_());
                }
                if (other.v_()) {
                    this.r(other.w_());
                }
                if (other.x_()) {
                    this.s(other.y_());
                }
                if (other.z_()) {
                    this.t(other.an());
                }
                if (other.ao()) {
                    this.u(other.ap());
                }
                if (other.aq()) {
                    this.v(other.ar());
                }
                if (other.as()) {
                    this.w(other.at());
                }
                if (other.au()) {
                    this.x(other.av());
                }
                if (other.aw()) {
                    this.y(other.ax());
                }
                if (other.ay()) {
                    this.z(other.az());
                }
                if (other.A_()) {
                    this.A(other.aB());
                }
                if (other.aC()) {
                    this.B(other.aD());
                }
                if (other.B_()) {
                    this.C(other.aF());
                }
                if (other.aG()) {
                    this.D(other.aH());
                }
                if (other.aI()) {
                    this.E(other.aJ());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                g parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (g)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a c(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.c;
            }

            public a d(int value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public int t() {
                return this.d;
            }

            public a e(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFFB;
                this.d = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public a.g v() {
                return this.e;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFF7;
                this.e = an.b$g.h().v();
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public a.g x() {
                return this.f;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a aK() {
                this.a &= 0xFFFFFFEF;
                this.f = an.b$g.h().x();
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.g;
            }

            public a f(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a aL() {
                this.a &= 0xFFFFFFDF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.h;
            }

            public a g(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a aM() {
                this.a &= 0xFFFFFFBF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.i;
            }

            public a h(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a aN() {
                this.a &= 0xFFFFFF7F;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public int F() {
                return this.j;
            }

            public a i(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a aO() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            private void bp() {
                if ((this.a & 0x200) != 512) {
                    this.k = new ArrayList<Integer>(this.k);
                    this.a |= 0x200;
                }
            }

            @Override
            public List<Integer> G() {
                return Collections.unmodifiableList(this.k);
            }

            @Override
            public int H() {
                return this.k.size();
            }

            @Override
            public int a(int index) {
                return this.k.get(index);
            }

            public a a(int index, int value) {
                this.bp();
                this.k.set(index, value);
                this.t_();
                return this;
            }

            public a j(int value) {
                this.bp();
                this.k.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends Integer> values) {
                this.bp();
                p.a.a(values, this.k);
                this.t_();
                return this;
            }

            public a aP() {
                this.k = Collections.emptyList();
                this.a &= 0xFFFFFDFF;
                this.t_();
                return this;
            }

            private void bq() {
                if ((this.a & 0x400) != 1024) {
                    this.l = new ArrayList<a.g>(this.l);
                    this.a |= 0x400;
                }
            }

            @Override
            public List<a.g> K() {
                return Collections.unmodifiableList(this.l);
            }

            @Override
            public int L() {
                return this.l.size();
            }

            @Override
            public a.g b(int index) {
                return this.l.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bq();
                this.l.set(index, value);
                this.t_();
                return this;
            }

            public a g(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bq();
                this.l.add(value);
                this.t_();
                return this;
            }

            public a b(Iterable<? extends a.g> values) {
                this.bq();
                p.a.a(values, this.l);
                this.t_();
                return this;
            }

            public a aQ() {
                this.l = Collections.emptyList();
                this.a &= 0xFFFFFBFF;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a k(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a aR() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a l(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aS() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a m(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a aT() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a n(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a aU() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aa() {
                return (this.a & 0x8000) == 32768;
            }

            @Override
            public int ab() {
                return this.q;
            }

            public a o(int value) {
                this.a |= 0x8000;
                this.q = value;
                this.t_();
                return this;
            }

            public a aV() {
                this.a &= 0xFFFF7FFF;
                this.q = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ae() {
                return (this.a & 0x10000) == 65536;
            }

            @Override
            public int af() {
                return this.r;
            }

            public a p(int value) {
                this.a |= 0x10000;
                this.r = value;
                this.t_();
                return this;
            }

            public a aW() {
                this.a &= 0xFFFEFFFF;
                this.r = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ag() {
                return (this.a & 0x20000) == 131072;
            }

            @Override
            public int u_() {
                return this.s;
            }

            public a q(int value) {
                this.a |= 0x20000;
                this.s = value;
                this.t_();
                return this;
            }

            public a aX() {
                this.a &= 0xFFFDFFFF;
                this.s = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean v_() {
                return (this.a & 0x40000) == 262144;
            }

            @Override
            public int w_() {
                return this.t;
            }

            public a r(int value) {
                this.a |= 0x40000;
                this.t = value;
                this.t_();
                return this;
            }

            public a aY() {
                this.a &= 0xFFFBFFFF;
                this.t = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean x_() {
                return (this.a & 0x80000) == 524288;
            }

            @Override
            public int y_() {
                return this.u;
            }

            public a s(int value) {
                this.a |= 0x80000;
                this.u = value;
                this.t_();
                return this;
            }

            public a aZ() {
                this.a &= 0xFFF7FFFF;
                this.u = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean z_() {
                return (this.a & 0x100000) == 0x100000;
            }

            @Override
            public int an() {
                return this.v;
            }

            public a t(int value) {
                this.a |= 0x100000;
                this.v = value;
                this.t_();
                return this;
            }

            public a ba() {
                this.a &= 0xFFEFFFFF;
                this.v = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ao() {
                return (this.a & 0x200000) == 0x200000;
            }

            @Override
            public int ap() {
                return this.w;
            }

            public a u(int value) {
                this.a |= 0x200000;
                this.w = value;
                this.t_();
                return this;
            }

            public a bb() {
                this.a &= 0xFFDFFFFF;
                this.w = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aq() {
                return (this.a & 0x400000) == 0x400000;
            }

            @Override
            public int ar() {
                return this.x;
            }

            public a v(int value) {
                this.a |= 0x400000;
                this.x = value;
                this.t_();
                return this;
            }

            public a bc() {
                this.a &= 0xFFBFFFFF;
                this.x = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean as() {
                return (this.a & 0x800000) == 0x800000;
            }

            @Override
            public int at() {
                return this.y;
            }

            public a w(int value) {
                this.a |= 0x800000;
                this.y = value;
                this.t_();
                return this;
            }

            public a bd() {
                this.a &= 0xFF7FFFFF;
                this.y = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean au() {
                return (this.a & 0x1000000) == 0x1000000;
            }

            @Override
            public int av() {
                return this.z;
            }

            public a x(int value) {
                this.a |= 0x1000000;
                this.z = value;
                this.t_();
                return this;
            }

            public a be() {
                this.a &= 0xFEFFFFFF;
                this.z = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aw() {
                return (this.a & 0x2000000) == 0x2000000;
            }

            @Override
            public int ax() {
                return this.A;
            }

            public a y(int value) {
                this.a |= 0x2000000;
                this.A = value;
                this.t_();
                return this;
            }

            public a bf() {
                this.a &= 0xFDFFFFFF;
                this.A = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ay() {
                return (this.a & 0x4000000) == 0x4000000;
            }

            @Override
            public int az() {
                return this.B;
            }

            public a z(int value) {
                this.a |= 0x4000000;
                this.B = value;
                this.t_();
                return this;
            }

            public a bg() {
                this.a &= 0xFBFFFFFF;
                this.B = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A_() {
                return (this.a & 0x8000000) == 0x8000000;
            }

            @Override
            public int aB() {
                return this.C;
            }

            public a A(int value) {
                this.a |= 0x8000000;
                this.C = value;
                this.t_();
                return this;
            }

            public a bh() {
                this.a &= 0xF7FFFFFF;
                this.C = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aC() {
                return (this.a & 0x10000000) == 0x10000000;
            }

            @Override
            public int aD() {
                return this.D;
            }

            public a B(int value) {
                this.a |= 0x10000000;
                this.D = value;
                this.t_();
                return this;
            }

            public a bi() {
                this.a &= 0xEFFFFFFF;
                this.D = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean B_() {
                return (this.a & 0x20000000) == 0x20000000;
            }

            @Override
            public int aF() {
                return this.E;
            }

            public a C(int value) {
                this.a |= 0x20000000;
                this.E = value;
                this.t_();
                return this;
            }

            public a bj() {
                this.a &= 0xDFFFFFFF;
                this.E = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aG() {
                return (this.a & 0x40000000) == 0x40000000;
            }

            @Override
            public int aH() {
                return this.F;
            }

            public a D(int value) {
                this.a |= 0x40000000;
                this.F = value;
                this.t_();
                return this;
            }

            public a bk() {
                this.a &= 0xBFFFFFFF;
                this.F = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aI() {
                return (this.a & Integer.MIN_VALUE) == Integer.MIN_VALUE;
            }

            @Override
            public int aJ() {
                return this.G;
            }

            public a E(int value) {
                this.a |= Integer.MIN_VALUE;
                this.G = value;
                this.t_();
                return this;
            }

            public a bl() {
                this.a &= Integer.MAX_VALUE;
                this.G = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }
        }
    }

    public static interface h
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public int t();

        public boolean u();

        public a.g v();

        public boolean w();

        public a.g x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();

        public List<Integer> G();

        public int H();

        public int a(int var1);

        public List<a.g> K();

        public int L();

        public a.g b(int var1);

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();

        public boolean aa();

        public int ab();

        public boolean ae();

        public int af();

        public boolean ag();

        public int u_();

        public boolean v_();

        public int w_();

        public boolean x_();

        public int y_();

        public boolean z_();

        public int an();

        public boolean ao();

        public int ap();

        public boolean aq();

        public int ar();

        public boolean as();

        public int at();

        public boolean au();

        public int av();

        public boolean aw();

        public int ax();

        public boolean ay();

        public int az();

        public boolean A_();

        public int aB();

        public boolean aC();

        public int aD();

        public boolean B_();

        public int aF();

        public boolean aG();

        public int aH();

        public boolean aI();

        public int aJ();
    }

    public static final class i
    extends p
    implements j {
        private static final i x;
        private final ap y;
        public static ab<i> a;
        private int z;
        public static final int b = 1;
        private int A;
        public static final int c = 2;
        private int B;
        public static final int d = 3;
        private a.g C;
        public static final int e = 4;
        private int D;
        public static final int f = 5;
        private int E;
        public static final int g = 6;
        private int F;
        public static final int h = 7;
        private int G;
        public static final int i = 8;
        private int H;
        public static final int j = 9;
        private int I;
        public static final int k = 10;
        private int J;
        public static final int l = 11;
        private int K;
        public static final int n = 12;
        private int L;
        public static final int o = 13;
        private int M;
        public static final int p = 14;
        private int N;
        public static final int q = 15;
        private int O;
        public static final int r = 16;
        private int P;
        public static final int s = 17;
        private int Q;
        public static final int t = 18;
        private a.g R;
        public static final int u = 19;
        private int S;
        public static final int v = 20;
        private int T;
        public static final int w = 21;
        private List<a.g> U;
        private byte V = (byte)-1;
        private int W = -1;
        private static final long X = 0L;

        static {
            a = new a.c<i>(){

                public i c(a.h input, n extensionRegistry) throws s {
                    return new i(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            x = new i(true);
            x.as();
        }

        private i(p.a<?> builder) {
            super(builder);
            this.y = builder.b_();
        }

        private i(boolean noInit) {
            this.y = ap.c();
        }

        public static i h() {
            return x;
        }

        public i k() {
            return x;
        }

        @Override
        public final ap b_() {
            return this.y;
        }

        private i(a.h input, n extensionRegistry) throws s {
            this.as();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block30: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block30;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.z |= 1;
                                this.A = input.g();
                                break;
                            }
                            case 16: {
                                this.z |= 2;
                                this.B = input.g();
                                break;
                            }
                            case 26: {
                                this.z |= 4;
                                this.C = input.l();
                                break;
                            }
                            case 32: {
                                this.z |= 8;
                                this.D = input.g();
                                break;
                            }
                            case 40: {
                                this.z |= 0x10;
                                this.E = input.g();
                                break;
                            }
                            case 48: {
                                this.z |= 0x20;
                                this.F = input.g();
                                break;
                            }
                            case 56: {
                                this.z |= 0x40;
                                this.G = input.g();
                                break;
                            }
                            case 64: {
                                this.z |= 0x80;
                                this.H = input.g();
                                break;
                            }
                            case 72: {
                                this.z |= 0x100;
                                this.I = input.g();
                                break;
                            }
                            case 80: {
                                this.z |= 0x200;
                                this.J = input.g();
                                break;
                            }
                            case 88: {
                                this.z |= 0x400;
                                this.K = input.g();
                                break;
                            }
                            case 96: {
                                this.z |= 0x800;
                                this.L = input.g();
                                break;
                            }
                            case 104: {
                                this.z |= 0x1000;
                                this.M = input.g();
                                break;
                            }
                            case 112: {
                                this.z |= 0x2000;
                                this.N = input.g();
                                break;
                            }
                            case 120: {
                                this.z |= 0x4000;
                                this.O = input.g();
                                break;
                            }
                            case 128: {
                                this.z |= 0x8000;
                                this.P = input.g();
                                break;
                            }
                            case 136: {
                                this.z |= 0x10000;
                                this.Q = input.g();
                                break;
                            }
                            case 146: {
                                this.z |= 0x20000;
                                this.R = input.l();
                                break;
                            }
                            case 152: {
                                this.z |= 0x40000;
                                this.S = input.g();
                                break;
                            }
                            case 160: {
                                this.z |= 0x80000;
                                this.T = input.g();
                                break;
                            }
                            case 170: {
                                if ((mutable_bitField0_ & 0x100000) != 0x100000) {
                                    this.U = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 0x100000;
                                }
                                this.U.add(input.l());
                            }
                        }
                    }
                }
                catch (s e2) {
                    throw e2.a(this);
                }
                catch (IOException e3) {
                    throw new s(e3.getMessage()).a(this);
                }
            }
            finally {
                if ((mutable_bitField0_ & 0x100000) == 0x100000) {
                    this.U = Collections.unmodifiableList(this.U);
                }
                this.y = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return i;
        }

        @Override
        protected p.g l() {
            return j.a(i.class, a.class);
        }

        public ab<i> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.z & 1) == 1;
        }

        @Override
        public int p() {
            return this.A;
        }

        @Override
        public boolean q() {
            return (this.z & 2) == 2;
        }

        @Override
        public int r() {
            return this.B;
        }

        @Override
        public boolean s() {
            return (this.z & 4) == 4;
        }

        @Override
        public a.g t() {
            return this.C;
        }

        @Override
        public boolean u() {
            return (this.z & 8) == 8;
        }

        @Override
        public int v() {
            return this.D;
        }

        @Override
        public boolean w() {
            return (this.z & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.E;
        }

        @Override
        public boolean y() {
            return (this.z & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.F;
        }

        @Override
        public boolean A() {
            return (this.z & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.G;
        }

        @Override
        public boolean C() {
            return (this.z & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.H;
        }

        @Override
        public boolean E() {
            return (this.z & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.I;
        }

        @Override
        public boolean G() {
            return (this.z & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.J;
        }

        @Override
        public boolean K() {
            return (this.z & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.K;
        }

        @Override
        public boolean S() {
            return (this.z & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.L;
        }

        @Override
        public boolean U() {
            return (this.z & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.M;
        }

        @Override
        public boolean W() {
            return (this.z & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.N;
        }

        @Override
        public boolean Y() {
            return (this.z & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.O;
        }

        @Override
        public boolean aa() {
            return (this.z & 0x8000) == 32768;
        }

        @Override
        public int ab() {
            return this.P;
        }

        @Override
        public boolean ae() {
            return (this.z & 0x10000) == 65536;
        }

        @Override
        public int af() {
            return this.Q;
        }

        @Override
        public boolean ag() {
            return (this.z & 0x20000) == 131072;
        }

        @Override
        public a.g C_() {
            return this.R;
        }

        @Override
        public boolean D_() {
            return (this.z & 0x40000) == 262144;
        }

        @Override
        public int E_() {
            return this.S;
        }

        @Override
        public boolean F_() {
            return (this.z & 0x80000) == 524288;
        }

        @Override
        public int G_() {
            return this.T;
        }

        @Override
        public List<a.g> H_() {
            return this.U;
        }

        @Override
        public int an() {
            return this.U.size();
        }

        @Override
        public a.g a(int index) {
            return this.U.get(index);
        }

        private void as() {
            this.A = 0;
            this.B = 0;
            this.C = a.g.d;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 0;
            this.O = 0;
            this.P = 0;
            this.Q = 0;
            this.R = a.g.d;
            this.S = 0;
            this.T = 0;
            this.U = Collections.emptyList();
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.V;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.V = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.z & 1) == 1) {
                output.a(1, this.A);
            }
            if ((this.z & 2) == 2) {
                output.a(2, this.B);
            }
            if ((this.z & 4) == 4) {
                output.a(3, this.C);
            }
            if ((this.z & 8) == 8) {
                output.a(4, this.D);
            }
            if ((this.z & 0x10) == 16) {
                output.a(5, this.E);
            }
            if ((this.z & 0x20) == 32) {
                output.a(6, this.F);
            }
            if ((this.z & 0x40) == 64) {
                output.a(7, this.G);
            }
            if ((this.z & 0x80) == 128) {
                output.a(8, this.H);
            }
            if ((this.z & 0x100) == 256) {
                output.a(9, this.I);
            }
            if ((this.z & 0x200) == 512) {
                output.a(10, this.J);
            }
            if ((this.z & 0x400) == 1024) {
                output.a(11, this.K);
            }
            if ((this.z & 0x800) == 2048) {
                output.a(12, this.L);
            }
            if ((this.z & 0x1000) == 4096) {
                output.a(13, this.M);
            }
            if ((this.z & 0x2000) == 8192) {
                output.a(14, this.N);
            }
            if ((this.z & 0x4000) == 16384) {
                output.a(15, this.O);
            }
            if ((this.z & 0x8000) == 32768) {
                output.a(16, this.P);
            }
            if ((this.z & 0x10000) == 65536) {
                output.a(17, this.Q);
            }
            if ((this.z & 0x20000) == 131072) {
                output.a(18, this.R);
            }
            if ((this.z & 0x40000) == 262144) {
                output.a(19, this.S);
            }
            if ((this.z & 0x80000) == 524288) {
                output.a(20, this.T);
            }
            int i2 = 0;
            while (i2 < this.U.size()) {
                output.a(21, this.U.get(i2));
                ++i2;
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.W;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.z & 1) == 1) {
                size += a.i.g(1, this.A);
            }
            if ((this.z & 2) == 2) {
                size += a.i.g(2, this.B);
            }
            if ((this.z & 4) == 4) {
                size += a.i.c(3, this.C);
            }
            if ((this.z & 8) == 8) {
                size += a.i.g(4, this.D);
            }
            if ((this.z & 0x10) == 16) {
                size += a.i.g(5, this.E);
            }
            if ((this.z & 0x20) == 32) {
                size += a.i.g(6, this.F);
            }
            if ((this.z & 0x40) == 64) {
                size += a.i.g(7, this.G);
            }
            if ((this.z & 0x80) == 128) {
                size += a.i.g(8, this.H);
            }
            if ((this.z & 0x100) == 256) {
                size += a.i.g(9, this.I);
            }
            if ((this.z & 0x200) == 512) {
                size += a.i.g(10, this.J);
            }
            if ((this.z & 0x400) == 1024) {
                size += a.i.g(11, this.K);
            }
            if ((this.z & 0x800) == 2048) {
                size += a.i.g(12, this.L);
            }
            if ((this.z & 0x1000) == 4096) {
                size += a.i.g(13, this.M);
            }
            if ((this.z & 0x2000) == 8192) {
                size += a.i.g(14, this.N);
            }
            if ((this.z & 0x4000) == 16384) {
                size += a.i.g(15, this.O);
            }
            if ((this.z & 0x8000) == 32768) {
                size += a.i.g(16, this.P);
            }
            if ((this.z & 0x10000) == 65536) {
                size += a.i.g(17, this.Q);
            }
            if ((this.z & 0x20000) == 131072) {
                size += a.i.c(18, this.R);
            }
            if ((this.z & 0x40000) == 262144) {
                size += a.i.g(19, this.S);
            }
            if ((this.z & 0x80000) == 524288) {
                size += a.i.g(20, this.T);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.U.size()) {
                dataSize += a.i.b(this.U.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 2 * this.H_().size();
            this.W = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static i a(a.g data) throws s {
            return a.d(data);
        }

        public static i a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static i a(byte[] data) throws s {
            return a.d(data);
        }

        public static i a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static i a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static i a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static i b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static i b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static i a(a.h input) throws IOException {
            return a.d(input);
        }

        public static i a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a ao() {
            return a.aN();
        }

        public a ap() {
            return an.b$i.ao();
        }

        public static a a(i prototype) {
            return an.b$i.ao().a(prototype);
        }

        public a aq() {
            return an.b$i.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.aq();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.aq();
        }

        @Override
        public /* synthetic */ y Q() {
            return this.k();
        }

        @Override
        public /* synthetic */ x R() {
            return this.k();
        }

        @Override
        protected /* synthetic */ x.a b(p.b b2) {
            return this.a(b2);
        }

        @Override
        public /* synthetic */ y.a P() {
            return this.ap();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ap();
        }

        public static final class a
        extends p.a<a>
        implements j {
            private int a;
            private int b;
            private int c;
            private a.g d = a.g.d;
            private int e;
            private int f;
            private int g;
            private int h;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;
            private int q;
            private int r;
            private a.g s = a.g.d;
            private int t;
            private int u;
            private List<a.g> v = Collections.emptyList();

            public static final k.a k() {
                return i;
            }

            @Override
            protected p.g l() {
                return j.a(i.class, a.class);
            }

            private a() {
                this.aM();
            }

            private a(p.b parent) {
                super(parent);
                this.aM();
            }

            private void aM() {
                m;
            }

            private static a aN() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = a.g.d;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = 0;
                this.a &= 0xFFFFF7FF;
                this.n = 0;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                this.q = 0;
                this.a &= 0xFFFF7FFF;
                this.r = 0;
                this.a &= 0xFFFEFFFF;
                this.s = a.g.d;
                this.a &= 0xFFFDFFFF;
                this.t = 0;
                this.a &= 0xFFFBFFFF;
                this.u = 0;
                this.a &= 0xFFF7FFFF;
                this.v = Collections.emptyList();
                this.a &= 0xFFEFFFFF;
                return this;
            }

            public a n() {
                return an.b$i$a.aN().a(this.N());
            }

            @Override
            public k.a J() {
                return i;
            }

            public i I() {
                return an.b$i.h();
            }

            public i M() {
                i result = this.N();
                if (!result.a()) {
                    throw an.b$i$a.b(result);
                }
                return result;
            }

            public i N() {
                i result = new i(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.A = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.B = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.C = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.D = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.E = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.F = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.G = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.H = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.I = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.J = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.K = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.L = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.M = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.N = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.O = this.p;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x8000;
                }
                result.P = this.q;
                if ((from_bitField0_ & 0x10000) == 65536) {
                    to_bitField0_ |= 0x10000;
                }
                result.Q = this.r;
                if ((from_bitField0_ & 0x20000) == 131072) {
                    to_bitField0_ |= 0x20000;
                }
                result.R = this.s;
                if ((from_bitField0_ & 0x40000) == 262144) {
                    to_bitField0_ |= 0x40000;
                }
                result.S = this.t;
                if ((from_bitField0_ & 0x80000) == 524288) {
                    to_bitField0_ |= 0x80000;
                }
                result.T = this.u;
                if ((this.a & 0x100000) == 0x100000) {
                    this.v = Collections.unmodifiableList(this.v);
                    this.a &= 0xFFEFFFFF;
                }
                result.U = this.v;
                result.z = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof i) {
                    return this.a((i)other);
                }
                super.a(other);
                return this;
            }

            public a a(i other) {
                if (other == an.b$i.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (other.q()) {
                    this.c(other.r());
                }
                if (other.s()) {
                    this.e(other.t());
                }
                if (other.u()) {
                    this.d(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
                }
                if (other.y()) {
                    this.f(other.z());
                }
                if (other.A()) {
                    this.g(other.B());
                }
                if (other.C()) {
                    this.h(other.D());
                }
                if (other.E()) {
                    this.i(other.F());
                }
                if (other.G()) {
                    this.j(other.H());
                }
                if (other.K()) {
                    this.k(other.L());
                }
                if (other.S()) {
                    this.l(other.T());
                }
                if (other.U()) {
                    this.m(other.V());
                }
                if (other.W()) {
                    this.n(other.X());
                }
                if (other.Y()) {
                    this.o(other.Z());
                }
                if (other.aa()) {
                    this.p(other.ab());
                }
                if (other.ae()) {
                    this.q(other.af());
                }
                if (other.ag()) {
                    this.f(other.C_());
                }
                if (other.D_()) {
                    this.r(other.E_());
                }
                if (other.F_()) {
                    this.s(other.G_());
                }
                if (!other.U.isEmpty()) {
                    if (this.v.isEmpty()) {
                        this.v = other.U;
                        this.a &= 0xFFEFFFFF;
                    } else {
                        this.aO();
                        this.v.addAll(other.U);
                    }
                    this.t_();
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                i parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (i)e2.a();
                        throw e2;
                    }
                }
                finally {
                    if (parsedMessage != null) {
                        this.a(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean o() {
                return (this.a & 1) == 1;
            }

            @Override
            public int p() {
                return this.b;
            }

            public a b(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public int r() {
                return this.c;
            }

            public a c(int value) {
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean s() {
                return (this.a & 4) == 4;
            }

            @Override
            public a.g t() {
                return this.d;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFFB;
                this.d = an.b$i.h().t();
                this.t_();
                return this;
            }

            @Override
            public boolean u() {
                return (this.a & 8) == 8;
            }

            @Override
            public int v() {
                return this.e;
            }

            public a d(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFF7;
                this.e = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean w() {
                return (this.a & 0x10) == 16;
            }

            @Override
            public int x() {
                return this.f;
            }

            public a e(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ao() {
                this.a &= 0xFFFFFFEF;
                this.f = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public int z() {
                return this.g;
            }

            public a f(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ap() {
                this.a &= 0xFFFFFFDF;
                this.g = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public int B() {
                return this.h;
            }

            public a g(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a aq() {
                this.a &= 0xFFFFFFBF;
                this.h = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean C() {
                return (this.a & 0x80) == 128;
            }

            @Override
            public int D() {
                return this.i;
            }

            public a h(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFFF7F;
                this.i = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean E() {
                return (this.a & 0x100) == 256;
            }

            @Override
            public int F() {
                return this.j;
            }

            public a i(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean G() {
                return (this.a & 0x200) == 512;
            }

            @Override
            public int H() {
                return this.k;
            }

            public a j(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a at() {
                this.a &= 0xFFFFFDFF;
                this.k = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean K() {
                return (this.a & 0x400) == 1024;
            }

            @Override
            public int L() {
                return this.l;
            }

            public a k(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a au() {
                this.a &= 0xFFFFFBFF;
                this.l = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S() {
                return (this.a & 0x800) == 2048;
            }

            @Override
            public int T() {
                return this.m;
            }

            public a l(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a av() {
                this.a &= 0xFFFFF7FF;
                this.m = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public int V() {
                return this.n;
            }

            public a m(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aw() {
                this.a &= 0xFFFFEFFF;
                this.n = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean W() {
                return (this.a & 0x2000) == 8192;
            }

            @Override
            public int X() {
                return this.o;
            }

            public a n(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ax() {
                this.a &= 0xFFFFDFFF;
                this.o = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean Y() {
                return (this.a & 0x4000) == 16384;
            }

            @Override
            public int Z() {
                return this.p;
            }

            public a o(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a ay() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean aa() {
                return (this.a & 0x8000) == 32768;
            }

            @Override
            public int ab() {
                return this.q;
            }

            public a p(int value) {
                this.a |= 0x8000;
                this.q = value;
                this.t_();
                return this;
            }

            public a az() {
                this.a &= 0xFFFF7FFF;
                this.q = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ae() {
                return (this.a & 0x10000) == 65536;
            }

            @Override
            public int af() {
                return this.r;
            }

            public a q(int value) {
                this.a |= 0x10000;
                this.r = value;
                this.t_();
                return this;
            }

            public a aG() {
                this.a &= 0xFFFEFFFF;
                this.r = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean ag() {
                return (this.a & 0x20000) == 131072;
            }

            @Override
            public a.g C_() {
                return this.s;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x20000;
                this.s = value;
                this.t_();
                return this;
            }

            public a aH() {
                this.a &= 0xFFFDFFFF;
                this.s = an.b$i.h().C_();
                this.t_();
                return this;
            }

            @Override
            public boolean D_() {
                return (this.a & 0x40000) == 262144;
            }

            @Override
            public int E_() {
                return this.t;
            }

            public a r(int value) {
                this.a |= 0x40000;
                this.t = value;
                this.t_();
                return this;
            }

            public a aI() {
                this.a &= 0xFFFBFFFF;
                this.t = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean F_() {
                return (this.a & 0x80000) == 524288;
            }

            @Override
            public int G_() {
                return this.u;
            }

            public a s(int value) {
                this.a |= 0x80000;
                this.u = value;
                this.t_();
                return this;
            }

            public a aJ() {
                this.a &= 0xFFF7FFFF;
                this.u = 0;
                this.t_();
                return this;
            }

            private void aO() {
                if ((this.a & 0x100000) != 0x100000) {
                    this.v = new ArrayList<a.g>(this.v);
                    this.a |= 0x100000;
                }
            }

            @Override
            public List<a.g> H_() {
                return Collections.unmodifiableList(this.v);
            }

            @Override
            public int an() {
                return this.v.size();
            }

            @Override
            public a.g a(int index) {
                return this.v.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aO();
                this.v.set(index, value);
                this.t_();
                return this;
            }

            public a g(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aO();
                this.v.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.aO();
                p.a.a(values, this.v);
                this.t_();
                return this;
            }

            public a aK() {
                this.v = Collections.emptyList();
                this.a &= 0xFFEFFFFF;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.N();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.N();
            }

            @Override
            public /* synthetic */ y.a g() {
                return this.n();
            }

            @Override
            public /* synthetic */ x.a i() {
                return this.n();
            }

            @Override
            public /* synthetic */ p.a ai() {
                return this.n();
            }

            @Override
            public /* synthetic */ y.a c(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a d(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ a.a a(a.h h2, n n2) throws IOException {
                return this.e(h2, n2);
            }

            @Override
            public /* synthetic */ x.a c(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ a.a a(x x2) {
                return this.d(x2);
            }

            @Override
            public /* synthetic */ y Q() {
                return this.I();
            }

            @Override
            public /* synthetic */ x R() {
                return this.I();
            }

            @Override
            public /* synthetic */ y.a h() {
                return this.m();
            }

            @Override
            public /* synthetic */ x.a j() {
                return this.m();
            }

            @Override
            public /* synthetic */ p.a ah() {
                return this.m();
            }

            @Override
            public /* synthetic */ y am() {
                return this.M();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.M();
            }
        }
    }

    public static interface j
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public boolean s();

        public a.g t();

        public boolean u();

        public int v();

        public boolean w();

        public int x();

        public boolean y();

        public int z();

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public int T();

        public boolean U();

        public int V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();

        public boolean aa();

        public int ab();

        public boolean ae();

        public int af();

        public boolean ag();

        public a.g C_();

        public boolean D_();

        public int E_();

        public boolean F_();

        public int G_();

        public List<a.g> H_();

        public int an();

        public a.g a(int var1);
    }
}

