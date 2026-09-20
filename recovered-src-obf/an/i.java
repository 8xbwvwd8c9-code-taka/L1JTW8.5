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

public final class i {
    private static k.a a;
    private static p.g b;
    private static k.a c;
    private static p.g d;
    private static k.a e;
    private static p.g f;
    private static k.a g;
    private static p.g h;
    private static k.g i;

    static {
        String[] descriptorData = new String[]{"\n\u0013PBMessageALL9.proto\u0012 l1j.server.server.datas.protobuf\"\u00da\u0002\n\u000btypeInvList\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\barray_18\u0018\u0012 \u0001(\f\u0012\u0010\n\barray_19\u0018\u0013", " \u0001(\f\"\u00da\u0002\n\u000btypeVersion\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007value_6\u0018\u0006 \u0001(\u0005\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\u0012\u0010\n\bvalue_10\u0018\n \u0001(\u0005\u0012\u0010\n\bvalue_11\u0018\u000b \u0001(\u0005\u0012\u0010\n\bvalue_12\u0018\f \u0001(\u0005\u0012\u0010\n\bvalue_13\u0018\r \u0001(\u0005\u0012\u0010\n\bvalue_14\u0018\u000e \u0001(\u0005\u0012\u0010\n\bvalue_15\u0018\u000f \u0001(\u0005\u0012\u0010\n\bvalue_16\u0018\u0010 \u0001(\u0005\u0012\u0010\n\bvalue_17\u0018\u0011 \u0001(\u0005\u0012\u0010\n\bvalue_18\u0018\u0012 \u0001(\u0005\u0012\u0010\n\bvalue_19\u0018\u0013 \u0001(\u0005\"\u00a3\u0001\n\btypeRank\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005", "\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007value_5\u0018\u0005 \u0001(\u0005\u0012\u000f\n\u0007array_6\u0018\u0006 \u0003(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005\"\u00a1\u0001\n\u0006type31\u0012\u000f\n\u0007value_1\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007value_2\u0018\u0002 \u0001(\u0005\u0012\u000f\n\u0007value_3\u0018\u0003 \u0001(\u0005\u0012\u000f\n\u0007value_4\u0018\u0004 \u0001(\u0005\u0012\u000f\n\u0007array_5\u0018\u0005 \u0001(\f\u0012\u000f\n\u0007array_6\u0018\u0006 \u0003(\f\u0012\u000f\n\u0007value_7\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007value_8\u0018\b \u0001(\u0005\u0012\u000f\n\u0007value_9\u0018\t \u0001(\u0005B1\n l1j.server.server.datas.protobufB\rPBMessageALL9"};
        k.g.a assigner = new k.g.a(){

            @Override
            public m a(k.g root) {
                i = root;
                a = an.i.a().e().get(0);
                b = new p.g(a, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Array18", "Array19"});
                c = an.i.a().e().get(1);
                d = new p.g(c, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19"});
                e = an.i.a().e().get(2);
                f = new p.g(e, new String[]{"Value1", "Value2", "Value3", "Value4", "Value5", "Array6", "Value7", "Value8", "Value9"});
                g = an.i.a().e().get(3);
                h = new p.g(g, new String[]{"Value1", "Value2", "Value3", "Value4", "Array5", "Array6", "Value7", "Value8", "Value9"});
                return null;
            }
        };
        k.g.a(descriptorData, new k.g[0], assigner);
    }

    private i() {
    }

    public static void a(m registry) {
    }

    public static k.g a() {
        return i;
    }

    public static final class an.i$a
    extends p
    implements b {
        private static final an.i$a k;
        private final ap l;
        public static ab<an.i$a> a;
        private int n;
        public static final int b = 1;
        private int o;
        public static final int c = 2;
        private int p;
        public static final int d = 3;
        private int q;
        public static final int e = 4;
        private int r;
        public static final int f = 5;
        private a.g s;
        public static final int g = 6;
        private List<a.g> t;
        public static final int h = 7;
        private int u;
        public static final int i = 8;
        private int v;
        public static final int j = 9;
        private int w;
        private byte x = (byte)-1;
        private int y = -1;
        private static final long z = 0L;

        static {
            a = new a.c<an.i$a>(){

                public an.i$a c(a.h input, n extensionRegistry) throws s {
                    return new an.i$a(input, extensionRegistry);
                }

                @Override
                public /* synthetic */ Object d(a.h h2, n n2) throws s {
                    return this.c(h2, n2);
                }
            };
            k = new an.i$a(true);
            k.S();
        }

        private an.i$a(p.a<?> builder) {
            super(builder);
            this.l = builder.b_();
        }

        private an.i$a(boolean noInit) {
            this.l = ap.c();
        }

        public static an.i$a h() {
            return k;
        }

        public an.i$a k() {
            return k;
        }

        @Override
        public final ap b_() {
            return this.l;
        }

        private an.i$a(a.h input, n extensionRegistry) throws s {
            this.S();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block18: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block18;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.n |= 1;
                                this.o = input.g();
                                break;
                            }
                            case 16: {
                                this.n |= 2;
                                this.p = input.g();
                                break;
                            }
                            case 24: {
                                this.n |= 4;
                                this.q = input.g();
                                break;
                            }
                            case 32: {
                                this.n |= 8;
                                this.r = input.g();
                                break;
                            }
                            case 42: {
                                this.n |= 0x10;
                                this.s = input.l();
                                break;
                            }
                            case 50: {
                                if ((mutable_bitField0_ & 0x20) != 32) {
                                    this.t = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 0x20;
                                }
                                this.t.add(input.l());
                                break;
                            }
                            case 56: {
                                this.n |= 0x20;
                                this.u = input.g();
                                break;
                            }
                            case 64: {
                                this.n |= 0x40;
                                this.v = input.g();
                                break;
                            }
                            case 72: {
                                this.n |= 0x80;
                                this.w = input.g();
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
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.t = Collections.unmodifiableList(this.t);
                }
                this.l = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return g;
        }

        @Override
        protected p.g l() {
            return h.a(an.i$a.class, a.class);
        }

        public ab<an.i$a> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.n & 1) == 1;
        }

        @Override
        public int p() {
            return this.o;
        }

        @Override
        public boolean q() {
            return (this.n & 2) == 2;
        }

        @Override
        public int r() {
            return this.p;
        }

        @Override
        public boolean s() {
            return (this.n & 4) == 4;
        }

        @Override
        public int t() {
            return this.q;
        }

        @Override
        public boolean u() {
            return (this.n & 8) == 8;
        }

        @Override
        public int v() {
            return this.r;
        }

        @Override
        public boolean w() {
            return (this.n & 0x10) == 16;
        }

