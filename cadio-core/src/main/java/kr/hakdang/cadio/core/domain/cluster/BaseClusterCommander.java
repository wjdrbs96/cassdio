package kr.hakdang.cadio.core.domain.cluster;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * BaseClusterCommander
 *
 * @author akageun
 * @since 2024-07-01
 */
@Slf4j
public abstract class BaseClusterCommander {

    protected CqlSession makeSession() {
        return CqlSession.builder()
            .addContactPoint(new InetSocketAddress("127.0.0.1", 29042))
            .withLocalDatacenter("dc1")
            .build();
    }
}
