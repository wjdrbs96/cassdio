package kr.hakdang.cassdio.core.domain.cluster.keyspace.table;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClusterTableArgs
 *
 * @author seungh0
 * @since 2024-07-02
 */
public class TableDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ClusterTableListArgs {
        private String keyspace;
        private int pageSize;

        private String cursor;

        @Builder
        public ClusterTableListArgs(String keyspace, int pageSize, String cursor) {
            if (StringUtils.isBlank(keyspace)) {
                throw new IllegalArgumentException("keyspace can't be null or empty");
            }

            if (pageSize > 100) {
                throw new IllegalArgumentException("pageSize can't be greater than 100");
            }

            this.keyspace = keyspace;
            this.pageSize = pageSize;
            this.cursor = cursor;
        }
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ClusterTableGetArgs {

        private String keyspace;
        private String table;

        @Builder
        private ClusterTableGetArgs(String keyspace, String table) {
            if (StringUtils.isBlank(keyspace)) {
                throw new IllegalArgumentException("keyspace can't be null or empty");
            }

            if (StringUtils.isBlank(table)) {
                throw new IllegalArgumentException("table can't be null or empty");
            }

            this.keyspace = keyspace;
            this.table = table;
        }

    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ClusterTableRowArgs {

        private String keyspace;
        private String table;

        private int pageSize;
        private int timeoutSeconds;

        private String cursor;

        @Builder
        public ClusterTableRowArgs(String keyspace, String table, int pageSize, int timeoutSeconds, String cursor) {
            if (StringUtils.isBlank(keyspace)) {
                throw new IllegalArgumentException("keyspace can't be null or empty");
            }

            if (StringUtils.isBlank(table)) {
                throw new IllegalArgumentException("table can't be null or empty");
            }

            if (pageSize > 100) {
                throw new IllegalArgumentException("pageSize can't be greater than 100");
            }

            this.keyspace = keyspace;
            this.table = table;
            this.pageSize = pageSize;
            this.timeoutSeconds = timeoutSeconds;
            this.cursor = cursor;
        }
    }

}
