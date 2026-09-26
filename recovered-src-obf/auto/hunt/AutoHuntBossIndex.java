package auto.hunt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class AutoHuntBossIndex implements AutoHuntBossResolver {
    private volatile Set<Integer> npcIds = Collections.emptySet();

    public void replaceAll(Collection<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            npcIds = Collections.emptySet();
            return;
        }
        npcIds = Collections.unmodifiableSet(new HashSet<Integer>(ids));
    }

    @Override
    public boolean isBoss(int npcId) {
        return npcIds.contains(npcId);
    }
}
