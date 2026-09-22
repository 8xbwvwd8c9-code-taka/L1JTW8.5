# Calculation Proof Ledger

Purpose: durable arithmetic reference for the 381 -> 850 migration.

Rules:
- This ledger records validated calculation results supplied by calculation agents.
- Module audit remains the semantic/runtime authority.
- When implementing/fixing core later, use this ledger to recover formulas, edge cases, overflow proofs, and expected numeric results without recomputing.
- Do not treat a math proof as proof of runtime reachability unless the module audit says so.
- 850 remains authority; 381 math is donor evidence only.

---

## w_城戰獲勝獎勵

Audit:
`modules/CASTLE_WAR_VICTORY_REWARD_AUDIT.md`

Validated proof:
```text
CORRECT_PER_MEMBER=20000
DONOR_PER_MEMBER=40000
OVERPAY_PER_MEMBER=20000
RUNTIME_MULTIPLIER=2
OVER_ISSUANCE_PERCENT=100
DUPLICATE_ROW_FORMULA=effective_multiplier=2*N; per_member=40000*N
RECOVER_RECIPIENT_FINAL=20000
RECOVER_NONRECIPIENT_FINAL=0
```

Overflow:
```text
count*passes*M*7*30 overflows int at tested M=1000
count*passes*M*7 overflows int at tested M=10000
count*passes*M overflows int at tested M=100000
long safe for tested M<=1000000, 7 castles, 30 periods
```

Idempotency invariant:
```text
grant iff (castle_id, war_instance_id, reward_row_id, player_id) not previously settled
```

---

## w_負面魔法機率

Audit:
`modules/NEGATIVE_MAGIC_PROBABILITY_AUDIT.md`

Validated proof:
```text
Java integer division truncates toward zero.
釋放元素/弱化屬性:
A>D =>20
A=D =>15
A<D =>10

魔力奪取:
P=BASE

起死回生:
type6=10 => P=BASE+INT
```

Critical arithmetic:
```text
TYPE14_MIN_EFFECTIVE=7
TYPE15_MIN_EFFECTIVE=9
TYPE16_MIN_EFFECTIVE=13
TYPE17_MIN_EFFECTIVE=15
TYPE18_MIN_EFFECTIVE=20
```

Critical donor semantics:
```text
type24..28:
P -= (TMR-typeN)/divisor

When TMR <= typeN-divisor:
division is negative
subtracting negative increases P
```

Class post-process:
```text
Wizard: P>70 =>70
Elf:
P>70 =>55
else P=P/2

Elf discontinuity:
70=>35
71=>55
```

Overflow:
```text
type6*INT uses int multiplication first
tested overflow: 10000*1000000
safe: ((long)type6*INT)/10L
```

---

## w_物品掉落限制三

Audit:
`modules/DROP_LIMIT_THREE_AUDIT.md`

Validated current row:
```text
totalCount=100000000
appearCount=11
CURRENT_REMAINING=99999989
```

Timer:
```text
configured=720..900
donor actual support=720..899
support size=180
expected=809.5 minutes
900 unreachable
```

Gate:
```text
NOW.after(next_drop_time)
NOW < next => reject
NOW == next => reject
NOW > next => allow
```

Quota:
```text
count<=remaining
remaining allowed
remaining+1 rejected
batch accepted domain=1..2147483647
```

State safety:
```text
NEXTCOUNT_OVERFLOW=IMPOSSIBLE under valid state
malformed negative appeared/total can create nonsensical quota
reset persists DB state but does not prove in-memory reset
POTENTIAL_STALE_CACHE=YES
```

Throughput:
```text
minimum delay 720m => 2/day
expected delay 809.5m => ~1.7789/day
maximum delay 899m => ~1.6018/day
```

---

## MAP_HPR_MPR_FAMILY

Audit:
`modules/MAP_HPR_MPR_FAMILY_AUDIT.md`

Validated map4 rectangle areas:
```text
R1=104
R2=1287
R3=6728
R4=1715
R5=1104
R6=1344
```

Geometry:
```text
all pairwise overlaps=0
union area=12282
```

HashMap loss:
```text
6 rows same mapid
Map<Integer,Rectangle>
=> max retained=1
=> lost=5
last row wins
```

Load order examples:
```text
R1..R6 => final R6, 0/0, area1344
R6..R1 => final R1, 200/200, area104
```

Invalid geometry:
```text
map800:
minY=32926
maxY=32894
=> no accepted point
=> effective area=0
```

Underwater asymmetry:
```text
configured 50/50 with protection:
HPR=0
MPR=50
```

Area overflow:
```text
50000*50000=2500000000 > Integer.MAX_VALUE
safe area=((long)width)*height
```

---

## Usage during implementation

Before fixing a module:
1. Read its module audit.
2. Read this ledger entry.
3. Treat exact numeric proofs here as expected-value tests.
4. Re-validate only if runtime formula or source data changed.
5. Record new calculation-agent results here after validation.
