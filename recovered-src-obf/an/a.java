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

public final class a {
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
        String[] descriptorData = new String[]{"\n\u0012PBMessageALL.proto\u0012 l1j.server.server.datas.protobuf\"\u008c\u0002\n\u0005type1\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type2\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007array_3\u0018\u0003 \u0003(\f\u0012\u000f\n\u0007value_4\u0018\u0004 ", "\u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u009e\u0002\n\u0005type3\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007array_8\u0018\b \u0001(\f\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\ba", "rray_12\u0018\f \u0001(\f\u0012\u0010\n\barray_13\u0018\r \u0001(\f\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\"\u008c\u0002\n\u0005type4\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007array_7\u0018\u0007 \u0001(\f\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\"\u008c\u0002\n\u0005type5\u0012\u000f\n\u0007array_1\u0018\u0001 \u0001(\f\u0012\u000f\n\u0007array_2\u0018\u0002 \u0001(\f", "\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005B0\n l1j.server.server.datas.protobufB\fPBMessageALL"};
        k.g.a assigner = new k.g.a(){

            @Override
            public m a(k.g root) {
                k = root;
                a = an.a.a().e().get(0);
                b = new p.g(a, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                c = an.a.a().e().get(1);
                d = new p.g(c, new String[]{"Value1", "Value2", "Array3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                e = an.a.a().e().get(2);
                f = new p.g(e, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Array8", "Value9", "Value10", "Value11", "Array12", "Array13", "Value14", "Value15", "Value16"});
                g = an.a.a().e().get(3);
                h = new p.g(g, new String[]{"Array1", "Array2", "Value3", "Value4", "Array5", "Array6", "Array7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                i = an.a.a().e().get(4);
                j = new p.g(i, new String[]{"Array1", "Array2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15"});
                return null;
            }
        };
        k.g.a(descriptorData, new k.g[0], assigner);
    }

    private a() {
    }

    public static void a(m registry) {
    }

    public static k.g a() {
        return k;
    }

    public static final class an.a$a
    extends p
    implements b {
        private static final an.a$a r;
        private final ap s;
        public static ab<an.a$a> a;
        private int t;
        public static final int b = 1;
        private int u;
        public static final int c = 2;
        private int v;
        public static final int d = 3;
        private int w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private int z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

        static {
            a = new a.c<an.a$a>(){

                public an.a$a c(a.h input, n extensionRegistry) throws s {
                    return new an.a$a(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            r = new an.a$a(true);
            r.ag();
        }

        private an.a$a(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private an.a$a(boolean noInit) {
            this.s = ap.c();
        }

        public static an.a$a h() {
            return r;
        }

        public an.a$a k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private an.a$a(a.h input, n extensionRegistry) throws s {
            this.ag();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block24: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block24;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.t |= 1;
                                this.u = input.g();
                                break;
                            }
                            case 16: {
                                this.t |= 2;
                                this.v = input.g();
                                break;
                            }
                            case 24: {
                                this.t |= 4;
                                this.w = input.g();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 0x10;
                                this.y = input.g();
                                break;
                            }
                            case 48: {
                                this.t |= 0x20;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x40;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x100;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
                                this.I = input.g();
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
                this.s = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return a;
        }

        @Override
        protected p.g l() {
            return b.a(an.a$a.class, a.class);
        }

        public ab<an.a$a> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.t & 1) == 1;
        }

        @Override
        public int p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public int r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public int t() {
            return this.w;
        }

        @Override
        public boolean u() {
            return (this.t & 8) == 8;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = 0;
            this.w = 0;
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.a(2, this.v);
            }
            if ((this.t & 4) == 4) {
                output.a(3, this.w);
            }
            if ((this.t & 8) == 8) {
                output.a(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                output.a(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.a(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.a(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.a(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.a(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.a(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.g(1, this.u);
            }
            if ((this.t & 2) == 2) {
                size += a.i.g(2, this.v);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(3, this.w);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static an.a$a a(a.g data) throws s {
            return a.d(data);
        }

        public static an.a$a a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.a$a a(byte[] data) throws s {
            return a.d(data);
        }

        public static an.a$a a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.a$a a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static an.a$a a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static an.a$a b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static an.a$a b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static an.a$a a(a.h input) throws IOException {
            return a.d(input);
        }

        public static an.a$a a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.a$a.aa();
        }

        public static a a(an.a$a prototype) {
            return an.a$a.aa().a(prototype);
        }

        public a ae() {
            return an.a$a.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
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
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements b {
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
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return a;
            }

            @Override
            protected p.g l() {
                return b.a(an.a$a.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
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
                return this;
            }

            public a n() {
                return an.a$a$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return a;
            }

            public an.a$a I() {
                return an.a$a.h();
            }

            public an.a$a M() {
                an.a$a result = this.N();
                if (!result.a()) {
                    throw an.a$a$a.b(result);
                }
                return result;
            }

            public an.a$a N() {
                an.a$a result = new an.a$a(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.u = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof an.a$a) {
                    return this.a((an.a$a)other);
                }
                super.a(other);
                return this;
            }

            public a a(an.a$a other) {
                if (other == an.a$a.h()) {
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
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                an.a$a parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (an.a$a)e2.a();
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

            public a b(int value) {
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

            public a c(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
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

            public a ab() {
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

            public a ac() {
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

            public a ad() {
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

            public a ae() {
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

            public a af() {
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

            public a ag() {
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

            public a an() {
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

            public a ao() {
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

            public a ap() {
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

            public a aq() {
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

            public a ar() {
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

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
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
    }

    public static final class c
    extends p
    implements d {
        private static final c r;
        private final ap s;
        public static ab<c> a;
        private int t;
        public static final int b = 1;
        private int u;
        public static final int c = 2;
        private int v;
        public static final int d = 3;
        private List<a.g> w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private int z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

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
            r = new c(true);
            r.ag();
        }

        private c(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private c(boolean noInit) {
            this.s = ap.c();
        }

        public static c h() {
            return r;
        }

        public c k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private c(a.h input, n extensionRegistry) throws s {
            this.ag();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block24: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block24;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.t |= 1;
                                this.u = input.g();
                                break;
                            }
                            case 16: {
                                this.t |= 2;
                                this.v = input.g();
                                break;
                            }
                            case 26: {
                                if ((mutable_bitField0_ & 4) != 4) {
                                    this.w = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 4;
                                }
                                this.w.add(input.l());
                                break;
                            }
                            case 32: {
                                this.t |= 4;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 8;
                                this.y = input.g();
                                break;
                            }
                            case 48: {
                                this.t |= 0x10;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x20;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x40;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x80;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x100;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x200;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x400;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x800;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x1000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x2000;
                                this.I = input.g();
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
                if ((mutable_bitField0_ & 4) == 4) {
                    this.w = Collections.unmodifiableList(this.w);
                }
                this.s = unknownFields.b();
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
            return (this.t & 1) == 1;
        }

        @Override
        public int p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public int r() {
            return this.v;
        }

        @Override
        public List<a.g> s() {
            return this.w;
        }

        @Override
        public int t() {
            return this.w.size();
        }

        @Override
        public a.g a(int index) {
            return this.w.get(index);
        }

        @Override
        public boolean u() {
            return (this.t & 4) == 4;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 8) == 8;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = 0;
            this.v = 0;
            this.w = Collections.emptyList();
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.a(2, this.v);
            }
            int i2 = 0;
            while (i2 < this.w.size()) {
                output.a(3, this.w.get(i2));
                ++i2;
            }
            if ((this.t & 4) == 4) {
                output.a(4, this.x);
            }
            if ((this.t & 8) == 8) {
                output.a(5, this.y);
            }
            if ((this.t & 0x10) == 16) {
                output.a(6, this.z);
            }
            if ((this.t & 0x20) == 32) {
                output.a(7, this.A);
            }
            if ((this.t & 0x40) == 64) {
                output.a(8, this.B);
            }
            if ((this.t & 0x80) == 128) {
                output.a(9, this.C);
            }
            if ((this.t & 0x100) == 256) {
                output.a(10, this.D);
            }
            if ((this.t & 0x200) == 512) {
                output.a(11, this.E);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(12, this.F);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(13, this.G);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(14, this.H);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.g(1, this.u);
            }
            if ((this.t & 2) == 2) {
                size += a.i.g(2, this.v);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.w.size()) {
                dataSize += a.i.b(this.w.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.s().size();
            if ((this.t & 4) == 4) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
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

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.a$c.aa();
        }

        public static a a(c prototype) {
            return an.a$c.aa().a(prototype);
        }

        public a ae() {
            return an.a$c.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
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
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements d {
            private int a;
            private int b;
            private int c;
            private List<a.g> d = Collections.emptyList();
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

            public static final k.a k() {
                return c;
            }

            @Override
            protected p.g l() {
                return d.a(c.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = 0;
                this.a &= 0xFFFFFFFE;
                this.c = 0;
                this.a &= 0xFFFFFFFD;
                this.d = Collections.emptyList();
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
                return this;
            }

            public a n() {
                return an.a$c$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return c;
            }

            public c I() {
                return an.a$c.h();
            }

            public c M() {
                c result = this.N();
                if (!result.a()) {
                    throw an.a$c$a.b(result);
                }
                return result;
            }

            public c N() {
                c result = new c(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.u = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.v = this.c;
                if ((this.a & 4) == 4) {
                    this.d = Collections.unmodifiableList(this.d);
                    this.a &= 0xFFFFFFFB;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 8;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x10;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x100;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x200;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x400;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x800;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x1000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x2000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
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
                if (other == an.a$c.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (other.q()) {
                    this.c(other.r());
                }
                if (!other.w.isEmpty()) {
                    if (this.d.isEmpty()) {
                        this.d = other.w;
                        this.a &= 0xFFFFFFFB;
                    } else {
                        this.aw();
                        this.d.addAll(other.w);
                    }
                    this.t_();
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

            private void aw() {
                if ((this.a & 4) != 4) {
                    this.d = new ArrayList<a.g>(this.d);
                    this.a |= 4;
                }
            }

            @Override
            public List<a.g> s() {
                return Collections.unmodifiableList(this.d);
            }

            @Override
            public int t() {
                return this.d.size();
            }

            @Override
            public a.g a(int index) {
                return this.d.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.d.set(index, value);
                this.t_();
                return this;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.aw();
                this.d.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.aw();
                p.a.a(values, this.d);
                this.t_();
                return this;
            }

            public a aa() {
                this.d = Collections.emptyList();
                this.a &= 0xFFFFFFFB;
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

            public a ab() {
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

            public a ac() {
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

            public a ad() {
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

            public a ae() {
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

            public a af() {
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

            public a ag() {
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

            public a an() {
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

            public a ao() {
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

            public a ap() {
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

            public a aq() {
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

            public a ar() {
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

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
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

    public static interface d
    extends aa {
        public boolean o();

        public int p();

        public boolean q();

        public int r();

        public List<a.g> s();

        public int t();

        public a.g a(int var1);

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
    }

    public static final class e
    extends p
    implements f {
        private static final e s;
        private final ap t;
        public static ab<e> a;
        private int u;
        public static final int b = 1;
        private int v;
        public static final int c = 2;
        private int w;
        public static final int d = 3;
        private int x;
        public static final int e = 4;
        private int y;
        public static final int f = 5;
        private int z;
        public static final int g = 6;
        private int A;
        public static final int h = 7;
        private int B;
        public static final int i = 8;
        private a.g C;
        public static final int j = 9;
        private int D;
        public static final int k = 10;
        private int E;
        public static final int l = 11;
        private int F;
        public static final int n = 12;
        private a.g G;
        public static final int o = 13;
        private a.g H;
        public static final int p = 14;
        private int I;
        public static final int q = 15;
        private int J;
        public static final int r = 16;
        private int K;
        private byte L = (byte)-1;
        private int M = -1;
        private static final long N = 0L;

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
            s = new e(true);
            s.ai();
        }

        private e(p.a<?> builder) {
            super(builder);
            this.t = builder.b_();
        }

        private e(boolean noInit) {
            this.t = ap.c();
        }

        public static e h() {
            return s;
        }

        public e k() {
            return s;
        }

        @Override
        public final ap b_() {
            return this.t;
        }

        private e(a.h input, n extensionRegistry) throws s {
            this.ai();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block25: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block25;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.u |= 1;
                                this.v = input.g();
                                break;
                            }
                            case 16: {
                                this.u |= 2;
                                this.w = input.g();
                                break;
                            }
                            case 24: {
                                this.u |= 4;
                                this.x = input.g();
                                break;
                            }
                            case 32: {
                                this.u |= 8;
                                this.y = input.g();
                                break;
                            }
                            case 40: {
                                this.u |= 0x10;
                                this.z = input.g();
                                break;
                            }
                            case 48: {
                                this.u |= 0x20;
                                this.A = input.g();
                                break;
                            }
                            case 56: {
                                this.u |= 0x40;
                                this.B = input.g();
                                break;
                            }
                            case 66: {
                                this.u |= 0x80;
                                this.C = input.l();
                                break;
                            }
                            case 72: {
                                this.u |= 0x100;
                                this.D = input.g();
                                break;
                            }
                            case 80: {
                                this.u |= 0x200;
                                this.E = input.g();
                                break;
                            }
                            case 88: {
                                this.u |= 0x400;
                                this.F = input.g();
                                break;
                            }
                            case 98: {
                                this.u |= 0x800;
                                this.G = input.l();
                                break;
                            }
                            case 106: {
                                this.u |= 0x1000;
                                this.H = input.l();
                                break;
                            }
                            case 112: {
                                this.u |= 0x2000;
                                this.I = input.g();
                                break;
                            }
                            case 120: {
                                this.u |= 0x4000;
                                this.J = input.g();
                                break;
                            }
                            case 128: {
                                this.u |= 0x8000;
                                this.K = input.g();
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
                this.t = unknownFields.b();
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
            return (this.u & 1) == 1;
        }

        @Override
        public int p() {
            return this.v;
        }

        @Override
        public boolean q() {
            return (this.u & 2) == 2;
        }

        @Override
        public int r() {
            return this.w;
        }

        @Override
        public boolean s() {
            return (this.u & 4) == 4;
        }

        @Override
        public int t() {
            return this.x;
        }

        @Override
        public boolean u() {
            return (this.u & 8) == 8;
        }

        @Override
        public int v() {
            return this.y;
        }

        @Override
        public boolean w() {
            return (this.u & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.z;
        }

        @Override
        public boolean y() {
            return (this.u & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.A;
        }

        @Override
        public boolean A() {
            return (this.u & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.B;
        }

        @Override
        public boolean C() {
            return (this.u & 0x80) == 128;
        }

        @Override
        public a.g D() {
            return this.C;
        }

        @Override
        public boolean E() {
            return (this.u & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.D;
        }

        @Override
        public boolean G() {
            return (this.u & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.E;
        }

        @Override
        public boolean K() {
            return (this.u & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.F;
        }

        @Override
        public boolean S() {
            return (this.u & 0x800) == 2048;
        }

        @Override
        public a.g T() {
            return this.G;
        }

        @Override
        public boolean U() {
            return (this.u & 0x1000) == 4096;
        }

        @Override
        public a.g V() {
            return this.H;
        }

        @Override
        public boolean W() {
            return (this.u & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.I;
        }

        @Override
        public boolean Y() {
            return (this.u & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.J;
        }

        @Override
        public boolean aa() {
            return (this.u & 0x8000) == 32768;
        }

        @Override
        public int ab() {
            return this.K;
        }

        private void ai() {
            this.v = 0;
            this.w = 0;
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = a.g.d;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = a.g.d;
            this.H = a.g.d;
            this.I = 0;
            this.J = 0;
            this.K = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.L;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.L = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.u & 1) == 1) {
                output.a(1, this.v);
            }
            if ((this.u & 2) == 2) {
                output.a(2, this.w);
            }
            if ((this.u & 4) == 4) {
                output.a(3, this.x);
            }
            if ((this.u & 8) == 8) {
                output.a(4, this.y);
            }
            if ((this.u & 0x10) == 16) {
                output.a(5, this.z);
            }
            if ((this.u & 0x20) == 32) {
                output.a(6, this.A);
            }
            if ((this.u & 0x40) == 64) {
                output.a(7, this.B);
            }
            if ((this.u & 0x80) == 128) {
                output.a(8, this.C);
            }
            if ((this.u & 0x100) == 256) {
                output.a(9, this.D);
            }
            if ((this.u & 0x200) == 512) {
                output.a(10, this.E);
            }
            if ((this.u & 0x400) == 1024) {
                output.a(11, this.F);
            }
            if ((this.u & 0x800) == 2048) {
                output.a(12, this.G);
            }
            if ((this.u & 0x1000) == 4096) {
                output.a(13, this.H);
            }
            if ((this.u & 0x2000) == 8192) {
                output.a(14, this.I);
            }
            if ((this.u & 0x4000) == 16384) {
                output.a(15, this.J);
            }
            if ((this.u & 0x8000) == 32768) {
                output.a(16, this.K);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.M;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.u & 1) == 1) {
                size += a.i.g(1, this.v);
            }
            if ((this.u & 2) == 2) {
                size += a.i.g(2, this.w);
            }
            if ((this.u & 4) == 4) {
                size += a.i.g(3, this.x);
            }
            if ((this.u & 8) == 8) {
                size += a.i.g(4, this.y);
            }
            if ((this.u & 0x10) == 16) {
                size += a.i.g(5, this.z);
            }
            if ((this.u & 0x20) == 32) {
                size += a.i.g(6, this.A);
            }
            if ((this.u & 0x40) == 64) {
                size += a.i.g(7, this.B);
            }
            if ((this.u & 0x80) == 128) {
                size += a.i.c(8, this.C);
            }
            if ((this.u & 0x100) == 256) {
                size += a.i.g(9, this.D);
            }
            if ((this.u & 0x200) == 512) {
                size += a.i.g(10, this.E);
            }
            if ((this.u & 0x400) == 1024) {
                size += a.i.g(11, this.F);
            }
            if ((this.u & 0x800) == 2048) {
                size += a.i.c(12, this.G);
            }
            if ((this.u & 0x1000) == 4096) {
                size += a.i.c(13, this.H);
            }
            if ((this.u & 0x2000) == 8192) {
                size += a.i.g(14, this.I);
            }
            if ((this.u & 0x4000) == 16384) {
                size += a.i.g(15, this.J);
            }
            if ((this.u & 0x8000) == 32768) {
                size += a.i.g(16, this.K);
            }
            this.M = size += this.b_().d();
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

        public static a ae() {
            return a.ay();
        }

        public a af() {
            return an.a$e.ae();
        }

        public static a a(e prototype) {
            return an.a$e.ae().a(prototype);
        }

        public a ag() {
            return an.a$e.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ag();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ag();
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
            return this.af();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.af();
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
            private a.g i = a.g.d;
            private int j;
            private int k;
            private int l;
            private a.g m = a.g.d;
            private a.g n = a.g.d;
            private int o;
            private int p;
            private int q;

            public static final k.a k() {
                return e;
            }

            @Override
            protected p.g l() {
                return f.a(e.class, a.class);
            }

            private a() {
                this.ax();
            }

            private a(p.b parent) {
                super(parent);
                this.ax();
            }

            private void ax() {
                m;
            }

            private static a ay() {
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
                this.f = 0;
                this.a &= 0xFFFFFFEF;
                this.g = 0;
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = a.g.d;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                this.k = 0;
                this.a &= 0xFFFFFDFF;
                this.l = 0;
                this.a &= 0xFFFFFBFF;
                this.m = a.g.d;
                this.a &= 0xFFFFF7FF;
                this.n = a.g.d;
                this.a &= 0xFFFFEFFF;
                this.o = 0;
                this.a &= 0xFFFFDFFF;
                this.p = 0;
                this.a &= 0xFFFFBFFF;
                this.q = 0;
                this.a &= 0xFFFF7FFF;
                return this;
            }

            public a n() {
                return an.a$e$a.ay().a(this.N());
            }

            @Override
            public k.a J() {
                return e;
            }

            public e I() {
                return an.a$e.h();
            }

            public e M() {
                e result = this.N();
                if (!result.a()) {
                    throw an.a$e$a.b(result);
                }
                return result;
            }

            public e N() {
                e result = new e(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.v = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.w = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.x = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.y = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.z = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.A = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.B = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.C = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.D = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.E = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.F = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.G = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.H = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.I = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.J = this.p;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x8000;
                }
                result.K = this.q;
                result.u = to_bitField0_;
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
                if (other == an.a$e.h()) {
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
                    this.e(other.D());
                }
                if (other.E()) {
                    this.h(other.F());
                }
                if (other.G()) {
                    this.i(other.H());
                }
                if (other.K()) {
                    this.j(other.L());
                }
                if (other.S()) {
                    this.f(other.T());
                }
                if (other.U()) {
                    this.g(other.V());
                }
                if (other.W()) {
                    this.k(other.X());
                }
                if (other.Y()) {
                    this.l(other.Z());
                }
                if (other.aa()) {
                    this.m(other.ab());
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
                return this.b;
            }

            public a a(int value) {
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

            public a b(int value) {
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

            public a c(int value) {
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

            public a ae() {
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

            public a af() {
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

            public a ag() {
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
            public a.g D() {
                return this.i;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a an() {
                this.a &= 0xFFFFFF7F;
                this.i = an.a$e.h().D();
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

            public a h(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ao() {
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

            public a i(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a ap() {
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

            public a j(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a aq() {
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
            public a.g T() {
                return this.m;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ar() {
                this.a &= 0xFFFFF7FF;
                this.m = an.a$e.h().T();
                this.t_();
                return this;
            }

            @Override
            public boolean U() {
                return (this.a & 0x1000) == 4096;
            }

            @Override
            public a.g V() {
                return this.n;
            }

            public a g(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFEFFF;
                this.n = an.a$e.h().V();
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

            public a k(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a at() {
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

            public a l(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a au() {
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

            public a m(int value) {
                this.a |= 0x8000;
                this.q = value;
                this.t_();
                return this;
            }

            public a av() {
                this.a &= 0xFFFF7FFF;
                this.q = 0;
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

        public a.g D();

        public boolean E();

        public int F();

        public boolean G();

        public int H();

        public boolean K();

        public int L();

        public boolean S();

        public a.g T();

        public boolean U();

        public a.g V();

        public boolean W();

        public int X();

        public boolean Y();

        public int Z();

        public boolean aa();

        public int ab();
    }

    public static final class g
    extends p
    implements h {
        private static final g r;
        private final ap s;
        public static ab<g> a;
        private int t;
        public static final int b = 1;
        private a.g u;
        public static final int c = 2;
        private a.g v;
        public static final int d = 3;
        private int w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private a.g y;
        public static final int g = 6;
        private a.g z;
        public static final int h = 7;
        private a.g A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

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
            r = new g(true);
            r.ag();
        }

        private g(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private g(boolean noInit) {
            this.s = ap.c();
        }

        public static g h() {
            return r;
        }

        public g k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private g(a.h input, n extensionRegistry) throws s {
            this.ag();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block24: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block24;
                                done = true;
                                break;
                            }
                            case 10: {
                                this.t |= 1;
                                this.u = input.l();
                                break;
                            }
                            case 18: {
                                this.t |= 2;
                                this.v = input.l();
                                break;
                            }
                            case 24: {
                                this.t |= 4;
                                this.w = input.g();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.g();
                                break;
                            }
                            case 42: {
                                this.t |= 0x10;
                                this.y = input.l();
                                break;
                            }
                            case 50: {
                                this.t |= 0x20;
                                this.z = input.l();
                                break;
                            }
                            case 58: {
                                this.t |= 0x40;
                                this.A = input.l();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x100;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
                                this.I = input.g();
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
                this.s = unknownFields.b();
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
            return (this.t & 1) == 1;
        }

        @Override
        public a.g p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public a.g r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public int t() {
            return this.w;
        }

        @Override
        public boolean u() {
            return (this.t & 8) == 8;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public a.g x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public a.g z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public a.g B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = a.g.d;
            this.v = a.g.d;
            this.w = 0;
            this.x = 0;
            this.y = a.g.d;
            this.z = a.g.d;
            this.A = a.g.d;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.a(2, this.v);
            }
            if ((this.t & 4) == 4) {
                output.a(3, this.w);
            }
            if ((this.t & 8) == 8) {
                output.a(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                output.a(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.a(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.a(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.a(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.a(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.a(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.c(1, this.u);
            }
            if ((this.t & 2) == 2) {
                size += a.i.c(2, this.v);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(3, this.w);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.c(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.c(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.c(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
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

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.a$g.aa();
        }

        public static a a(g prototype) {
            return an.a$g.aa().a(prototype);
        }

        public a ae() {
            return an.a$g.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
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
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements h {
            private int a;
            private a.g b = a.g.d;
            private a.g c = a.g.d;
            private int d;
            private int e;
            private a.g f = a.g.d;
            private a.g g = a.g.d;
            private a.g h = a.g.d;
            private int i;
            private int j;
            private int k;
            private int l;
            private int m;
            private int n;
            private int o;
            private int p;

            public static final k.a k() {
                return g;
            }

            @Override
            protected p.g l() {
                return h.a(g.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = a.g.d;
                this.a &= 0xFFFFFFFE;
                this.c = a.g.d;
                this.a &= 0xFFFFFFFD;
                this.d = 0;
                this.a &= 0xFFFFFFFB;
                this.e = 0;
                this.a &= 0xFFFFFFF7;
                this.f = a.g.d;
                this.a &= 0xFFFFFFEF;
                this.g = a.g.d;
                this.a &= 0xFFFFFFDF;
                this.h = a.g.d;
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
                return this;
            }

            public a n() {
                return an.a$g$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return g;
            }

            public g I() {
                return an.a$g.h();
            }

            public g M() {
                g result = this.N();
                if (!result.a()) {
                    throw an.a$g$a.b(result);
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
                result.u = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
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
                if (other == an.a$g.h()) {
                    return this;
                }
                if (other.o()) {
                    this.e(other.p());
                }
                if (other.q()) {
                    this.f(other.r());
                }
                if (other.s()) {
                    this.a(other.t());
                }
                if (other.u()) {
                    this.b(other.v());
                }
                if (other.w()) {
                    this.g(other.x());
                }
                if (other.y()) {
                    this.h(other.z());
                }
                if (other.A()) {
                    this.i(other.B());
                }
                if (other.C()) {
                    this.c(other.D());
                }
                if (other.E()) {
                    this.d(other.F());
                }
                if (other.G()) {
                    this.e(other.H());
                }
                if (other.K()) {
                    this.f(other.L());
                }
                if (other.S()) {
                    this.g(other.T());
                }
                if (other.U()) {
                    this.h(other.V());
                }
                if (other.W()) {
                    this.i(other.X());
                }
                if (other.Y()) {
                    this.j(other.Z());
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
            public a.g p() {
                return this.b;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = an.a$g.h().p();
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public a.g r() {
                return this.c;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = an.a$g.h().r();
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

            public a a(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
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

            public a b(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ab() {
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

            public a g(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
                this.a &= 0xFFFFFFEF;
                this.f = an.a$g.h().x();
                this.t_();
                return this;
            }

            @Override
            public boolean y() {
                return (this.a & 0x20) == 32;
            }

            @Override
            public a.g z() {
                return this.g;
            }

            public a h(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
                this.a &= 0xFFFFFFDF;
                this.g = an.a$g.h().z();
                this.t_();
                return this;
            }

            @Override
            public boolean A() {
                return (this.a & 0x40) == 64;
            }

            @Override
            public a.g B() {
                return this.h;
            }

            public a i(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
                this.a &= 0xFFFFFFBF;
                this.h = an.a$g.h().B();
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

            public a c(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a af() {
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

            public a d(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
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

            public a e(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
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

            public a f(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
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

            public a g(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
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

            public a h(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
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

            public a i(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
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

            public a j(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
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

    public static interface h
    extends aa {
        public boolean o();

        public a.g p();

        public boolean q();

        public a.g r();

        public boolean s();

        public int t();

        public boolean u();

        public int v();

        public boolean w();

        public a.g x();

        public boolean y();

        public a.g z();

        public boolean A();

        public a.g B();

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
    }

    public static final class i
    extends p
    implements j {
        private static final i r;
        private final ap s;
        public static ab<i> a;
        private int t;
        public static final int b = 1;
        private a.g u;
        public static final int c = 2;
        private a.g v;
        public static final int d = 3;
        private int w;
        public static final int e = 4;
        private int x;
        public static final int f = 5;
        private int y;
        public static final int g = 6;
        private int z;
        public static final int h = 7;
        private int A;
        public static final int i = 8;
        private int B;
        public static final int j = 9;
        private int C;
        public static final int k = 10;
        private int D;
        public static final int l = 11;
        private int E;
        public static final int n = 12;
        private int F;
        public static final int o = 13;
        private int G;
        public static final int p = 14;
        private int H;
        public static final int q = 15;
        private int I;
        private byte J = (byte)-1;
        private int K = -1;
        private static final long L = 0L;

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
            r = new i(true);
            r.ag();
        }

        private i(p.a<?> builder) {
            super(builder);
            this.s = builder.b_();
        }

        private i(boolean noInit) {
            this.s = ap.c();
        }

        public static i h() {
            return r;
        }

        public i k() {
            return r;
        }

        @Override
        public final ap b_() {
            return this.s;
        }

        private i(a.h input, n extensionRegistry) throws s {
            this.ag();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block24: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block24;
                                done = true;
                                break;
                            }
                            case 10: {
                                this.t |= 1;
                                this.u = input.l();
                                break;
                            }
                            case 18: {
                                this.t |= 2;
                                this.v = input.l();
                                break;
                            }
                            case 24: {
                                this.t |= 4;
                                this.w = input.g();
                                break;
                            }
                            case 32: {
                                this.t |= 8;
                                this.x = input.g();
                                break;
                            }
                            case 40: {
                                this.t |= 0x10;
                                this.y = input.g();
                                break;
                            }
                            case 48: {
                                this.t |= 0x20;
                                this.z = input.g();
                                break;
                            }
                            case 56: {
                                this.t |= 0x40;
                                this.A = input.g();
                                break;
                            }
                            case 64: {
                                this.t |= 0x80;
                                this.B = input.g();
                                break;
                            }
                            case 72: {
                                this.t |= 0x100;
                                this.C = input.g();
                                break;
                            }
                            case 80: {
                                this.t |= 0x200;
                                this.D = input.g();
                                break;
                            }
                            case 88: {
                                this.t |= 0x400;
                                this.E = input.g();
                                break;
                            }
                            case 96: {
                                this.t |= 0x800;
                                this.F = input.g();
                                break;
                            }
                            case 104: {
                                this.t |= 0x1000;
                                this.G = input.g();
                                break;
                            }
                            case 112: {
                                this.t |= 0x2000;
                                this.H = input.g();
                                break;
                            }
                            case 120: {
                                this.t |= 0x4000;
                                this.I = input.g();
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
                this.s = unknownFields.b();
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
            return (this.t & 1) == 1;
        }

        @Override
        public a.g p() {
            return this.u;
        }

        @Override
        public boolean q() {
            return (this.t & 2) == 2;
        }

        @Override
        public a.g r() {
            return this.v;
        }

        @Override
        public boolean s() {
            return (this.t & 4) == 4;
        }

        @Override
        public int t() {
            return this.w;
        }

        @Override
        public boolean u() {
            return (this.t & 8) == 8;
        }

        @Override
        public int v() {
            return this.x;
        }

        @Override
        public boolean w() {
            return (this.t & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.y;
        }

        @Override
        public boolean y() {
            return (this.t & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.z;
        }

        @Override
        public boolean A() {
            return (this.t & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.A;
        }

        @Override
        public boolean C() {
            return (this.t & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.B;
        }

        @Override
        public boolean E() {
            return (this.t & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.C;
        }

        @Override
        public boolean G() {
            return (this.t & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.D;
        }

        @Override
        public boolean K() {
            return (this.t & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.E;
        }

        @Override
        public boolean S() {
            return (this.t & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.F;
        }

        @Override
        public boolean U() {
            return (this.t & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.G;
        }

        @Override
        public boolean W() {
            return (this.t & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.H;
        }

        @Override
        public boolean Y() {
            return (this.t & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.I;
        }

        private void ag() {
            this.u = a.g.d;
            this.v = a.g.d;
            this.w = 0;
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.A = 0;
            this.B = 0;
            this.C = 0;
            this.D = 0;
            this.E = 0;
            this.F = 0;
            this.G = 0;
            this.H = 0;
            this.I = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.J;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.J = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.t & 1) == 1) {
                output.a(1, this.u);
            }
            if ((this.t & 2) == 2) {
                output.a(2, this.v);
            }
            if ((this.t & 4) == 4) {
                output.a(3, this.w);
            }
            if ((this.t & 8) == 8) {
                output.a(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                output.a(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                output.a(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                output.a(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                output.a(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                output.a(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                output.a(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                output.a(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                output.a(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                output.a(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                output.a(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                output.a(15, this.I);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.K;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.t & 1) == 1) {
                size += a.i.c(1, this.u);
            }
            if ((this.t & 2) == 2) {
                size += a.i.c(2, this.v);
            }
            if ((this.t & 4) == 4) {
                size += a.i.g(3, this.w);
            }
            if ((this.t & 8) == 8) {
                size += a.i.g(4, this.x);
            }
            if ((this.t & 0x10) == 16) {
                size += a.i.g(5, this.y);
            }
            if ((this.t & 0x20) == 32) {
                size += a.i.g(6, this.z);
            }
            if ((this.t & 0x40) == 64) {
                size += a.i.g(7, this.A);
            }
            if ((this.t & 0x80) == 128) {
                size += a.i.g(8, this.B);
            }
            if ((this.t & 0x100) == 256) {
                size += a.i.g(9, this.C);
            }
            if ((this.t & 0x200) == 512) {
                size += a.i.g(10, this.D);
            }
            if ((this.t & 0x400) == 1024) {
                size += a.i.g(11, this.E);
            }
            if ((this.t & 0x800) == 2048) {
                size += a.i.g(12, this.F);
            }
            if ((this.t & 0x1000) == 4096) {
                size += a.i.g(13, this.G);
            }
            if ((this.t & 0x2000) == 8192) {
                size += a.i.g(14, this.H);
            }
            if ((this.t & 0x4000) == 16384) {
                size += a.i.g(15, this.I);
            }
            this.K = size += this.b_().d();
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

        public static a aa() {
            return a.av();
        }

        public a ab() {
            return an.a$i.aa();
        }

        public static a a(i prototype) {
            return an.a$i.aa().a(prototype);
        }

        public a ae() {
            return an.a$i.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.ae();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.ae();
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
            return this.ab();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.ab();
        }

        public static final class a
        extends p.a<a>
        implements j {
            private int a;
            private a.g b = a.g.d;
            private a.g c = a.g.d;
            private int d;
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

            public static final k.a k() {
                return i;
            }

            @Override
            protected p.g l() {
                return j.a(i.class, a.class);
            }

            private a() {
                this.au();
            }

            private a(p.b parent) {
                super(parent);
                this.au();
            }

            private void au() {
                m;
            }

            private static a av() {
                return new a();
            }

            public a m() {
                super.ah();
                this.b = a.g.d;
                this.a &= 0xFFFFFFFE;
                this.c = a.g.d;
                this.a &= 0xFFFFFFFD;
                this.d = 0;
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
                return this;
            }

            public a n() {
                return an.a$i$a.av().a(this.N());
            }

            @Override
            public k.a J() {
                return i;
            }

            public i I() {
                return an.a$i.h();
            }

            public i M() {
                i result = this.N();
                if (!result.a()) {
                    throw an.a$i$a.b(result);
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
                result.u = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.v = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.w = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.x = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.y = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.z = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.A = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.B = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.C = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.D = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.E = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.F = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.G = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.H = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.I = this.p;
                result.t = to_bitField0_;
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
                if (other == an.a$i.h()) {
                    return this;
                }
                if (other.o()) {
                    this.e(other.p());
                }
                if (other.q()) {
                    this.f(other.r());
                }
                if (other.s()) {
                    this.a(other.t());
                }
                if (other.u()) {
                    this.b(other.v());
                }
                if (other.w()) {
                    this.c(other.x());
                }
                if (other.y()) {
                    this.d(other.z());
                }
                if (other.A()) {
                    this.e(other.B());
                }
                if (other.C()) {
                    this.f(other.D());
                }
                if (other.E()) {
                    this.g(other.F());
                }
                if (other.G()) {
                    this.h(other.H());
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
            public a.g p() {
                return this.b;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFFE;
                this.b = an.a$i.h().p();
                this.t_();
                return this;
            }

            @Override
            public boolean q() {
                return (this.a & 2) == 2;
            }

            @Override
            public a.g r() {
                return this.c;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 2;
                this.c = value;
                this.t_();
                return this;
            }

            public a P() {
                this.a &= 0xFFFFFFFD;
                this.c = an.a$i.h().r();
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

            public a a(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a aa() {
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

            public a b(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a ab() {
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

            public a c(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a ac() {
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

            public a d(int value) {
                this.a |= 0x20;
                this.g = value;
                this.t_();
                return this;
            }

            public a ad() {
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

            public a e(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a ae() {
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

            public a f(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a af() {
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

            public a g(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a ag() {
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

            public a h(int value) {
                this.a |= 0x200;
                this.k = value;
                this.t_();
                return this;
            }

            public a an() {
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

            public a i(int value) {
                this.a |= 0x400;
                this.l = value;
                this.t_();
                return this;
            }

            public a ao() {
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

            public a j(int value) {
                this.a |= 0x800;
                this.m = value;
                this.t_();
                return this;
            }

            public a ap() {
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

            public a k(int value) {
                this.a |= 0x1000;
                this.n = value;
                this.t_();
                return this;
            }

            public a aq() {
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

            public a l(int value) {
                this.a |= 0x2000;
                this.o = value;
                this.t_();
                return this;
            }

            public a ar() {
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

            public a m(int value) {
                this.a |= 0x4000;
                this.p = value;
                this.t_();
                return this;
            }

            public a as() {
                this.a &= 0xFFFFBFFF;
                this.p = 0;
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

        public a.g p();

        public boolean q();

        public a.g r();

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
    }
}

