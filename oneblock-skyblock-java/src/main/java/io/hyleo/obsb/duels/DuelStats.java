package io.hyleo.obsb.duels;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DuelStats {
    float damageDealt;
    float damageTaken;
    Float remainingHealth;

    public boolean dealDamage(float damage) {
        if (isDead()) {
            return false;
        }
        damageDealt += damage;
        return true;
    }

    public boolean takeDamage(float damage) {
        if (isDead()) {
            return false;
        }
        damageTaken += damage;
        return true;
    }

    public boolean remainingHealth(float health) {
        if (isDead()) {
            return false;
        }
        remainingHealth = health;
        return true;
    }

    public boolean isDead() {
        return remainingHealth != null;
    }

}
