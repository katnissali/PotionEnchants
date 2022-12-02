package me.BigMarco254.potionenchants.utils;

import java.io.Serializable;
import java.util.Objects;

public class Pair<K, V> implements Serializable {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }

    public int hashCode() {
        return this.key.hashCode() * 13 + (this.value == null ? 0 : this.value.hashCode());
    }

    private boolean equals(Pair o) {
        if (this == o) return true;
        return Objects.equals(this.value, o.value) && Objects.equals(this.key, o.key);
//        if (!Objects.equals(this.key, o.key)) {
//            return false;
//        }
//        return Objects.equals(this.value, o.value);
    }
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Pair)) return false;
        return this.equals((Pair) o);

//        if (this == o) {
//            return true;
//        }
//        if (o instanceof Pair) {
//            Pair pair = (Pair)o;
//            if (!Objects.equals(this.key, pair.key)) {
//                return false;
//            }
//            return Objects.equals(this.value, pair.value);
//        }
//        return false;
    }
}

