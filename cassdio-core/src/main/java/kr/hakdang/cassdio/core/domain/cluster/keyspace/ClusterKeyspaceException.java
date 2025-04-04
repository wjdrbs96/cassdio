package kr.hakdang.cassdio.core.domain.cluster.keyspace;

import kr.hakdang.cassdio.common.error.BaseException;
import kr.hakdang.cassdio.common.error.ErrorCode;

/**
 * ClusterKeyspaceException
 *
 * @author seungh0
 * @since 2024-07-07
 */
public class ClusterKeyspaceException {

    public static class ClusterKeyspaceNotFoundException extends BaseException {

        public ClusterKeyspaceNotFoundException(String message) {
            super(message, ErrorCode.E404_NOT_FOUND_KEYSPACE);
        }

    }

}
