package com.yaroslav.utils;
import java.util.UUID;

public class UniqueIdentifier {

    private UniqueIdentifier() {
    }

    public static long getIdentifier() {
        UUID id = UUID.randomUUID();
        int mostSig = (int) (id.getMostSignificantBits() >> 32);   // Take higher 32 bits of most significant part
        int leastSig = (int) (id.getLeastSignificantBits() & 0xFFFFFFFFL);  // Lower 32 bits of least significant part

        return mostSig ^ leastSig;  // XOR the two parts for a reasonably unique int
    }

}
