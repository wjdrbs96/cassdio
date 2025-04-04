package kr.hakdang.cassdio.common.utils;

import java.util.UUID;
import java.util.zip.CRC32;

/**
 * IdGenerator
 *
 * @author akageun
 * @since 2024-07-17
 */
public abstract class IdGenerator {

    public static String makeId() {
        String uuid = UUID.randomUUID().toString();

        CRC32 crc = new CRC32();
        crc.update(uuid.getBytes());

        return Long.toString(crc.getValue());
    }
}
