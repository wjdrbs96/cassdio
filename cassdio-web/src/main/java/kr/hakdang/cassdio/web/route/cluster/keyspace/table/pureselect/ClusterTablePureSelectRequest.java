package kr.hakdang.cassdio.web.route.cluster.keyspace.table.pureselect;

import kr.hakdang.cassdio.core.domain.cluster.keyspace.table.ClusterTableArgs.ClusterTablePureSelectArgs;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ClusterTablePureSelectRequest
 *
 * @author akageun
 * @since 2024-07-03
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClusterTablePureSelectRequest {
    private String cursor;
    private int pageSize;

    @Builder
    public ClusterTablePureSelectRequest(String cursor, int pageSize) {
        this.cursor = cursor;
        this.pageSize = pageSize;
    }

    public ClusterTablePureSelectArgs makeArgs(String keyspace, String table) {
        return ClusterTablePureSelectArgs.builder()
            .keyspace(keyspace)
            .table(table)
            .cursor(cursor)
            .pageSize(pageSize)
            .build();
    }
}