        @Override
        public a.g x() {
            return this.s;
        }

        @Override
        public List<a.g> y() {
            return this.t;
        }

        @Override
        public int z() {
            return this.t.size();
        }

        @Override
        public a.g a(int index) {
            return this.t.get(index);
        }

        @Override
        public boolean A() {
            return (this.n & 0x20) == 32;
        }

        @Override
        public int B() {
            return this.u;
        }

        @Override
        public boolean C() {
            return (this.n & 0x40) == 64;
        }

        @Override
        public int D() {
            return this.v;
        }

        @Override
        public boolean E() {
            return (this.n & 0x80) == 128;
        }

        @Override
        public int F() {
            return this.w;
        }

        private void S() {
            this.o = 0;
            this.p = 0;
            this.q = 0;
            this.r = 0;
            this.s = a.g.d;
            this.t = Collections.emptyList();
            this.u = 0;
            this.v = 0;
            this.w = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.x;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.x = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.n & 1) == 1) {
                output.a(1, this.o);
            }
            if ((this.n & 2) == 2) {
                output.a(2, this.p);
            }
            if ((this.n & 4) == 4) {
                output.a(3, this.q);
            }
            if ((this.n & 8) == 8) {
                output.a(4, this.r);
            }
            if ((this.n & 0x10) == 16) {
                output.a(5, this.s);
            }
            int i2 = 0;
            while (i2 < this.t.size()) {
                output.a(6, this.t.get(i2));
                ++i2;
            }
            if ((this.n & 0x20) == 32) {
                output.a(7, this.u);
            }
            if ((this.n & 0x40) == 64) {
                output.a(8, this.v);
            }
            if ((this.n & 0x80) == 128) {
                output.a(9, this.w);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.y;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.n & 1) == 1) {
                size += a.i.g(1, this.o);
            }
            if ((this.n & 2) == 2) {
                size += a.i.g(2, this.p);
            }
            if ((this.n & 4) == 4) {
                size += a.i.g(3, this.q);
            }
            if ((this.n & 8) == 8) {
                size += a.i.g(4, this.r);
            }
            if ((this.n & 0x10) == 16) {
                size += a.i.c(5, this.s);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.t.size()) {
                dataSize += a.i.b(this.t.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.y().size();
            if ((this.n & 0x20) == 32) {
                size += a.i.g(7, this.u);
            }
            if ((this.n & 0x40) == 64) {
                size += a.i.g(8, this.v);
            }
            if ((this.n & 0x80) == 128) {
                size += a.i.g(9, this.w);
            }
            this.y = size += this.b_().d();
            return size;
        }

        @Override
        protected Object I() throws ObjectStreamException {
            return super.I();
        }

        public static an.i$a a(a.g data) throws s {
            return a.d(data);
        }

        public static an.i$a a(a.g data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.i$a a(byte[] data) throws s {
            return a.d(data);
        }

        public static an.i$a a(byte[] data, n extensionRegistry) throws s {
            return a.d(data, extensionRegistry);
        }

        public static an.i$a a(InputStream input) throws IOException {
            return a.h(input);
        }

        public static an.i$a a(InputStream input, n extensionRegistry) throws IOException {
            return a.h(input, extensionRegistry);
        }

        public static an.i$a b(InputStream input) throws IOException {
            return a.f(input);
        }

        public static an.i$a b(InputStream input, n extensionRegistry) throws IOException {
            return a.f(input, extensionRegistry);
        }

        public static an.i$a a(a.h input) throws IOException {
            return a.d(input);
        }

        public static an.i$a a(a.h input, n extensionRegistry) throws IOException {
            return a.b(input, extensionRegistry);
        }

        public static a G() {
            return a.X();
        }

        public a H() {
            return an.i$a.G();
        }

        public static a a(an.i$a prototype) {
            return an.i$a.G().a(prototype);
        }

        public a K() {
            return an.i$a.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.K();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.K();
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
            return this.H();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.H();
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
            private List<a.g> g = Collections.emptyList();
            private int h;
            private int i;
            private int j;

            public static final k.a k() {
                return g;
            }

            @Override
            protected p.g l() {
                return h.a(an.i$a.class, a.class);
            }

            private a() {
                this.W();
            }

            private a(p.b parent) {
                super(parent);
                this.W();
            }

            private void W() {
                m;
            }

            private static a X() {
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
                this.g = Collections.emptyList();
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                return this;
            }

            public a n() {
                return an.i$a$a.X().a(this.I());
            }

            @Override
            public k.a J() {
                return g;
            }

            public an.i$a G() {
                return an.i$a.h();
            }

            public an.i$a H() {
                an.i$a result = this.I();
                if (!result.a()) {
                    throw an.i$a$a.b(result);
                }
                return result;
            }

            public an.i$a I() {
                an.i$a result = new an.i$a(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.o = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.p = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.q = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.r = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.s = this.f;
                if ((this.a & 0x20) == 32) {
                    this.g = Collections.unmodifiableList(this.g);
                    this.a &= 0xFFFFFFDF;
                }
                result.t = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result.u = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result.v = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result.w = this.j;
                result.n = to_bitField0_;
                this.q_();
                return result;
            }

            public a d(x other) {
                if (other instanceof an.i$a) {
                    return this.a((an.i$a)other);
                }
                super.a(other);
                return this;
            }

            public a a(an.i$a other) {
                if (other == an.i$a.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (other.q()) {
                    this.c(other.r());
                }
                if (other.s()) {
                    this.d(other.t());
                }
                if (other.u()) {
                    this.e(other.v());
                }
                if (other.w()) {
                    this.e(other.x());
                }
                if (!other.t.isEmpty()) {
                    if (this.g.isEmpty()) {
                        this.g = other.t;
                        this.a &= 0xFFFFFFDF;
                    } else {
                        this.Y();
                        this.g.addAll(other.t);
                    }
                    this.t_();
                }
                if (other.A()) {
                    this.f(other.B());
                }
                if (other.C()) {
                    this.g(other.D());
                }
                if (other.E()) {
                    this.h(other.F());
                }
                this.d(other.b_());
                return this;
            }

            @Override
            public final boolean a() {
                return true;
            }

            public a e(a.h input, n extensionRegistry) throws IOException {
                an.i$a parsedMessage = null;
                try {
                    try {
                        parsedMessage = a.d(input, extensionRegistry);
                    }
                    catch (s e2) {
                        parsedMessage = (an.i$a)e2.a();
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

            public a K() {
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

            public a L() {
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

            public a d(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a M() {
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

            public a e(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a N() {
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

            public a O() {
                this.a &= 0xFFFFFFEF;
                this.f = an.i$a.h().x();
                this.t_();
                return this;
            }

            private void Y() {
                if ((this.a & 0x20) != 32) {
                    this.g = new ArrayList<a.g>(this.g);
                    this.a |= 0x20;
                }
            }

            @Override
            public List<a.g> y() {
                return Collections.unmodifiableList(this.g);
            }

            @Override
            public int z() {
                return this.g.size();
            }

            @Override
            public a.g a(int index) {
                return this.g.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.Y();
                this.g.set(index, value);
                this.t_();
                return this;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.Y();
                this.g.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.Y();
                p.a.a(values, this.g);
                this.t_();
                return this;
            }

            public a P() {
                this.g = Collections.emptyList();
                this.a &= 0xFFFFFFDF;
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

            public a f(int value) {
                this.a |= 0x40;
                this.h = value;
                this.t_();
                return this;
            }

            public a S() {
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

            public a g(int value) {
                this.a |= 0x80;
                this.i = value;
                this.t_();
                return this;
            }

            public a T() {
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

            public a h(int value) {
                this.a |= 0x100;
                this.j = value;
                this.t_();
                return this;
            }

            public a U() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.I();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.I();
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
                return this.G();
            }

            @Override
            public /* synthetic */ x R() {
                return this.G();
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
                return this.H();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.H();
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

        public List<a.g> y();

        public int z();

        public a.g a(int var1);

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();
    }

    public static final class c
    extends p
    implements d {
        private static final c v;
        private final ap w;
        public static ab<c> a;
        private int x;
        public static final int b = 1;
        private int y;
        public static final int c = 2;
        private int z;
        public static final int d = 3;
        private int A;
        public static final int e = 4;
        private int B;
        public static final int f = 5;
        private int C;
        public static final int g = 6;
        private int D;
        public static final int h = 7;
        private int E;
        public static final int i = 8;
        private int F;
        public static final int j = 9;
        private int G;
        public static final int k = 10;
        private int H;
        public static final int l = 11;
        private int I;
        public static final int n = 12;
        private int J;
        public static final int o = 13;
        private int K;
        public static final int p = 14;
        private int L;
        public static final int q = 15;
        private int M;
        public static final int r = 16;
        private int N;
        public static final int s = 17;
        private int O;
        public static final int t = 18;
        private a.g P;
        public static final int u = 19;
        private a.g Q;
        private byte R = (byte)-1;
        private int S = -1;
        private static final long T = 0L;

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
            v = new c(true);
            v.ao();
        }

        private c(p.a<?> builder) {
            super(builder);
            this.w = builder.b_();
        }

        private c(boolean noInit) {
            this.w = ap.c();
        }

        public static c h() {
            return v;
        }

        public c k() {
            return v;
        }

        @Override
        public final ap b_() {
            return this.w;
        }

        private c(a.h input, n extensionRegistry) throws s {
            this.ao();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block28: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block28;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.x |= 1;
                                this.y = input.g();
                                break;
                            }
                            case 16: {
                                this.x |= 2;
                                this.z = input.g();
                                break;
                            }
                            case 24: {
                                this.x |= 4;
                                this.A = input.g();
                                break;
                            }
                            case 32: {
                                this.x |= 8;
                                this.B = input.g();
                                break;
                            }
                            case 40: {
                                this.x |= 0x10;
                                this.C = input.g();
                                break;
                            }
                            case 48: {
                                this.x |= 0x20;
                                this.D = input.g();
                                break;
                            }
                            case 56: {
                                this.x |= 0x40;
                                this.E = input.g();
                                break;
                            }
                            case 64: {
                                this.x |= 0x80;
                                this.F = input.g();
                                break;
                            }
                            case 72: {
                                this.x |= 0x100;
                                this.G = input.g();
                                break;
                            }
                            case 80: {
                                this.x |= 0x200;
                                this.H = input.g();
                                break;
                            }
                            case 88: {
                                this.x |= 0x400;
                                this.I = input.g();
                                break;
                            }
                            case 96: {
                                this.x |= 0x800;
                                this.J = input.g();
                                break;
                            }
                            case 104: {
                                this.x |= 0x1000;
                                this.K = input.g();
                                break;
                            }
                            case 112: {
                                this.x |= 0x2000;
                                this.L = input.g();
                                break;
                            }
                            case 120: {
                                this.x |= 0x4000;
                                this.M = input.g();
                                break;
                            }
                            case 128: {
                                this.x |= 0x8000;
                                this.N = input.g();
                                break;
                            }
                            case 136: {
                                this.x |= 0x10000;
                                this.O = input.g();
                                break;
                            }
                            case 146: {
                                this.x |= 0x20000;
                                this.P = input.l();
                                break;
                            }
                            case 154: {
                                this.x |= 0x40000;
                                this.Q = input.l();
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
                this.w = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return a;
        }

        @Override
        protected p.g l() {
            return b.a(c.class, a.class);
        }

        public ab<c> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.x & 1) == 1;
        }

        @Override
        public int p() {
            return this.y;
        }

        @Override
        public boolean q() {
            return (this.x & 2) == 2;
        }

        @Override
        public int r() {
            return this.z;
        }

        @Override
        public boolean s() {
            return (this.x & 4) == 4;
        }

        @Override
        public int t() {
            return this.A;
        }

        @Override
        public boolean u() {
            return (this.x & 8) == 8;
        }

        @Override
        public int v() {
            return this.B;
        }

        @Override
        public boolean w() {
            return (this.x & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.C;
        }

        @Override
        public boolean y() {
            return (this.x & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.D;
        }

        @Override
        public boolean A() {
            return (this.x & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.E;
        }

        @Override
        public boolean C() {
            return (this.x & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.F;
        }

        @Override
        public boolean E() {
            return (this.x & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.G;
        }

        @Override
        public boolean G() {
            return (this.x & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.H;
        }

        @Override
        public boolean K() {
            return (this.x & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.I;
        }

        @Override
        public boolean S() {
            return (this.x & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.J;
        }

        @Override
        public boolean U() {
            return (this.x & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.K;
        }

        @Override
        public boolean W() {
            return (this.x & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.L;
        }

        @Override
        public boolean Y() {
            return (this.x & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.M;
        }

        @Override
        public boolean aa() {
            return (this.x & 0x8000) == 32768;
        }

        @Override
        public int ab() {
            return this.N;
        }

        @Override
        public boolean ae() {
            return (this.x & 0x10000) == 65536;
        }

        @Override
        public int af() {
            return this.O;
        }

        @Override
        public boolean ag() {
            return (this.x & 0x20000) == 131072;
        }

        @Override
        public a.g O_() {
            return this.P;
        }

        @Override
        public boolean P_() {
            return (this.x & 0x40000) == 262144;
        }

        @Override
        public a.g Q_() {
            return this.Q;
        }

        private void ao() {
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
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 0;
            this.O = 0;
            this.P = a.g.d;
            this.Q = a.g.d;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.R;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.R = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.x & 1) == 1) {
                output.a(1, this.y);
            }
            if ((this.x & 2) == 2) {
                output.a(2, this.z);
            }
            if ((this.x & 4) == 4) {
                output.a(3, this.A);
            }
            if ((this.x & 8) == 8) {
                output.a(4, this.B);
            }
            if ((this.x & 0x10) == 16) {
                output.a(5, this.C);
            }
            if ((this.x & 0x20) == 32) {
                output.a(6, this.D);
            }
            if ((this.x & 0x40) == 64) {
                output.a(7, this.E);
            }
            if ((this.x & 0x80) == 128) {
                output.a(8, this.F);
            }
            if ((this.x & 0x100) == 256) {
                output.a(9, this.G);
            }
            if ((this.x & 0x200) == 512) {
                output.a(10, this.H);
            }
            if ((this.x & 0x400) == 1024) {
                output.a(11, this.I);
            }
            if ((this.x & 0x800) == 2048) {
                output.a(12, this.J);
            }
            if ((this.x & 0x1000) == 4096) {
                output.a(13, this.K);
            }
            if ((this.x & 0x2000) == 8192) {
                output.a(14, this.L);
            }
            if ((this.x & 0x4000) == 16384) {
                output.a(15, this.M);
            }
            if ((this.x & 0x8000) == 32768) {
                output.a(16, this.N);
            }
            if ((this.x & 0x10000) == 65536) {
                output.a(17, this.O);
            }
            if ((this.x & 0x20000) == 131072) {
                output.a(18, this.P);
            }
            if ((this.x & 0x40000) == 262144) {
                output.a(19, this.Q);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.S;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.x & 1) == 1) {
                size += a.i.g(1, this.y);
            }
            if ((this.x & 2) == 2) {
                size += a.i.g(2, this.z);
            }
            if ((this.x & 4) == 4) {
                size += a.i.g(3, this.A);
            }
            if ((this.x & 8) == 8) {
                size += a.i.g(4, this.B);
            }
            if ((this.x & 0x10) == 16) {
                size += a.i.g(5, this.C);
            }
            if ((this.x & 0x20) == 32) {
                size += a.i.g(6, this.D);
            }
            if ((this.x & 0x40) == 64) {
                size += a.i.g(7, this.E);
            }
            if ((this.x & 0x80) == 128) {
                size += a.i.g(8, this.F);
            }
            if ((this.x & 0x100) == 256) {
                size += a.i.g(9, this.G);
            }
            if ((this.x & 0x200) == 512) {
                size += a.i.g(10, this.H);
            }
            if ((this.x & 0x400) == 1024) {
                size += a.i.g(11, this.I);
            }
            if ((this.x & 0x800) == 2048) {
                size += a.i.g(12, this.J);
            }
            if ((this.x & 0x1000) == 4096) {
                size += a.i.g(13, this.K);
            }
            if ((this.x & 0x2000) == 8192) {
                size += a.i.g(14, this.L);
            }
            if ((this.x & 0x4000) == 16384) {
                size += a.i.g(15, this.M);
            }
            if ((this.x & 0x8000) == 32768) {
                size += a.i.g(16, this.N);
            }
            if ((this.x & 0x10000) == 65536) {
                size += a.i.g(17, this.O);
            }
            if ((this.x & 0x20000) == 131072) {
                size += a.i.c(18, this.P);
            }
            if ((this.x & 0x40000) == 262144) {
                size += a.i.c(19, this.Q);
            }
            this.S = size += this.b_().d();
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

        public static a ak() {
            return a.aK();
        }

        public a al() {
            return an.i$c.ak();
        }

        public static a a(c prototype) {
            return an.i$c.ak().a(prototype);
        }

        public a am() {
            return an.i$c.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.am();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.am();
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
            return this.al();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.al();
        }

        public static final class a
        extends p.a<a>
        implements d {
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
            private int q;
            private int r;
            private a.g s = a.g.d;
            private a.g t = a.g.d;

            public static final k.a k() {
                return a;
            }

            @Override
            protected p.g l() {
                return b.a(c.class, a.class);
            }

            private a() {
                this.aJ();
            }

            private a(p.b parent) {
                super(parent);
                this.aJ();
            }

            private void aJ() {
                m;
            }

            private static a aK() {
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
                this.q = 0;
                this.a &= 0xFFFF7FFF;
                this.r = 0;
                this.a &= 0xFFFEFFFF;
                this.s = a.g.d;
                this.a &= 0xFFFDFFFF;
                this.t = a.g.d;
                this.a &= 0xFFFBFFFF;
                return this;
            }

            public a n() {
                return an.i$c$a.aK().a(this.N());
            }

            @Override
            public k.a J() {
                return a;
            }

            public c I() {
                return an.i$c.h();
            }

            public c M() {
                c result = this.N();
                if (!result.a()) {
                    throw an.i$c$a.b(result);
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
                result.y = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.z = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.A = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.B = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.C = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.D = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.E = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.F = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.G = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.H = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.I = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.J = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.K = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.L = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.M = this.p;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x8000;
                }
                result.N = this.q;
                if ((from_bitField0_ & 0x10000) == 65536) {
                    to_bitField0_ |= 0x10000;
                }
                result.O = this.r;
                if ((from_bitField0_ & 0x20000) == 131072) {
                    to_bitField0_ |= 0x20000;
                }
                result.P = this.s;
                if ((from_bitField0_ & 0x40000) == 262144) {
                    to_bitField0_ |= 0x40000;
                }
                result.Q = this.t;
                result.x = to_bitField0_;
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
                if (other == an.i$c.h()) {
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
                if (other.aa()) {
                    this.p(other.ab());
                }
                if (other.ae()) {
                    this.q(other.af());
                }
                if (other.ag()) {
                    this.e(other.O_());
                }
                if (other.P_()) {
                    this.f(other.Q_());
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

            public a an() {
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

            public a ao() {
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

            public a ap() {
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

            public a aq() {
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

            public a ar() {
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

            public a as() {
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

            public a at() {
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

            public a au() {
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

            public a av() {
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

            public a aw() {
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

            public a ax() {
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

            public a ay() {
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

            public a az() {
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
            public a.g O_() {
                return this.s;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x20000;
                this.s = value;
                this.t_();
                return this;
            }

            public a aG() {
                this.a &= 0xFFFDFFFF;
                this.s = an.i$c.h().O_();
                this.t_();
                return this;
            }

            @Override
            public boolean P_() {
                return (this.a & 0x40000) == 262144;
            }

            @Override
            public a.g Q_() {
                return this.t;
            }

            public a f(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.a |= 0x40000;
                this.t = value;
                this.t_();
                return this;
            }

            public a aH() {
                this.a &= 0xFFFBFFFF;
                this.t = an.i$c.h().Q_();
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

        public boolean aa();

        public int ab();

        public boolean ae();

        public int af();

        public boolean ag();

        public a.g O_();

        public boolean P_();

        public a.g Q_();
    }

    public static final class e
    extends p
    implements f {
        private static final e k;
        private final ap l;
        public static ab<e> a;
        private int n;
        public static final int b = 1;
        private int o;
        public static final int c = 2;
        private int p;
        public static final int d = 3;
        private int q;
        public static final int e = 4;
        private int r;
        public static final int f = 5;
        private int s;
        public static final int g = 6;
        private List<a.g> t;
        public static final int h = 7;
        private int u;
        public static final int i = 8;
        private int v;
        public static final int j = 9;
        private int w;
        private byte x = (byte)-1;
        private int y = -1;
        private static final long z = 0L;

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
            k = new e(true);
            k.S();
        }

        private e(p.a<?> builder) {
            super(builder);
            this.l = builder.b_();
        }

        private e(boolean noInit) {
            this.l = ap.c();
        }

        public static e h() {
            return k;
        }

        public e k() {
            return k;
        }

        @Override
        public final ap b_() {
            return this.l;
        }

        private e(a.h input, n extensionRegistry) throws s {
            this.S();
            int mutable_bitField0_ = 0;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block18: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block18;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.n |= 1;
                                this.o = input.g();
                                break;
                            }
                            case 16: {
                                this.n |= 2;
                                this.p = input.g();
                                break;
                            }
                            case 24: {
                                this.n |= 4;
                                this.q = input.g();
                                break;
                            }
                            case 32: {
                                this.n |= 8;
                                this.r = input.g();
                                break;
                            }
                            case 40: {
                                this.n |= 0x10;
                                this.s = input.g();
                                break;
                            }
                            case 50: {
                                if ((mutable_bitField0_ & 0x20) != 32) {
                                    this.t = new ArrayList<a.g>();
                                    mutable_bitField0_ |= 0x20;
                                }
                                this.t.add(input.l());
                                break;
                            }
                            case 56: {
                                this.n |= 0x20;
                                this.u = input.g();
                                break;
                            }
                            case 64: {
                                this.n |= 0x40;
                                this.v = input.g();
                                break;
                            }
                            case 72: {
                                this.n |= 0x80;
                                this.w = input.g();
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
                if ((mutable_bitField0_ & 0x20) == 32) {
                    this.t = Collections.unmodifiableList(this.t);
                }
                this.l = unknownFields.b();
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
            return (this.n & 1) == 1;
        }

        @Override
        public int p() {
            return this.o;
        }

        @Override
        public boolean q() {
            return (this.n & 2) == 2;
        }

        @Override
        public int r() {
            return this.p;
        }

        @Override
        public boolean s() {
            return (this.n & 4) == 4;
        }

        @Override
        public int t() {
            return this.q;
        }

        @Override
        public boolean u() {
            return (this.n & 8) == 8;
        }

        @Override
        public int v() {
            return this.r;
        }

        @Override
        public boolean w() {
            return (this.n & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.s;
        }

        @Override
        public List<a.g> y() {
            return this.t;
        }

        @Override
        public int z() {
            return this.t.size();
        }

        @Override
        public a.g a(int index) {
            return this.t.get(index);
        }

        @Override
        public boolean A() {
            return (this.n & 0x20) == 32;
        }

        @Override
        public int B() {
            return this.u;
        }

        @Override
        public boolean C() {
            return (this.n & 0x40) == 64;
        }

        @Override
        public int D() {
            return this.v;
        }

        @Override
        public boolean E() {
            return (this.n & 0x80) == 128;
        }

        @Override
        public int F() {
            return this.w;
        }

        private void S() {
            this.o = 0;
            this.p = 0;
            this.q = 0;
            this.r = 0;
            this.s = 0;
            this.t = Collections.emptyList();
            this.u = 0;
            this.v = 0;
            this.w = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.x;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.x = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.n & 1) == 1) {
                output.a(1, this.o);
            }
            if ((this.n & 2) == 2) {
                output.a(2, this.p);
            }
            if ((this.n & 4) == 4) {
                output.a(3, this.q);
            }
            if ((this.n & 8) == 8) {
                output.a(4, this.r);
            }
            if ((this.n & 0x10) == 16) {
                output.a(5, this.s);
            }
            int i2 = 0;
            while (i2 < this.t.size()) {
                output.a(6, this.t.get(i2));
                ++i2;
            }
            if ((this.n & 0x20) == 32) {
                output.a(7, this.u);
            }
            if ((this.n & 0x40) == 64) {
                output.a(8, this.v);
            }
            if ((this.n & 0x80) == 128) {
                output.a(9, this.w);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.y;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.n & 1) == 1) {
                size += a.i.g(1, this.o);
            }
            if ((this.n & 2) == 2) {
                size += a.i.g(2, this.p);
            }
            if ((this.n & 4) == 4) {
                size += a.i.g(3, this.q);
            }
            if ((this.n & 8) == 8) {
                size += a.i.g(4, this.r);
            }
            if ((this.n & 0x10) == 16) {
                size += a.i.g(5, this.s);
            }
            int dataSize = 0;
            int i2 = 0;
            while (i2 < this.t.size()) {
                dataSize += a.i.b(this.t.get(i2));
                ++i2;
            }
            size += dataSize;
            size += 1 * this.y().size();
            if ((this.n & 0x20) == 32) {
                size += a.i.g(7, this.u);
            }
            if ((this.n & 0x40) == 64) {
                size += a.i.g(8, this.v);
            }
            if ((this.n & 0x80) == 128) {
                size += a.i.g(9, this.w);
            }
            this.y = size += this.b_().d();
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

        public static a G() {
            return a.X();
        }

        public a H() {
            return an.i$e.G();
        }

        public static a a(e prototype) {
            return an.i$e.G().a(prototype);
        }

        public a K() {
            return an.i$e.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.K();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.K();
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
            return this.H();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.H();
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
            private List<a.g> g = Collections.emptyList();
            private int h;
            private int i;
            private int j;

            public static final k.a k() {
                return e;
            }

            @Override
            protected p.g l() {
                return f.a(e.class, a.class);
            }

            private a() {
                this.W();
            }

            private a(p.b parent) {
                super(parent);
                this.W();
            }

            private void W() {
                m;
            }

            private static a X() {
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
                this.g = Collections.emptyList();
                this.a &= 0xFFFFFFDF;
                this.h = 0;
                this.a &= 0xFFFFFFBF;
                this.i = 0;
                this.a &= 0xFFFFFF7F;
                this.j = 0;
                this.a &= 0xFFFFFEFF;
                return this;
            }

            public a n() {
                return an.i$e$a.X().a(this.I());
            }

            @Override
            public k.a J() {
                return e;
            }

            public e G() {
                return an.i$e.h();
            }

            public e H() {
                e result = this.I();
                if (!result.a()) {
                    throw an.i$e$a.b(result);
                }
                return result;
            }

            public e I() {
                e result = new e(this);
                int from_bitField0_ = this.a;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.o = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.p = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.q = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.r = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.s = this.f;
                if ((this.a & 0x20) == 32) {
                    this.g = Collections.unmodifiableList(this.g);
                    this.a &= 0xFFFFFFDF;
                }
                result.t = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x20;
                }
                result.u = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x40;
                }
                result.v = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x80;
                }
                result.w = this.j;
                result.n = to_bitField0_;
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
                if (other == an.i$e.h()) {
                    return this;
                }
                if (other.o()) {
                    this.b(other.p());
                }
                if (other.q()) {
                    this.c(other.r());
                }
                if (other.s()) {
                    this.d(other.t());
                }
                if (other.u()) {
                    this.e(other.v());
                }
                if (other.w()) {
                    this.f(other.x());
                }
                if (!other.t.isEmpty()) {
                    if (this.g.isEmpty()) {
                        this.g = other.t;
                        this.a &= 0xFFFFFFDF;
                    } else {
                        this.Y();
                        this.g.addAll(other.t);
                    }
                    this.t_();
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

            public a b(int value) {
                this.a |= 1;
                this.b = value;
                this.t_();
                return this;
            }

            public a K() {
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

            public a L() {
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

            public a d(int value) {
                this.a |= 4;
                this.d = value;
                this.t_();
                return this;
            }

            public a M() {
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

            public a e(int value) {
                this.a |= 8;
                this.e = value;
                this.t_();
                return this;
            }

            public a N() {
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

            public a f(int value) {
                this.a |= 0x10;
                this.f = value;
                this.t_();
                return this;
            }

            public a O() {
                this.a &= 0xFFFFFFEF;
                this.f = 0;
                this.t_();
                return this;
            }

            private void Y() {
                if ((this.a & 0x20) != 32) {
                    this.g = new ArrayList<a.g>(this.g);
                    this.a |= 0x20;
                }
            }

            @Override
            public List<a.g> y() {
                return Collections.unmodifiableList(this.g);
            }

            @Override
            public int z() {
                return this.g.size();
            }

            @Override
            public a.g a(int index) {
                return this.g.get(index);
            }

            public a a(int index, a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.Y();
                this.g.set(index, value);
                this.t_();
                return this;
            }

            public a e(a.g value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.Y();
                this.g.add(value);
                this.t_();
                return this;
            }

            public a a(Iterable<? extends a.g> values) {
                this.Y();
                p.a.a(values, this.g);
                this.t_();
                return this;
            }

            public a P() {
                this.g = Collections.emptyList();
                this.a &= 0xFFFFFFDF;
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

            public a S() {
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

            public a T() {
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

            public a U() {
                this.a &= 0xFFFFFEFF;
                this.j = 0;
                this.t_();
                return this;
            }

            @Override
            public /* synthetic */ y al() {
                return this.I();
            }

            @Override
            public /* synthetic */ x aj() {
                return this.I();
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
                return this.G();
            }

            @Override
            public /* synthetic */ x R() {
                return this.G();
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
                return this.H();
            }

            @Override
            public /* synthetic */ x ak() {
                return this.H();
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

        public List<a.g> y();

        public int z();

        public a.g a(int var1);

        public boolean A();

        public int B();

        public boolean C();

        public int D();

        public boolean E();

        public int F();
    }

    public static final class g
    extends p
    implements h {
        private static final g v;
        private final ap w;
        public static ab<g> a;
        private int x;
        public static final int b = 1;
        private int y;
        public static final int c = 2;
        private int z;
        public static final int d = 3;
        private int A;
        public static final int e = 4;
        private int B;
        public static final int f = 5;
        private int C;
        public static final int g = 6;
        private int D;
        public static final int h = 7;
        private int E;
        public static final int i = 8;
        private int F;
        public static final int j = 9;
        private int G;
        public static final int k = 10;
        private int H;
        public static final int l = 11;
        private int I;
        public static final int n = 12;
        private int J;
        public static final int o = 13;
        private int K;
        public static final int p = 14;
        private int L;
        public static final int q = 15;
        private int M;
        public static final int r = 16;
        private int N;
        public static final int s = 17;
        private int O;
        public static final int t = 18;
        private int P;
        public static final int u = 19;
        private int Q;
        private byte R = (byte)-1;
        private int S = -1;
        private static final long T = 0L;

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
            v = new g(true);
            v.ao();
        }

        private g(p.a<?> builder) {
            super(builder);
            this.w = builder.b_();
        }

        private g(boolean noInit) {
            this.w = ap.c();
        }

        public static g h() {
            return v;
        }

        public g k() {
            return v;
        }

        @Override
        public final ap b_() {
            return this.w;
        }

        private g(a.h input, n extensionRegistry) throws s {
            this.ao();
            boolean mutable_bitField0_ = false;
            ap.a unknownFields = ap.b();
            try {
                try {
                    boolean done = false;
                    block28: while (!done) {
                        int tag = input.a();
                        switch (tag) {
                            case 0: {
                                done = true;
                                break;
                            }
                            default: {
                                if (this.a(input, unknownFields, extensionRegistry, tag)) continue block28;
                                done = true;
                                break;
                            }
                            case 8: {
                                this.x |= 1;
                                this.y = input.g();
                                break;
                            }
                            case 16: {
                                this.x |= 2;
                                this.z = input.g();
                                break;
                            }
                            case 24: {
                                this.x |= 4;
                                this.A = input.g();
                                break;
                            }
                            case 32: {
                                this.x |= 8;
                                this.B = input.g();
                                break;
                            }
                            case 40: {
                                this.x |= 0x10;
                                this.C = input.g();
                                break;
                            }
                            case 48: {
                                this.x |= 0x20;
                                this.D = input.g();
                                break;
                            }
                            case 56: {
                                this.x |= 0x40;
                                this.E = input.g();
                                break;
                            }
                            case 64: {
                                this.x |= 0x80;
                                this.F = input.g();
                                break;
                            }
                            case 72: {
                                this.x |= 0x100;
                                this.G = input.g();
                                break;
                            }
                            case 80: {
                                this.x |= 0x200;
                                this.H = input.g();
                                break;
                            }
                            case 88: {
                                this.x |= 0x400;
                                this.I = input.g();
                                break;
                            }
                            case 96: {
                                this.x |= 0x800;
                                this.J = input.g();
                                break;
                            }
                            case 104: {
                                this.x |= 0x1000;
                                this.K = input.g();
                                break;
                            }
                            case 112: {
                                this.x |= 0x2000;
                                this.L = input.g();
                                break;
                            }
                            case 120: {
                                this.x |= 0x4000;
                                this.M = input.g();
                                break;
                            }
                            case 128: {
                                this.x |= 0x8000;
                                this.N = input.g();
                                break;
                            }
                            case 136: {
                                this.x |= 0x10000;
                                this.O = input.g();
                                break;
                            }
                            case 144: {
                                this.x |= 0x20000;
                                this.P = input.g();
                                break;
                            }
                            case 152: {
                                this.x |= 0x40000;
                                this.Q = input.g();
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
                this.w = unknownFields.b();
                this.ad();
            }
        }

        public static final k.a n() {
            return c;
        }

        @Override
        protected p.g l() {
            return d.a(g.class, a.class);
        }

        public ab<g> m() {
            return a;
        }

        @Override
        public boolean o() {
            return (this.x & 1) == 1;
        }

        @Override
        public int p() {
            return this.y;
        }

        @Override
        public boolean q() {
            return (this.x & 2) == 2;
        }

        @Override
        public int r() {
            return this.z;
        }

        @Override
        public boolean s() {
            return (this.x & 4) == 4;
        }

        @Override
        public int t() {
            return this.A;
        }

        @Override
        public boolean u() {
            return (this.x & 8) == 8;
        }

        @Override
        public int v() {
            return this.B;
        }

        @Override
        public boolean w() {
            return (this.x & 0x10) == 16;
        }

        @Override
        public int x() {
            return this.C;
        }

        @Override
        public boolean y() {
            return (this.x & 0x20) == 32;
        }

        @Override
        public int z() {
            return this.D;
        }

        @Override
        public boolean A() {
            return (this.x & 0x40) == 64;
        }

        @Override
        public int B() {
            return this.E;
        }

        @Override
        public boolean C() {
            return (this.x & 0x80) == 128;
        }

        @Override
        public int D() {
            return this.F;
        }

        @Override
        public boolean E() {
            return (this.x & 0x100) == 256;
        }

        @Override
        public int F() {
            return this.G;
        }

        @Override
        public boolean G() {
            return (this.x & 0x200) == 512;
        }

        @Override
        public int H() {
            return this.H;
        }

        @Override
        public boolean K() {
            return (this.x & 0x400) == 1024;
        }

        @Override
        public int L() {
            return this.I;
        }

        @Override
        public boolean S() {
            return (this.x & 0x800) == 2048;
        }

        @Override
        public int T() {
            return this.J;
        }

        @Override
        public boolean U() {
            return (this.x & 0x1000) == 4096;
        }

        @Override
        public int V() {
            return this.K;
        }

        @Override
        public boolean W() {
            return (this.x & 0x2000) == 8192;
        }

        @Override
        public int X() {
            return this.L;
        }

        @Override
        public boolean Y() {
            return (this.x & 0x4000) == 16384;
        }

        @Override
        public int Z() {
            return this.M;
        }

        @Override
        public boolean aa() {
            return (this.x & 0x8000) == 32768;
        }

        @Override
        public int ab() {
            return this.N;
        }

        @Override
        public boolean ae() {
            return (this.x & 0x10000) == 65536;
        }

        @Override
        public int af() {
            return this.O;
        }

        @Override
        public boolean ag() {
            return (this.x & 0x20000) == 131072;
        }

        @Override
        public int R_() {
            return this.P;
        }

        @Override
        public boolean S_() {
            return (this.x & 0x40000) == 262144;
        }

        @Override
        public int T_() {
            return this.Q;
        }

        private void ao() {
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
            this.J = 0;
            this.K = 0;
            this.L = 0;
            this.M = 0;
            this.N = 0;
            this.O = 0;
            this.P = 0;
            this.Q = 0;
        }

        @Override
        public final boolean a() {
            byte isInitialized = this.R;
            if (isInitialized != -1) {
                return isInitialized == 1;
            }
            this.R = 1;
            return true;
        }

        @Override
        public void a(a.i output) throws IOException {
            this.d();
            if ((this.x & 1) == 1) {
                output.a(1, this.y);
            }
            if ((this.x & 2) == 2) {
                output.a(2, this.z);
            }
            if ((this.x & 4) == 4) {
                output.a(3, this.A);
            }
            if ((this.x & 8) == 8) {
                output.a(4, this.B);
            }
            if ((this.x & 0x10) == 16) {
                output.a(5, this.C);
            }
            if ((this.x & 0x20) == 32) {
                output.a(6, this.D);
            }
            if ((this.x & 0x40) == 64) {
                output.a(7, this.E);
            }
            if ((this.x & 0x80) == 128) {
                output.a(8, this.F);
            }
            if ((this.x & 0x100) == 256) {
                output.a(9, this.G);
            }
            if ((this.x & 0x200) == 512) {
                output.a(10, this.H);
            }
            if ((this.x & 0x400) == 1024) {
                output.a(11, this.I);
            }
            if ((this.x & 0x800) == 2048) {
                output.a(12, this.J);
            }
            if ((this.x & 0x1000) == 4096) {
                output.a(13, this.K);
            }
            if ((this.x & 0x2000) == 8192) {
                output.a(14, this.L);
            }
            if ((this.x & 0x4000) == 16384) {
                output.a(15, this.M);
            }
            if ((this.x & 0x8000) == 32768) {
                output.a(16, this.N);
            }
            if ((this.x & 0x10000) == 65536) {
                output.a(17, this.O);
            }
            if ((this.x & 0x20000) == 131072) {
                output.a(18, this.P);
            }
            if ((this.x & 0x40000) == 262144) {
                output.a(19, this.Q);
            }
            this.b_().a(output);
        }

        @Override
        public int d() {
            int size = this.S;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.x & 1) == 1) {
                size += a.i.g(1, this.y);
            }
            if ((this.x & 2) == 2) {
                size += a.i.g(2, this.z);
            }
            if ((this.x & 4) == 4) {
                size += a.i.g(3, this.A);
            }
            if ((this.x & 8) == 8) {
                size += a.i.g(4, this.B);
            }
            if ((this.x & 0x10) == 16) {
                size += a.i.g(5, this.C);
            }
            if ((this.x & 0x20) == 32) {
                size += a.i.g(6, this.D);
            }
            if ((this.x & 0x40) == 64) {
                size += a.i.g(7, this.E);
            }
            if ((this.x & 0x80) == 128) {
                size += a.i.g(8, this.F);
            }
            if ((this.x & 0x100) == 256) {
                size += a.i.g(9, this.G);
            }
            if ((this.x & 0x200) == 512) {
                size += a.i.g(10, this.H);
            }
            if ((this.x & 0x400) == 1024) {
                size += a.i.g(11, this.I);
            }
            if ((this.x & 0x800) == 2048) {
                size += a.i.g(12, this.J);
            }
            if ((this.x & 0x1000) == 4096) {
                size += a.i.g(13, this.K);
            }
            if ((this.x & 0x2000) == 8192) {
                size += a.i.g(14, this.L);
            }
            if ((this.x & 0x4000) == 16384) {
                size += a.i.g(15, this.M);
            }
            if ((this.x & 0x8000) == 32768) {
                size += a.i.g(16, this.N);
            }
            if ((this.x & 0x10000) == 65536) {
                size += a.i.g(17, this.O);
            }
            if ((this.x & 0x20000) == 131072) {
                size += a.i.g(18, this.P);
            }
            if ((this.x & 0x40000) == 262144) {
                size += a.i.g(19, this.Q);
            }
            this.S = size += this.b_().d();
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

        public static a ak() {
            return a.aK();
        }

        public a al() {
            return an.i$g.ak();
        }

        public static a a(g prototype) {
            return an.i$g.ak().a(prototype);
        }

        public a am() {
            return an.i$g.a(this);
        }

        protected a a(p.b parent) {
            a builder = new a(parent);
            return builder;
        }

        @Override
        public /* synthetic */ y.a O() {
            return this.am();
        }

        @Override
        public /* synthetic */ x.a M() {
            return this.am();
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
            return this.al();
        }

        @Override
        public /* synthetic */ x.a N() {
            return this.al();
        }

        public static final class a
        extends p.a<a>
        implements h {
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
            private int q;
            private int r;
            private int s;
            private int t;

            public static final k.a k() {
                return c;
            }

            @Override
            protected p.g l() {
                return d.a(g.class, a.class);
            }

            private a() {
                this.aJ();
            }

            private a(p.b parent) {
                super(parent);
                this.aJ();
            }

            private void aJ() {
                m;
            }

            private static a aK() {
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
                this.q = 0;
                this.a &= 0xFFFF7FFF;
                this.r = 0;
                this.a &= 0xFFFEFFFF;
                this.s = 0;
                this.a &= 0xFFFDFFFF;
                this.t = 0;
                this.a &= 0xFFFBFFFF;
                return this;
            }

            public a n() {
                return an.i$g$a.aK().a(this.N());
            }

            @Override
            public k.a J() {
                return c;
            }

            public g I() {
                return an.i$g.h();
            }

            public g M() {
                g result = this.N();
                if (!result.a()) {
                    throw an.i$g$a.b(result);
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
                result.y = this.b;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.z = this.c;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.A = this.d;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.B = this.e;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.C = this.f;
                if ((from_bitField0_ & 0x20) == 32) {
                    to_bitField0_ |= 0x20;
                }
                result.D = this.g;
                if ((from_bitField0_ & 0x40) == 64) {
                    to_bitField0_ |= 0x40;
                }
                result.E = this.h;
                if ((from_bitField0_ & 0x80) == 128) {
                    to_bitField0_ |= 0x80;
                }
                result.F = this.i;
                if ((from_bitField0_ & 0x100) == 256) {
                    to_bitField0_ |= 0x100;
                }
                result.G = this.j;
                if ((from_bitField0_ & 0x200) == 512) {
                    to_bitField0_ |= 0x200;
                }
                result.H = this.k;
                if ((from_bitField0_ & 0x400) == 1024) {
                    to_bitField0_ |= 0x400;
                }
                result.I = this.l;
                if ((from_bitField0_ & 0x800) == 2048) {
                    to_bitField0_ |= 0x800;
                }
                result.J = this.m;
                if ((from_bitField0_ & 0x1000) == 4096) {
                    to_bitField0_ |= 0x1000;
                }
                result.K = this.n;
                if ((from_bitField0_ & 0x2000) == 8192) {
                    to_bitField0_ |= 0x2000;
                }
                result.L = this.o;
                if ((from_bitField0_ & 0x4000) == 16384) {
                    to_bitField0_ |= 0x4000;
                }
                result.M = this.p;
                if ((from_bitField0_ & 0x8000) == 32768) {
                    to_bitField0_ |= 0x8000;
                }
                result.N = this.q;
                if ((from_bitField0_ & 0x10000) == 65536) {
                    to_bitField0_ |= 0x10000;
                }
                result.O = this.r;
                if ((from_bitField0_ & 0x20000) == 131072) {
                    to_bitField0_ |= 0x20000;
                }
                result.P = this.s;
                if ((from_bitField0_ & 0x40000) == 262144) {
                    to_bitField0_ |= 0x40000;
                }
                result.Q = this.t;
                result.x = to_bitField0_;
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
                if (other == an.i$g.h()) {
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
                if (other.aa()) {
                    this.p(other.ab());
                }
                if (other.ae()) {
                    this.q(other.af());
                }
                if (other.ag()) {
                    this.r(other.R_());
                }
                if (other.S_()) {
                    this.s(other.T_());
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

            public a an() {
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

            public a ao() {
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

            public a ap() {
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

            public a aq() {
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

            public a ar() {
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

            public a as() {
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

            public a at() {
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

            public a au() {
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

            public a av() {
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

            public a aw() {
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

            public a ax() {
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

            public a ay() {
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

            public a az() {
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
            public int R_() {
                return this.s;
            }

            public a r(int value) {
                this.a |= 0x20000;
                this.s = value;
                this.t_();
                return this;
            }

            public a aG() {
                this.a &= 0xFFFDFFFF;
                this.s = 0;
                this.t_();
                return this;
            }

            @Override
            public boolean S_() {
                return (this.a & 0x40000) == 262144;
            }

            @Override
            public int T_() {
                return this.t;
            }

            public a s(int value) {
                this.a |= 0x40000;
                this.t = value;
                this.t_();
                return this;
            }

            public a aH() {
                this.a &= 0xFFFBFFFF;
                this.t = 0;
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

        public boolean aa();

        public int ab();

        public boolean ae();

        public int af();

        public boolean ag();

        public int R_();

        public boolean S_();

        public int T_();
    }
}

